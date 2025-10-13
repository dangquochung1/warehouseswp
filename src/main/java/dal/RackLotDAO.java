package dal;

import java.sql.*;
import java.util.*;
import model.RackLot;

public class RackLotDAO extends DBContext {

    // 1️⃣ Lấy tất cả racklot
    public List<RackLot> getAllRackLots() {
        String sql = "SELECT * FROM [dbo].[racklot]";
        List<RackLot> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                RackLot r = new RackLot();
                r.setRacklotId(rs.getString("racklot_id"));
                r.setRackId(rs.getString("rack_id"));
                r.setLotdetailId(rs.getString("lotdetail_id"));
                r.setQuantity(rs.getInt("quantity"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Lấy danh sách RackLot theo rackId
    public List<RackLot> getRackLotsByRackId(String rackId) {
        String sql = "SELECT * FROM [dbo].[racklot] WHERE rack_id = ?";
        List<RackLot> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rackId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                RackLot r = new RackLot();
                r.setRacklotId(rs.getString("racklot_id"));
                r.setRackId(rs.getString("rack_id"));
                r.setLotdetailId(rs.getString("lotdetail_id"));
                r.setQuantity(rs.getInt("quantity"));
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3️⃣ Thêm mới RackLot
    public int insertRackLot(RackLot r) {
        String sql = """
            INSERT INTO [dbo].[racklot] 
            (racklot_id, rack_id, lotdetail_id, quantity)
            VALUES (?, ?, ?, ?)
            """;
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getRacklotId());
            ps.setString(2, r.getRackId());
            ps.setString(3, r.getLotdetailId());
            ps.setInt(4, r.getQuantity());
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 4️⃣ Cập nhật RackLot
    public void updateRackLot(RackLot r) {
        String sql = """
            UPDATE [dbo].[racklot]
            SET rack_id = ?, lotdetail_id = ?, quantity = ?
            WHERE racklot_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getRackId());
            ps.setString(2, r.getLotdetailId());
            ps.setInt(3, r.getQuantity());
            ps.setString(4, r.getRacklotId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5️⃣ Xóa RackLot
    public int deleteRackLot(String racklotId) {
        String sql = "DELETE FROM [dbo].[racklot] WHERE racklot_id = ?";
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, racklotId);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 6️⃣ Lấy danh sách sản phẩm + số lượng trong rack
    public List<Map<String, Object>> getProductQuantitiesByRack(String rackId) {
        String sql = """
            SELECT 
                p.productid,
                p.name AS product_name,
                ld.lotdetail_id,
                rl.quantity
            FROM racklot rl
            JOIN lotdetail ld ON rl.lotdetail_id = ld.lotdetail_id
            JOIN product p ON ld.product_id = p.productid
            WHERE rl.rack_id = ?
            """;
        List<Map<String, Object>> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rackId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Map<String, Object> map = new HashMap<>();
                map.put("productId", rs.getString("productid"));
                map.put("productName", rs.getString("product_name"));
                map.put("lotDetailId", rs.getString("lotdetail_id"));
                map.put("quantity", rs.getInt("quantity"));
                result.add(map);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 7️⃣ Lấy tổng số lượng hàng hiện có trong rack
    public int getTotalQuantityInRack(String rackId) {
        String sql = "SELECT SUM(quantity) AS total FROM [dbo].[racklot] WHERE rack_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rackId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 🔍 Main test
    public static void main(String[] args) {
        RackLotDAO dao = new RackLotDAO();

        System.out.println("=== Toàn bộ dữ liệu trong bảng RackLot ===");
        List<RackLot> allRackLots = dao.getAllRackLots();
        for (RackLot r : allRackLots) {
            System.out.println("RackLotID: " + r.getRacklotId() +
                    ", RackID: " + r.getRackId() +
                    ", LotDetailID: " + r.getLotdetailId() +
                    ", Quantity: " + r.getQuantity());
        }

        System.out.println("\n=== Kiểm tra chi tiết sản phẩm trong 1 rack ===");
        String testRackId = "R001"; // 👉 thay bằng rackid có thật trong DB
        List<Map<String, Object>> productList = dao.getProductQuantitiesByRack(testRackId);
        for (Map<String, Object> p : productList) {
            System.out.println("Rack: " + testRackId +
                    " | ProductID: " + p.get("productId") +
                    " | ProductName: " + p.get("productName") +
                    " | LotDetailID: " + p.get("lotDetailId") +
                    " | Quantity: " + p.get("quantity"));
        }

        System.out.println("\n=== Tổng số lượng trong rack " + testRackId + " ===");
        int total = dao.getTotalQuantityInRack(testRackId);
        System.out.println("Total quantity in rack " + testRackId + " = " + total);
    }
}                            
