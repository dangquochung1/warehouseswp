package dal;

import model.Orders;
import model.OrderDetail;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends DBContext {
    Connection conn = connection;
    PreparedStatement psOrder = null;
    PreparedStatement psDetail = null;
    public boolean createOutboundOrder(Orders order, List<OrderDetail> details) {


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
    public boolean updateOrderStatus(String orderId, String newStatus) {


        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newStatus);
            ps.setString(2, orderId);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Product> getProductsByOrderId(String odid) {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT DISTINCT " +
                "p.productid, " +
                "p.name AS productName, " +
                "p.avgprice AS avgPrice, " +
                "a.aisleid AS aisleId, " +
                "a.name AS aisleName, " +
                "od.quantity_expected, " +
                "(" +
                "    SELECT MIN(ld2.purchase_price)" +
                "    FROM lotdetail ld2" +
                "    WHERE ld2.product_id = p.productid" +
                ") AS lowestPrice " +
                "FROM orderdetail od " +
                "JOIN product p ON od.product_id = p.productid " +
                "LEFT JOIN lotdetail ld ON p.productid = ld.product_id " +
                "LEFT JOIN racklot rl ON rl.lotdetail_id = ld.lotdetail_id " +
                "LEFT JOIN rack r ON r.rackid = rl.rack_id " +
                "LEFT JOIN aisle a ON a.aisleid = r.aisleid " +
                "WHERE od.order_id = ?";


        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, odid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productid"));
                product.setName(rs.getString("productName"));
                product.setAisleId(rs.getString("aisleId"));
                product.setAisleName(rs.getString("aisleName"));
                product.setLowestPrice(rs.getDouble("lowestPrice"));
                product.setAvgPrice(rs.getDouble("avgPrice"));
                product.setQuantity_expected(rs.getInt("quantity_expected"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}