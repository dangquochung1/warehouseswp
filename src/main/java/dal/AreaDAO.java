package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Area;

public class AreaDAO extends DBContext {

    // 1️⃣ Lấy toàn bộ Area
    public List<Area> getAllAreas() {
        String sql = "SELECT * FROM [dbo].[area]";
        List<Area> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Area a = new Area();
                a.setAreaId(rs.getString("areaid"));
                a.setWarehouseId(rs.getString("warehouseid"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                list.add(a);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Lấy Area theo warehouseId
    public List<Area> getAreasByWarehouseId(String warehouseId) {
        String sql = "SELECT * FROM [dbo].[area] WHERE warehouseid = ?";
        List<Area> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, warehouseId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Area a = new Area();
                a.setAreaId(rs.getString("areaid"));
                a.setWarehouseId(rs.getString("warehouseid"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

//    // 3️⃣ Thêm mới Area
//    public int insertArea(Area a) {
//        String sql = """
//            INSERT INTO [dbo].[area]
//            (areaid, warehouseid, name, description)
//            VALUES (?, ?, ?, ?)
//            """;
//        int n = 0;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, a.getAreaId());
//            ps.setString(2, a.getWarehouseId());
//            ps.setString(3, a.getName());
//            ps.setString(4, a.getDescription());
//            n = ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n;
//    }

    // 4️⃣ Lấy Area theo ID
    public Area getAreaById(String areaId) {
        String sql = "SELECT * FROM [dbo].[area] WHERE areaid = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, areaId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Area a = new Area();
                a.setAreaId(rs.getString("areaid"));
                a.setWarehouseId(rs.getString("warehouseid"));
                a.setName(rs.getString("name"));
                a.setDescription(rs.getString("description"));
                return a;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    // 5️⃣ Cập nhật Area
//    public void updateArea(Area a) {
//        String sql = """
//            UPDATE [dbo].[area]
//            SET warehouseid = ?, name = ?, description = ?
//            WHERE areaid = ?
//            """;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, a.getWarehouseId());
//            ps.setString(2, a.getName());
//            ps.setString(3, a.getDescription());
//            ps.setString(4, a.getAreaId());
//            ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    // 6️⃣ Xóa Area
//    public int deleteArea(String areaId) {
//        String sql = "DELETE FROM [dbo].[area] WHERE areaid = ?";
//        int n = 0;
//        try (PreparedStatement ps = connection.prepareStatement(sql)) {
//            ps.setString(1, areaId);
//            n = ps.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return n;
//    }

    // 🔍 Test DAO
    public static void main(String[] args) {
        AreaDAO dao = new AreaDAO();
        List<Area> list = dao.getAllAreas();
        for (Area a : list) {
            System.out.println(a.toString());
        }
    }
}
