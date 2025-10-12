package dal;

import java.sql.*;
import java.util.*;
import model.RackLot;
public class RackScreenDAO extends DBContext{
    // lấy tất cả các rack(id,name), sum= tổng các racklot.rack_id=rack.rack_id
    public List<RackLot> getAllRackLots() {
        String sql = "SELECT \n" +
                "    r.rackid,\n" +
                "    r.name,\n" +
                "    ISNULL(SUM(rl.quantity), 0) AS sum\n" +
                "FROM \n" +
                "    [warehouseDB].[dbo].[rack] r\n" +
                "LEFT JOIN \n" +
                "    [warehouseDB].[dbo].[racklot] rl \n" +
                "    ON r.rackid = rl.rack_id\n" +
                "GROUP BY \n" +
                "    r.rackid, \n" +
                "    r.name\n" +
                "ORDER BY \n" +
                "    r.rackid;";
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
}
