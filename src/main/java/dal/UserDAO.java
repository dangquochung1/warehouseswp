package dal;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends DBContext {

    public List<User> getUsersByRoleName(String roleName) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM [user] u " +
                "INNER JOIN role r ON u.rid = r.roleid " +
                "WHERE r.name = ? AND u.status = 'active'";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, roleName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getString("uid"));
                user.setUsername(rs.getString("username"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public List<User> getStaffByWarehouseId(String warehouseId) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT u.* FROM [user] u " +
                "INNER JOIN role r ON u.rid = r.roleid " +
                "WHERE r.name = 'Staff' AND u.wid = ? AND u.status = 'active'";

        try(PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, warehouseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setUid(rs.getString("uid"));
                user.setUsername(rs.getString("username"));
                user.setFullname(rs.getString("fullname"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                users.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}