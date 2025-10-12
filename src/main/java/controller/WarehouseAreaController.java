package controller;

import dal.WarehouseDAO;
import dal.AreaDAO;
import jakarta.servlet.annotation.WebServlet;
import model.Warehouse;
import model.Area;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/WarehouseAreaController")
public class WarehouseAreaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WarehouseDAO warehouseDAO = new WarehouseDAO();
        AreaDAO areaDAO = new AreaDAO();

        // Lấy toàn bộ Warehouse
        List<Warehouse> warehouses = warehouseDAO.getAllWarehouses();
        request.setAttribute("warehouses", warehouses);

        // Lấy tham số warehouse được chọn
        String warehouseId = request.getParameter("warehouse");
        if (warehouseId == null && !warehouses.isEmpty()) {
            warehouseId = warehouses.get(0).getWarehouseId(); // mặc định chọn warehouse đầu tiên
        }

        // Lấy toàn bộ Area theo warehouse đã chọn
        List<Area> areas = areaDAO.getAreasByWarehouseId(warehouseId);
        request.setAttribute("areas", areas);

        // Lấy tham số area được chọn
        String areaId = request.getParameter("area");
        if (areaId == null && !areas.isEmpty()) {
            areaId = areas.get(0).getAreaId(); // mặc định chọn area đầu tiên
        }

        // Gửi dữ liệu sang JSP
        request.setAttribute("selectedWarehouse", warehouseId);
        request.setAttribute("selectedArea", areaId);

        request.getRequestDispatcher("rack-detail.jsp").forward(request, response);
    }
}
