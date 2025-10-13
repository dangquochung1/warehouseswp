package dal;


import model.OrderDetail;
import model.Orders;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OutboundStaffDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    private Connection getSafeConnection() {
        Connection connection = null;
        try {
            // Ghi đè URL để tắt SSL kiểm tra chứng chỉ
            String url = "jdbc:sqlserver://localhost:1433;databaseName=warehouseDB;encrypt=false;trustServerCertificate=true";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    public List<Orders> getAllOutboundOrdersForStaff() {
        List<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE type = 'outbound' AND status IN ('pending', 'processing');";
        try {
            conn = getSafeConnection(); // 🔹 Dùng connection tạm an toàn
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Orders(
                        rs.getString("order_id"),
                        rs.getString("type"),
                        rs.getString("created_by"),
                        rs.getString("assigned_to"),
                        rs.getDate("created_at"),
                        rs.getDate("scheduled_date"),
                        rs.getString("schedule_id"),
                        rs.getString("status"),
                        rs.getString("note")
                ));
            }     } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return list;
    }

}
