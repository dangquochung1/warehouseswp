package dal;

import model.Orders;
import model.OrderDetail;
import java.sql.*;
import java.util.List;

public class OrderDAO extends DBContext {

    public boolean createOutboundOrder(Orders order, List<OrderDetail> details) {
        Connection conn = connection;
        PreparedStatement psOrder = null;
        PreparedStatement psDetail = null;

        try {

            conn.setAutoCommit(false);

            // Generate order ID
            String orderId = generateOrderId(conn);
            order.setOrderId(orderId);

            // Insert Order
            String sqlOrder = "INSERT INTO orders (order_id, type, created_by, assigned_to, " +
                    "scheduled_date, status, note, created_at) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, GETDATE())";

            psOrder = conn.prepareStatement(sqlOrder);
            psOrder.setString(1, order.getOrderId());
            psOrder.setString(2, order.getType());
            psOrder.setString(3, order.getCreatedBy());
            psOrder.setString(4, order.getAssignedTo());
            psOrder.setDate(5, (Date) order.getScheduledDate());
            psOrder.setString(6, order.getStatus());
            psOrder.setString(7, order.getNote());
            psOrder.executeUpdate();

            // Insert Order Details
            String sqlDetail = "INSERT INTO orderdetail (orderdetail_id, order_id, product_id, " +
                    "quantity_expected, note, aisleid) VALUES (?, ?, ?, ?, ?,?)";

            psDetail = conn.prepareStatement(sqlDetail);

            for (int i = 0; i < details.size(); i++) {
                OrderDetail detail = details.get(i);
                String detailId = orderId + "-D" + String.format("%03d", i + 1);

                psDetail.setString(1, detailId);
                psDetail.setString(2, orderId);
                psDetail.setString(3, detail.getProductId());
                psDetail.setInt(4, detail.getQuantity_expected());
                psDetail.setString(5, detail.getNote());
                psDetail.setString(6, detail.getAisleId());
                psDetail.addBatch();
            }

            psDetail.executeBatch();
            conn.commit();

            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (psDetail != null) psDetail.close();
                if (psOrder != null) psOrder.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateOrderId(Connection conn) throws SQLException {
        String sql = "SELECT TOP 1 order_id FROM orders ORDER BY order_id DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            if (rs.next()) {
                String lastId = rs.getString("order_id");
                int num = Integer.parseInt(lastId.substring(1)) + 1;
                return "O" + String.format("%03d", num);
            } else {
                return "O001";
            }
        }
    }
}