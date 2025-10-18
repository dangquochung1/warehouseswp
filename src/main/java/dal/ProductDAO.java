package dal;

import model.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO extends DBContext {

    public Map<String, String> getProductNameMap() {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT productid, name FROM [dbo].[product]";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("productid"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }
}
