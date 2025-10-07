package dal;

import model.Orders;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OutboundDAO {

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

    public List<Orders> getAllOutboundOrders() {
        List<Orders> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE type = 'outbound'";
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


    public int getTotalOutboundNumber() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM orders WHERE type = 'outbound'";
        try {
            conn = getSafeConnection(); // 🔹 Dùng connection tạm
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return total;
    }
    public int getTotalPendingNumber() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM Orders WHERE type = 'Outbound' AND status = 'pending'";
        try {
            conn = getSafeConnection(); // 🔹 Dùng connection tạm
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return total;
    }
    public int getTotalInProgressNumber() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM Orders WHERE type = 'Outbound' AND status = 'processing'";
        try {
            conn = getSafeConnection(); // 🔹 Dùng connection tạm
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return total;
    }
    public int getTotalCompletedNumber() {
        int total = 0;
        String query = "SELECT COUNT(*) AS total FROM Orders WHERE type = 'Outbound' AND status = 'done'";
        try {
            conn = getSafeConnection(); // 🔹 Dùng connection tạm
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }
        return total;
    }

    public static void main(String[] args) {
        OutboundDAO dao = new OutboundDAO();
        List<Orders> list = dao.getAllOutboundOrders();

        if (list.isEmpty()) {
            System.out.println("⚠️ Không có dữ liệu outbound orders trong database!");
        } else {
            System.out.println("✅ Danh sách outbound orders:");
            for (Orders o : list) {
                System.out.println(
                        "Order ID: " + o.getOrderId() +
                                " | Create Date: " + o.getCreatedAt() +
                                " | Create By: " + o.getCreatedBy() +
                                " | Assigned To: " + o.getAssignedTo() +
                                " | Status: " + o.getStatus()
                );
            }
        }

        // Kiểm tra thống kê
        System.out.println("\n📊 Thống kê số lượng:");
        System.out.println("Tổng outbound: " + dao.getTotalOutboundNumber());
        System.out.println("Pending: " + dao.getTotalPendingNumber());
        System.out.println("Processing: " + dao.getTotalInProgressNumber());
        System.out.println("Done: " + dao.getTotalCompletedNumber());
    }
}
