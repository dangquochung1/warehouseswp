package controller;

import dal.*;
import jakarta.servlet.annotation.WebServlet;
import model.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.LinkedHashMap;

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
        // ... (Logic xác định areaId) ...
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
        // ... (Logic xác định aisleId) ...
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


        // --- CHUYỂN LOGIC XỬ LÝ LOT INFO VÀO ĐÂY ---
        RackLotDAO rackLotDAO = new RackLotDAO();
        Map<String, String> rackLotInfoFormatted = new HashMap<>(); // Dùng Map<RackId, Chuỗi đã định dạng>

        for (Rack r : racks) {
            // 1. Lấy dữ liệu thô
            List<Map<String, Object>> lotList = rackLotDAO.getLotInfoByRack(r.getRackId());
            StringBuilder lotInfoStr = new StringBuilder();

            // 2. Định dạng chuỗi (Logic đã từng nằm trong JSP)
            if (lotList != null && !lotList.isEmpty()) {
                for (Map<String, Object> lot : lotList) {
                    lotInfoStr.append("LotID: ").append(lot.get("lotId"))
                            .append(" - LotDetailID: ").append(lot.get("lotdetailId"))
                            .append(" - Supplier: ").append(lot.get("supplierId"))
                            .append(" - Qty: ").append(lot.get("quantity"))
                            .append("\n");
                }
            } else {
                lotInfoStr.append("Không có dữ liệu");
            }

            // 3. Lưu chuỗi đã định dạng
            rackLotInfoFormatted.put(r.getRackId(), lotInfoStr.toString());
        }

        request.setAttribute("rackLotInfoFormatted", rackLotInfoFormatted);


        // --- LOGIC TÍNH TOÁN CẤU TRÚC LƯỚI ---
        Map<String, List<Rack>> mapAisleToRacks = new LinkedHashMap<>();
        int maxRows = 0;

        for (Aisle a : aisles) {
            List<Rack> listRacks = new ArrayList<>();
            for (Rack r : racks) {
                if (r.getAisleId() != null && r.getAisleId().equals(a.getAisleId())) {
                    listRacks.add(r);
                }
            }
            mapAisleToRacks.put(a.getAisleId(), listRacks);
            if (listRacks.size() > maxRows) {
                maxRows = listRacks.size();
            }
        }

        int cols = aisles.size();

        // Gửi dữ liệu đã xử lý sang JSP
        request.setAttribute("mapAisleToRacks", mapAisleToRacks);
        request.setAttribute("maxRows", maxRows);
        request.setAttribute("cols", cols);

        // Gửi dữ liệu filter
        request.setAttribute("selectedWarehouse", warehouseId);
        request.setAttribute("selectedArea", areaId);
        request.setAttribute("selectedAisle", aisleId);
        String lotId = request.getParameter("lotId");
        if (lotId != null && !lotId.isEmpty()) {
            LotDAO lotDAO = new LotDAO();
            LotDetailDAO lotDetailDAO = new LotDetailDAO();
            ProductDAO productDAO = new ProductDAO();

            // Lấy thông tin Lot
            Lot lot = lotDAO.getLotById(lotId);
            // Lấy danh sách LotDetail
            List<LotDetail> lotDetails = lotDetailDAO.getLotDetailsByLotId(lotId);
            Map<String, String> productMap = productDAO.getProductNameMap();

            // Gửi qua JSP
            request.setAttribute("selectedLot", lot);
            request.setAttribute("selectedLotDetails", lotDetails);
            request.setAttribute("productMap", productMap);
        }

        request.getRequestDispatcher("location.jsp").forward(request, response);
    }
}