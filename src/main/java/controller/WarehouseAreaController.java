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

        // --- WAREHOUSES ---
        List<Warehouse> warehouses = warehouseDAO.getAllWarehouses();
        request.setAttribute("warehouses", warehouses);

        String warehouseId = request.getParameter("warehouse");
        if ((warehouseId == null || warehouseId.isEmpty()) && !warehouses.isEmpty()) {
            warehouseId = warehouses.get(0).getWarehouseId();
        }

        // --- AREAS (tùy theo warehouseId) ---
        List<Area> areas = new ArrayList<>();
        if (warehouseId != null) {
            areas = areaDAO.getAreasByWarehouseId(warehouseId);
        }
        request.setAttribute("areas", areas);

        String areaId = request.getParameter("area");
        // Nếu areaId null hoặc không nằm trong list areas -> reset về area đầu tiên (nếu có)
        boolean areaValid = false;
        if (areaId != null && !areaId.isEmpty()) {
            for (Area a : areas) {
                if (areaId.equalsIgnoreCase(a.getAreaId())) {
                    areaValid = true;
                    break;
                }
            }
        }
        if (!areaValid && !areas.isEmpty()) {
            areaId = areas.get(0).getAreaId();
        }

        // --- AISLES (tùy theo areaId) ---
        List<Aisle> aisles = new ArrayList<>();
        if (areaId != null) {
            aisles = aisleDAO.getAislesByAreaId(areaId);
        }
        request.setAttribute("aisles", aisles);

        String aisleId = request.getParameter("aisle");
        // Validate aisleId tương tự
        boolean aisleValid = false;
        if (aisleId != null && !aisleId.isEmpty()) {
            for (Aisle ai : aisles) {
                if (aisleId.equalsIgnoreCase(ai.getAisleId())) {
                    aisleValid = true;
                    break;
                }
            }
        }
        if (!aisleValid && !aisles.isEmpty()) {
            aisleId = aisles.get(0).getAisleId();
        }

        // --- RACKS: lấy theo aisles hiện tại (toàn bộ aisles trong area) ---
        List<Rack> racks = new ArrayList<>();
        for (Aisle a : aisles) {
            List<Rack> tmp = rackDAO.getRacksByAisleId(a.getAisleId());
            if (tmp != null) racks.addAll(tmp);
        }
        request.setAttribute("racks", racks);

        // Gửi dữ liệu sang JSP
        request.setAttribute("selectedWarehouse", warehouseId);
        request.setAttribute("selectedArea", areaId);
        request.setAttribute("selectedAisle", aisleId);

        request.getRequestDispatcher("location.jsp").forward(request, response);
    }

}
