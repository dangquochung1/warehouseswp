package controller.outboundController;

import dal.ProductDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;
import model.ProductWithWarehouses;
import model.WarehouseStock;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetAllProductsController", urlPatterns = {"/getAllProducts"})
public class GetAllProductsController extends HttpServlet {
    private ProductDAO productDAO;

    @Override
    public void init() {
        productDAO = new ProductDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        String kw = request.getParameter("q");
        int page = parseIntOrDefault(request.getParameter("page"), 1);
        int size = parseIntOrDefault(request.getParameter("size"), 50);

        List<ProductWithWarehouses> products = productDAO.getProductsWithWarehouses(kw, page, size);

        StringBuilder json = new StringBuilder("[");
        for (int i = 0; i < products.size(); i++) {
            ProductWithWarehouses p = products.get(i);
            json.append("{")
                    .append("\"productId\":\"").append(escape(p.getProductId())).append("\",")
                    .append("\"productName\":\"").append(escape(p.getName())).append("\",")
                    .append("\"lowestPrice\":").append(p.getLowestPrice()).append(",")
                    .append("\"avgPrice\":").append(p.getAvgPrice()).append(",")
                    .append("\"warehouses\":[");
            for (int j = 0; j < p.getWarehouses().size(); j++) {
                WarehouseStock w = p.getWarehouses().get(j);
                json.append("{")
                        .append("\"warehouseId\":\"").append(escape(w.getWarehouseId())).append("\",")
                        .append("\"warehouseName\":\"").append(escape(w.getWarehouseName())).append("\",")
                        .append("\"quantity\":").append(w.getQuantity()).append(",")  // Thêm dấu phẩy
                        .append("\"aisleId\":\"").append(escape(w.getAisleId())).append("\",")
                        .append("\"aisleName\":\"").append(escape(w.getAisleName())).append("\"")
                        .append("}");
                if (j < p.getWarehouses().size() - 1) json.append(",");
            }
            json.append("]}");
            if (i < products.size() - 1) json.append(",");
        }
        json.append("]");
        response.getWriter().print(json.toString());
    }

    private int parseIntOrDefault(String s, int d) {
        try { return Integer.parseInt(s); } catch (Exception e) { return d; }
    }
    private String escape(String s) {
        if (s == null) return "";
        return s.replace("\\","\\\\").replace("\"","\\\"");
    }
}
