package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import model.LotDetail;
import service.RackLotService;
import dal.LotDetailDAO;

@WebServlet("/RackLotController")
public class RackLotController extends HttpServlet {
    private RackLotService rackLotService = new RackLotService();
    private LotDetailDAO lotDetailDAO = new LotDetailDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        doPost(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        String warehouseId = req.getParameter("warehouseId");
        String lotId = req.getParameter("lotId");
        String lotDetailId = req.getParameter("lotDetailId");

        try {
            if (lotId != null && !lotId.isEmpty()) {
                // 🟢 Nếu có lotId -> xử lý toàn bộ LotDetail trong Lot
                List<LotDetail> details = lotDetailDAO.getLotDetailsByLotId(lotId);
                for (LotDetail d : details) {
                    rackLotService.autoDistribute(warehouseId, d.getLotDetailId());
                }
            } else if (lotDetailId != null && !lotDetailId.isEmpty()) {
                // 🟡 Nếu chỉ có lotDetailId -> xử lý 1 LotDetail
                rackLotService.autoDistribute(warehouseId, lotDetailId);
            } else {
                throw new Exception("Thiếu tham số lotId hoặc lotDetailId");
            }

            res.sendRedirect("success.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
