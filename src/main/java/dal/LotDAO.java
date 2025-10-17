package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Lot;

public class LotDAO extends DBContext {

    // 1️⃣ Lấy toàn bộ Lot
    public List<Lot> getAllLots() {
        String sql = "SELECT * FROM [dbo].[lot]";
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Lot l = new Lot();
                l.setLotId(rs.getString("lot_id"));
                l.setSupplierId(rs.getString("supplier_id"));
                l.setLotCode(rs.getString("lot_code"));
                l.setReceivedDate(rs.getDate("received_date"));
                l.setDescription(rs.getString("description"));
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Lấy Lot theo ID
    public Lot getLotById(String lotId) {
        String sql = "SELECT * FROM [dbo].[lot] WHERE lot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Lot l = new Lot();
                l.setLotId(rs.getString("lot_id"));
                l.setSupplierId(rs.getString("supplier_id"));
                l.setLotCode(rs.getString("lot_code"));
                l.setReceivedDate(rs.getDate("received_date"));
                l.setDescription(rs.getString("description"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 3️⃣ Thêm mới Lot
    public int insertLot(Lot l) {
        String sql = """
        INSERT INTO [dbo].[lot]
        (lot_id, supplier_id, lot_code, received_date, description)
        VALUES (?, ?, ?, ?, ?)
        """;
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, l.getLotId());
            ps.setString(2, l.getSupplierId());
            ps.setString(3, l.getLotCode());

            // ⚠️ Chuyển từ java.util.Date sang java.sql.Date
            if (l.getReceivedDate() != null) {
                ps.setDate(4, new java.sql.Date(l.getReceivedDate().getTime()));
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }

            ps.setString(5, l.getDescription());
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }


    // 4️⃣ Cập nhật Lot
    public void updateLot(Lot l) {
        String sql = """
        UPDATE [dbo].[lot]
        SET supplier_id = ?, lot_code = ?, received_date = ?, description = ?
        WHERE lot_id = ?
        """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, l.getSupplierId());
            ps.setString(2, l.getLotCode());

            // ⚠️ Ép kiểu tương tự
            if (l.getReceivedDate() != null) {
                ps.setDate(3, new java.sql.Date(l.getReceivedDate().getTime()));
            } else {
                ps.setNull(3, java.sql.Types.DATE);
            }

            ps.setString(4, l.getDescription());
            ps.setString(5, l.getLotId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // 5️⃣ Xóa Lot
    public int deleteLot(String lotId) {
        String sql = "DELETE FROM [dbo].[lot] WHERE lot_id = ?";
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotId);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 6️⃣ Tìm Lot theo supplier_id
    public List<Lot> getLotsBySupplier(String supplierId) {
        String sql = "SELECT * FROM [dbo].[lot] WHERE supplier_id = ?";
        List<Lot> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, supplierId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Lot l = new Lot();
                l.setLotId(rs.getString("lot_id"));
                l.setSupplierId(rs.getString("supplier_id"));
                l.setLotCode(rs.getString("lot_code"));
                l.setReceivedDate(rs.getDate("received_date"));
                l.setDescription(rs.getString("description"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 7️⃣ Tìm Lot theo mã code
    public Lot getLotByCode(String lotCode) {
        String sql = "SELECT * FROM [dbo].[lot] WHERE lot_code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotCode);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Lot l = new Lot();
                l.setLotId(rs.getString("lot_id"));
                l.setSupplierId(rs.getString("supplier_id"));
                l.setLotCode(rs.getString("lot_code"));
                l.setReceivedDate(rs.getDate("received_date"));
                l.setDescription(rs.getString("description"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔍 Test
    public static void main(String[] args) {
        LotDAO dao = new LotDAO();
        List<Lot> list = dao.getAllLots();
        for (Lot l : list) {
            System.out.println(l);
        }
    }
}
