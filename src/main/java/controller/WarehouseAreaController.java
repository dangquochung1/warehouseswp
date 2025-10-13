package controller;
import dal.WarehouseDAO;
import dal.AreaDAO;
import dal.AisleDAO;
import dal.RackDAO;
import jakarta.servlet.annotation.WebServlet;
import model.Warehouse;
import model.Area;
import model.Aisle;
import model.Rack;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/WarehouseAreaController")
public class WarehouseAreaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        WarehouseDAO warehouseDAO = new WarehouseDAO();
        AreaDAO areaDAO = new AreaDAO();
        AisleDAO aisleDAO = new AisleDAO();
        RackDAO rackDAO = new RackDAO();

        // --- WAREHOUSE ---
        List<Warehouse> warehouses = warehouseDAO.getAllWarehouses();
        request.setAttribute("warehouses", warehouses);

        String warehouseId = request.getParameter("warehouse");
        if (warehouseId == null && !warehouses.isEmpty()) {
            warehouseId = warehouses.get(0).getWarehouseId();
        }

        // --- AREA ---
        List<Area> areas = areaDAO.getAreasByWarehouseId(warehouseId);
        request.setAttribute("areas", areas);

        String areaId = request.getParameter("area");
        if (areaId == null && !areas.isEmpty()) {
            areaId = areas.get(0).getAreaId();
        }

        // --- AISLE ---
        List<Aisle> aisles = aisleDAO.getAislesByAreaId(areaId);
        request.setAttribute("aisles", aisles);

        String aisleId = request.getParameter("aisle");
        if (aisleId == null && !aisles.isEmpty()) {
            aisleId = aisles.get(0).getAisleId();
        }

        // --- RACK ---
        List<Rack> racks = new ArrayList<>();
        for (Aisle a : aisles) {
            racks.addAll(rackDAO.getRacksByAisleId(a.getAisleId()));
        }
        request.setAttribute("racks", racks);

        // Gửi dữ liệu sang JSP
        request.setAttribute("selectedWarehouse", warehouseId);
        request.setAttribute("selectedArea", areaId);
        request.setAttribute("selectedAisle", aisleId);

        request.getRequestDispatcher("rack-detail.jsp").forward(request, response);
    }
}
