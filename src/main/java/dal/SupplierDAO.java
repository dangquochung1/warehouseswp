package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Supplier;

public class SupplierDAO extends DBContext {

    // 1️⃣ Lấy toàn bộ Supplier
    public List<Supplier> getAllSuppliers() {
        String sql = "SELECT * FROM [dbo].[supplier]";
        List<Supplier> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getString("supplier_id"));
                s.setName(rs.getString("name"));
                s.setContactName(rs.getString("contact_name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setDescription(rs.getString("description"));
                list.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Lấy Supplier theo ID
    public Supplier getSupplierById(String supplierId) {
        String sql = "SELECT * FROM [dbo].[supplier] WHERE supplier_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, supplierId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getString("supplier_id"));
                s.setName(rs.getString("name"));
                s.setContactName(rs.getString("contact_name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setDescription(rs.getString("description"));
                return s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3️⃣ Thêm mới Supplier
    public int insertSupplier(Supplier s) {
        String sql = """
            INSERT INTO [dbo].[supplier]
            (supplier_id, name, contact_name, phone, email, address, description)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getSupplierId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getContactName());
            ps.setString(4, s.getPhone());
            ps.setString(5, s.getEmail());
            ps.setString(6, s.getAddress());
            ps.setString(7, s.getDescription());
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 4️⃣ Cập nhật Supplier
    public int updateSupplier(Supplier s) {
        String sql = """
            UPDATE [dbo].[supplier]
            SET name = ?, contact_name = ?, phone = ?, email = ?, address = ?, description = ?
            WHERE supplier_id = ?
            """;
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getContactName());
            ps.setString(3, s.getPhone());
            ps.setString(4, s.getEmail());
            ps.setString(5, s.getAddress());
            ps.setString(6, s.getDescription());
            ps.setString(7, s.getSupplierId());
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 5️⃣ Xóa Supplier
    public int deleteSupplier(String supplierId) {
        String sql = "DELETE FROM [dbo].[supplier] WHERE supplier_id = ?";
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, supplierId);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 6️⃣ Tìm Supplier theo tên gần đúng
    public List<Supplier> searchSuppliersByName(String keyword) {
        String sql = "SELECT * FROM [dbo].[supplier] WHERE name LIKE ?";
        List<Supplier> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Supplier s = new Supplier();
                s.setSupplierId(rs.getString("supplier_id"));
                s.setName(rs.getString("name"));
                s.setContactName(rs.getString("contact_name"));
                s.setPhone(rs.getString("phone"));
                s.setEmail(rs.getString("email"));
                s.setAddress(rs.getString("address"));
                s.setDescription(rs.getString("description"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🔍 Test nhanh
    public static void main(String[] args) {
        SupplierDAO dao = new SupplierDAO();
        List<Supplier> list = dao.getAllSuppliers();
        for (Supplier s : list) {
            System.out.println(s);
        }
    }
}
