package controller.outboundController;

import dal.UserDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "GetStaffByWarehouseController", urlPatterns = {"/getStaffByWarehouse"})
public class GetStaffByWarehouseController extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
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

            List<User> staffList = userDAO.getStaffByWarehouseId(warehouseId);

            String json = "[";
            for (int i = 0; i < staffList.size(); i++) {
                User s = staffList.get(i);
                json += String.format("{\"uid\":\"%s\",\"fullname\":\"%s\"}", s.getUid(), s.getFullname());
                if (i < staffList.size() - 1) json += ",";
            }
            json += "]";
            out.print(json);
        }
    }
}
