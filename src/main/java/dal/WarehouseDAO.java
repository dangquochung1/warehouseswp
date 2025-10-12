package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Warehouse;

public class WarehouseDAO extends DBContext {

    // 1️⃣ Lấy toàn bộ Warehouse
    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getString("warehouseid"));
                warehouse.setName(rs.getString("name"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setDescription(rs.getString("description"));
                warehouses.add(warehouse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouses;
    }

    // 2️⃣ Lấy Warehouse theo ID
    public Warehouse getWarehouseById(String id) {
        String sql = "SELECT * FROM [dbo].[warehouse] WHERE warehouseid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Warehouse w = new Warehouse();
                w.setWarehouseId(rs.getString("warehouseid"));
                w.setName(rs.getString("name"));
                w.setLocation(rs.getString("location"));
                w.setDescription(rs.getString("description"));
                return w;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    // 3️⃣ Thêm mới Warehouse
//    public int insertWarehouse(Warehouse w) {
//        String sql = """
//            INSERT INTO [dbo].[warehouse] (warehouseid, name, location, description)
//            VALUES (?, ?, ?, ?)
//            """;
//        int n = 0;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, w.getWarehouseId());
//            ps.setString(2, w.getName());
//            ps.setString(3, w.getLocation());
//            ps.setString(4, w.getDescription());
//            n = ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n;
//    }

//    // 4️⃣ Cập nhật Warehouse
//    public void updateWarehouse(Warehouse w) {
//        String sql = """
//            UPDATE [dbo].[warehouse]
//            SET name = ?, location = ?, description = ?
//            WHERE warehouseid = ?
//            """;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, w.getName());
//            ps.setString(2, w.getLocation());
//            ps.setString(3, w.getDescription());
//            ps.setString(4, w.getWarehouseId());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

//    // 5️⃣ Xóa Warehouse
//    public int deleteWarehouse(String id) {
//        String sql = "DELETE FROM [dbo].[warehouse] WHERE warehouseid = ?";
//        int n = 0;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, id);
//            n = ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n;
//    }

    // 🔍 Test DAO
    public static void main(String[] args) {
        WarehouseDAO dao = new WarehouseDAO();

        System.out.println("=== Danh sách Warehouse ===");
        List<Warehouse> list = dao.getAllWarehouses();
        for (Warehouse w : list) {
            System.out.println(w.toString());
        }

    }
}
