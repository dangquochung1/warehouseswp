package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Rack;

public class RackDAO extends DBContext {

    // ✅ Lấy toàn bộ Rack kèm tổng quantity (sum)
    public List<Rack> getAllRacks() {
        List<Rack> list = new ArrayList<>();
        String sql = """
            SELECT 
                r.rackid,
                r.aisleid,
                r.name,
                r.description,
                ISNULL(SUM(rl.quantity), 0) AS sum
            FROM [warehouseDB].[dbo].[rack] r
            LEFT JOIN [warehouseDB].[dbo].[racklot] rl 
                ON r.rackid = rl.rack_id
            GROUP BY 
                r.rackid, r.aisleid, r.name, r.description
            ORDER BY r.rackid
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Rack r = new Rack();
                r.setRackId(rs.getString("rackid"));
                r.setAisleId(rs.getString("aisleid"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                r.setSum(rs.getInt("sum"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Lấy Rack theo Aisle ID
    public List<Rack> getRacksByAisleId(String aisleId) {
        List<Rack> list = new ArrayList<>();
        String sql = """
            SELECT 
                r.rackid,
                r.aisleid,
                r.name,
                r.description,
                ISNULL(SUM(rl.quantity), 0) AS sum
            FROM [warehouseDB].[dbo].[rack] r
            LEFT JOIN [warehouseDB].[dbo].[racklot] rl 
                ON r.rackid = rl.rack_id
            WHERE r.aisleid = ?
            GROUP BY 
                r.rackid, r.aisleid, r.name, r.description
            ORDER BY r.rackid
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, aisleId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Rack r = new Rack();
                r.setRackId(rs.getString("rackid"));
                r.setAisleId(rs.getString("aisleid"));
                r.setName(rs.getString("name"));
                r.setDescription(rs.getString("description"));
                r.setSum(rs.getInt("sum"));
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Lấy Rack theo ID
    public Rack getRackById(String rackId) {
        String sql = """
            SELECT 
                r.rackid,
                r.aisleid,
                r.name,
                r.description,
                ISNULL(SUM(rl.quantity), 0) AS sum
            FROM [warehouseDB].[dbo].[rack] r
            LEFT JOIN [warehouseDB].[dbo].[racklot] rl 
                ON r.rackid = rl.rack_id
            WHERE r.rackid = ?
            GROUP BY 
                r.rackid, r.aisleid, r.name, r.description
        """;

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, rackId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Rack(
                        rs.getString("rackid"),
                        rs.getString("aisleid"),
                        rs.getString("name"),
                        rs.getInt("sum"),
                        rs.getString("description")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    // ✅ Cập nhật Rack
    public void updateRack(Rack r) {
        String sql = "UPDATE [warehouseDB].[dbo].[rack] SET aisleid = ?, name = ?, description = ? WHERE rackid = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, r.getAisleId());
            st.setString(2, r.getName());
            st.setString(3, r.getDescription());
            st.setString(4, r.getRackId());
            st.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        RackDAO dao = new RackDAO();

        System.out.println("=== LẤY TOÀN BỘ RACK ===");
        List<Rack> racks = dao.getAllRacks();
        for (Rack r : racks) {
            System.out.println(r.getRackId() + " | " + r.getAisleId() + " | " + r.getName() + " | sum=" + r.getSum());
        }

        System.out.println("\n=== LẤY RACK THEO AISLE ID: A001-01 ===");
        List<Rack> racksByAisle = dao.getRacksByAisleId("A001-01");
        for (Rack r : racksByAisle) {
            System.out.println(r.getRackId() + " | " + r.getName() + " | sum=" + r.getSum());
        }

        System.out.println("\n=== LẤY CHI TIẾT RACK THEO ID: R001 ===");
        Rack rack = dao.getRackById("C003-06R1");
        if (rack != null) {
            System.out.println(rack.getRackId() + " | " + rack.getAisleId() + " | " + rack.getName() + " | sum=" + rack.getSum());
        } else {
            System.out.println("Không tìm thấy rack R001");
        }
    }
}
