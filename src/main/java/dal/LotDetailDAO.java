package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.LotDetail;

public class LotDetailDAO extends DBContext {

    // 1️⃣ Lấy toàn bộ LotDetail
    public List<LotDetail> getAllLotDetails() {
        String sql = "SELECT * FROM [dbo].[lotdetail]";
        List<LotDetail> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LotDetail l = new LotDetail();
                l.setLotDetailId(rs.getString("lotdetail_id"));
                l.setLotId(rs.getString("lot_id"));
                l.setProductId(rs.getString("product_id"));
                l.setPurchasePrice(rs.getDouble("purchase_price"));
                l.setQuantityTotal(rs.getInt("quantity_total"));
                l.setQuantityRemaining(rs.getInt("quantity_remaining"));
                list.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 2️⃣ Lấy các LotDetail theo lotId
    public List<LotDetail> getLotDetailsByLotId(String lotId) {
        String sql = "SELECT * FROM [dbo].[lotdetail] WHERE lot_id = ?";
        List<LotDetail> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LotDetail l = new LotDetail();
                l.setLotDetailId(rs.getString("lotdetail_id"));
                l.setLotId(rs.getString("lot_id"));
                l.setProductId(rs.getString("product_id"));
                l.setPurchasePrice(rs.getDouble("purchase_price"));
                l.setQuantityTotal(rs.getInt("quantity_total"));
                l.setQuantityRemaining(rs.getInt("quantity_remaining"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 3️⃣ Thêm mới LotDetail
    public int insertLotDetail(LotDetail l) {
        String sql = """
            INSERT INTO [dbo].[lotdetail]
            (lotdetail_id, lot_id, product_id, purchase_price, quantity_total, quantity_remaining)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, l.getLotDetailId());
            ps.setString(2, l.getLotId());
            ps.setString(3, l.getProductId());
            ps.setDouble(4, l.getPurchasePrice());
            ps.setInt(5, l.getQuantityTotal());
            ps.setInt(6, l.getQuantityRemaining());
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 4️⃣ Tìm 1 LotDetail theo ID
    public LotDetail getLotDetailById(String lotDetailId) {
        String sql = "SELECT * FROM [dbo].[lotdetail] WHERE lotdetail_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotDetailId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LotDetail l = new LotDetail();
                l.setLotDetailId(rs.getString("lotdetail_id"));
                l.setLotId(rs.getString("lot_id"));
                l.setProductId(rs.getString("product_id"));
                l.setPurchasePrice(rs.getDouble("purchase_price"));
                l.setQuantityTotal(rs.getInt("quantity_total"));
                l.setQuantityRemaining(rs.getInt("quantity_remaining"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 5️⃣ Cập nhật LotDetail
    public void updateLotDetail(LotDetail l) {
        String sql = """
            UPDATE [dbo].[lotdetail]
            SET lot_id = ?, product_id = ?, purchase_price = ?, quantity_total = ?, quantity_remaining = ?
            WHERE lotdetail_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, l.getLotId());
            ps.setString(2, l.getProductId());
            ps.setDouble(3, l.getPurchasePrice());
            ps.setInt(4, l.getQuantityTotal());
            ps.setInt(5, l.getQuantityRemaining());
            ps.setString(6, l.getLotDetailId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 6️⃣ Xóa LotDetail
    public int deleteLotDetail(String lotDetailId) {
        String sql = "DELETE FROM [dbo].[lotdetail] WHERE lotdetail_id = ?";
        int n = 0;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, lotDetailId);
            n = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n;
    }

    // 7️⃣ Giảm số lượng còn lại
    public void decreaseRemainingQuantity(String lotDetailId, int amount) {
        String sql = """
            UPDATE [dbo].[lotdetail]
            SET quantity_remaining = quantity_remaining - ?
            WHERE lotdetail_id = ?
            """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setString(2, lotDetailId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔍 Test
    public static void main(String[] args) {
        LotDetailDAO dao = new LotDetailDAO();
        List<LotDetail> list = dao.getAllLotDetails();
        for (LotDetail l : list) {
            System.out.println(l.toString());
        }
    }
}
