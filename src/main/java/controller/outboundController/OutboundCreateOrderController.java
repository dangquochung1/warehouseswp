/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.outboundController;

import java.io.IOException;
import java.io.PrintWriter;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dal.OrderDAO;
import dal.UserDAO;
import dal.WarehouseDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import jakarta.servlet.http.HttpSession;
import model.User;
import model.Warehouse;
import model.Orders;
import model.OrderDetail;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@WebServlet(name = "OutboundCreateOrderController", urlPatterns = {"/outboundcreateorder"})
public class OutboundCreateOrderController extends HttpServlet {

    private UserDAO userDAO;
    private WarehouseDAO warehouseDAO;
    private OrderDAO orderDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
        warehouseDAO = new WarehouseDAO();
        orderDAO = new OrderDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Lấy danh sách warehouse
            List<Warehouse> warehouseList = warehouseDAO.getAllWarehouses();
            request.setAttribute("warehouseList", warehouseList);

            // Lấy danh sách staff (user có role là "Staff")
            List<User> staffList = userDAO.getUsersByRoleName("Staff");
            request.setAttribute("staffList", staffList);

            // Set current date
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = sdf.format(new Date());
            request.setAttribute("currentDate", currentDate);

            // Forward to JSP
            request.getRequestDispatcher("createOutboundOrder.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {
            HttpSession session = request.getSession();
            String location = request.getParameter("location");
            String responsibleStaff = request.getParameter("responsibleStaff");
            String orderDate = request.getParameter("orderDate");
            String expectedShipDate = request.getParameter("expectedShipDate");
            String[] items = request.getParameterValues("items[]");

            Orders order = new Orders();
            order.setType("outbound");
            order.setCreatedBy("U002");
            order.setAssignedTo(responsibleStaff);
            order.setScheduledDate(java.sql.Date.valueOf(orderDate));
            order.setStatus("pending");
            order.setNote("Created from web interface");

            List<OrderDetail> orderDetails = new ArrayList<>();
            dal.ProductDAO productDAO = new dal.ProductDAO();
            Gson gson = new Gson();

            if (items != null && items.length > 0) {
                for (String raw : items) {
                    if (raw == null || raw.isEmpty()) continue;

                    String json = java.net.URLDecoder.decode(raw, java.nio.charset.StandardCharsets.UTF_8);
                    JsonObject node = gson.fromJson(json, JsonObject.class);

                    String productId = node.get("productId").getAsString();
                    int qtyNeeded = node.get("quantity").getAsInt();
                    String preferredWid = null;
                    JsonElement e = node.get("preferredWarehouseId");
                    if (e != null && !e.isJsonNull()) {
                        preferredWid = e.getAsString();
                    }

                    int remaining = qtyNeeded;
                    int price = node.get("avgPrice").getAsInt();
                    // ===== BƯỚC 1: Lấy hết từ kho ưu tiên (nếu có) =====
                    if (preferredWid != null && !preferredWid.isEmpty()) {
                        List<model.RackLotStock> racks = productDAO.getRackLotsFIFO(productId, preferredWid);

                        for (model.RackLotStock rack : racks) {
                            if (remaining <= 0) break;

                            int takeQty = Math.min(rack.getQuantity(), remaining);

                            // Tạo OrderDetail cho rack này
                            OrderDetail detail = new OrderDetail();
                            detail.setProductId(productId);
                            detail.setQuantity_expected(takeQty);
                            detail.setAisleId(rack.getAisleId());
                            detail.setPrice(price);
                            detail.setNote("WH:" + preferredWid + " | Rack:" + rack.getRackId() + " | Lot:" + rack.getLotdetailId());
                            orderDetails.add(detail);

                            // Trừ tồn kho
                            productDAO.deductRackLotQuantity(rack.getRacklotId(), takeQty);
                            productDAO.deductLotDetailRemaining(rack.getLotdetailId(), takeQty);

                            remaining -= takeQty;
                        }
                    }

                    // ===== BƯỚC 2: Nếu còn thiếu, tìm kho khác =====
                    if (remaining > 0) {
                        // Lấy tất cả các kho khác có sản phẩm này
                        List<Warehouse> otherWarehouses = warehouseDAO.getAllWarehouses();

                        for (Warehouse wh : otherWarehouses) {
                            if (remaining <= 0) break;

                            // Bỏ qua kho đã dùng
                            if (wh.getWarehouseId().equals(preferredWid)) continue;

                            List<model.RackLotStock> racks = productDAO.getRackLotsFIFO(productId, wh.getWarehouseId());

                            for (model.RackLotStock rack : racks) {
                                if (remaining <= 0) break;

                                int takeQty = Math.min(rack.getQuantity(), remaining);

                                // Tạo OrderDetail cho rack này (kho khác)
                                OrderDetail detail = new OrderDetail();
                                detail.setProductId(productId);
                                detail.setQuantity_expected(takeQty);
                                detail.setAisleId(rack.getAisleId());
                                detail.setPrice(price);
                                detail.setNote("FALLBACK_WH:" + wh.getWarehouseId() + " | Rack:" + rack.getRackId() + " | Lot:" + rack.getLotdetailId());
                                orderDetails.add(detail);

                                // Trừ tồn kho
                                productDAO.deductRackLotQuantity(rack.getRacklotId(), takeQty);
                                productDAO.deductLotDetailRemaining(rack.getLotdetailId(), takeQty);

                                remaining -= takeQty;
                            }
                        }
                    }

                    // ===== BƯỚC 3: Nếu vẫn không đủ =====
                    if (remaining > 0) {
                        request.setAttribute("errorMessage",
                                "Not enough stock for product " + productId + ". Short by: " + remaining);
                        doGet(request, response);
                        return;
                    }
                }
            }

            // Lưu Order và OrderDetails
            boolean success = orderDAO.createOutboundOrder(order, orderDetails);

            if (success) {
                response.sendRedirect("outboundmanager?success=true");
            } else {
                request.setAttribute("errorMessage", "Failed to create outbound order");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}