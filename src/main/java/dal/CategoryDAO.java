package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CategoryDAO extends DBContext  {
    public Map<String, String> getCategoryNameMap() {
        Map<String, String> map = new HashMap<>();
        String sql = "SELECT category_id, name FROM category";
        try (PreparedStatement st = connection.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString("category_id"), rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

}
