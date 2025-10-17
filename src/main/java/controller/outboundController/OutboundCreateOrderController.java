/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controller.outboundController;

import java.io.IOException;
import java.io.PrintWriter;

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
            request.getRequestDispatcher("OutboundCreateOrder.jsp").forward(request, response);

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
//            User currentUser = (User) session.getAttribute("currentUser");
//
//            if (currentUser == null) {
//                response.sendRedirect("login.jsp");
//                return;
//            }

            // Lấy thông tin từ form
            String location = request.getParameter("location");
            String responsibleStaff = request.getParameter("responsibleStaff");
            String orderDate = request.getParameter("orderDate");
            String expectedShipDate = request.getParameter("expectedShipDate");

            // Lấy danh sách sản phẩm
            String[] productIds = request.getParameterValues("productId");
            String[] productNames = request.getParameterValues("productName");
            String[] quantities = request.getParameterValues("quantity");
            String[] notes = request.getParameterValues("note");
            String[] aisleIds = request.getParameterValues("aisleId");
            // Tạo Order object
            Orders order = new Orders();
            order.setType("outbound");
            order.setCreatedBy("U002"); // Thay "U002" bằng user hiện tại nếu có
            order.setAssignedTo(responsibleStaff);
            order.setScheduledDate(java.sql.Date.valueOf(orderDate));
            order.setStatus("pending");
            order.setNote("Created from web interface");

            // Tạo danh sách OrderDetail
            List<OrderDetail> orderDetails = new ArrayList<>();

            if (productIds != null && productIds.length > 0) {
                for (int i = 0; i < productIds.length; i++) {
                    OrderDetail detail = new OrderDetail();
                    detail.setProductId(productIds[i]);
                    detail.setQuantity_actual(Integer.parseInt(quantities[i]));
                    detail.setNote(notes != null && i < notes.length ? notes[i] : "");
                    detail.setAisleId(aisleIds[i]);
                    orderDetails.add(detail);
                }
            }

            // Lưu vào database
            boolean success = orderDAO.createOutboundOrder(order, orderDetails);

            if (success) {
                session.setAttribute("successMessage", "Outbound order created successfully!");
                response.sendRedirect("outboundmanager");
            } else {
                request.setAttribute("errorMessage", "Failed to create outbound order!");
                doGet(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            doGet(request, response);
        }
    }
}