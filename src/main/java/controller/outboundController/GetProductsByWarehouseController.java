package controller.outboundController;

import dal.WarehouseDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetProductsByWarehouseController", urlPatterns = {"/getProductsByWarehouse"})
public class GetProductsByWarehouseController extends HttpServlet {
    private WarehouseDAO warehouseDAO;

    @Override
    public void init() {
        warehouseDAO = new WarehouseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        String warehouseId = request.getParameter("warehouseId");

        try (PrintWriter out = response.getWriter()) {
            if (warehouseId == null || warehouseId.isEmpty()) {
                out.print("[]");
                return;
            }

            List<Product> products = warehouseDAO.getProductsByWarehouseId(warehouseId);

            String json = "[";
            for (int i = 0; i < products.size(); i++) {
                Product p = products.get(i);
                // thêm aisleId nếu cần thiết
                json += String.format("{\"productId\":\"%s\",\"productName\":\"%s\",\"rackId\":\"%s\"}",
                        p.getProductId(), p.getName(), p.getRackId());
                if (i < products.size() - 1) json += ",";
            }
            json += "]";
            out.print(json);
        }
    }
}
