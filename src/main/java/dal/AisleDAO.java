package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Aisle;

public class AisleDAO extends DBContext {

    public List<Aisle> getAislesByAreaId(String areaId) {
        List<Aisle> list = new ArrayList<>();
        String sql = "SELECT aisleid, areaid, name, description FROM aisle WHERE areaid = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setString(1, areaId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Aisle a = new Aisle();
                    a.setAisleId(rs.getString("aisleid"));
                    a.setAreaId(rs.getString("areaid"));
                    a.setName(rs.getString("name"));
                    a.setDescription(rs.getString("description"));
                    list.add(a);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static void main(String[] args) {
        AisleDAO dao = new AisleDAO();

        // 🔹 Nhập areaId bạn muốn test
        String areaId = "C003";

        List<Aisle> aisles = dao.getAislesByAreaId(areaId);

        if (aisles.isEmpty()) {
            System.out.println("Không tìm thấy aisle nào trong areaId = " + areaId);
        } else {
            for (Aisle a : aisles) {
                System.out.println(a);
            }
        }
    }
}
