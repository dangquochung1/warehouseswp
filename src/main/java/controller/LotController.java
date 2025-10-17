package controller;

import dal.LotDAO;
import dal.LotDetailDAO;
import dal.ProductDAO;
import dal.SupplierDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;
import model.Lot;
import model.LotDetail;
import model.Supplier;

@WebServlet("/LotController")
public class LotController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            LotDAO lotDAO = new LotDAO();
            LotDetailDAO lotDetailDAO = new LotDetailDAO();
            SupplierDAO supplierDAO = new SupplierDAO();

            // Lấy toàn bộ Lot
            List<Lot> lotList = lotDAO.getAllLots();

            // Lấy tất cả Supplier và map sang tên
            Map<String, String> supplierMap = new HashMap<>();
            for (Supplier s : supplierDAO.getAllSuppliers()) {
                supplierMap.put(s.getSupplierId(), s.getName());
            }

            // Map lotId → list LotDetail
            Map<String, List<LotDetail>> lotDetailsMap = new HashMap<>();
            for (Lot l : lotList) {
                List<LotDetail> details = lotDetailDAO.getLotDetailsByLotId(l.getLotId());
                lotDetailsMap.put(l.getLotId(), details);
            }
            ProductDAO productDAO = new ProductDAO();
            Map<String, String> productMap = productDAO.getProductNameMap();
            request.setAttribute("productMap", productMap);
            // Gửi dữ liệu sang JSP
            request.setAttribute("lotList", lotList);
            request.setAttribute("supplierMap", supplierMap);
            request.setAttribute("lotDetailsMap", lotDetailsMap);

            RequestDispatcher rd = request.getRequestDispatcher("lot.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("<h3>Lỗi Controller: " + e.getMessage() + "</h3>");
        }
    }
}
