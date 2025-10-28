package dal;

import model.Product;
import model.ProductWithWarehouses;
import model.WarehouseStock;
import model.Warehouse;
import model.RackLotStock; // Cần import các model khác nếu chúng được sử dụng trong các phương thức.

import java.sql.*;
import java.util.*;

public class ProductDAO extends DBContext {

    // Lấy Map<ProductID, ProductName>
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

    // Đếm tổng số sản phẩm (để tính phân trang)
    public int getTotalProducts(String searchKeyword) {
        // Chú ý: Cần điều chỉnh câu lệnh SQL để phù hợp với cách dùng tham số '?'
        // Dùng `OR name LIKE ? OR productid LIKE ?` sẽ cần 2 tham số, không cần tham số đầu tiên `? IS NULL` nếu dùng cách này.
        // Cách dùng dưới đây là một pattern SQL phổ biến để xử lý tham số tìm kiếm tùy chọn:
        String sql = "SELECT COUNT(*) as total FROM product " +
                "WHERE ? IS NULL OR name LIKE ? OR productid LIKE ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            String searchPattern = (searchKeyword != null && !searchKeyword.trim().isEmpty())
                    ? "%" + searchKeyword.trim() + "%"
                    : null;

            // Nếu searchPattern là null, tham số 1 sẽ là NULL, điều kiện đầu tiên `? IS NULL` sẽ đúng,
            // còn 2 tham số tiếp theo cũng là NULL nhưng điều kiện LIKE với NULL có thể không hoạt động như mong muốn.
            // *Cách fix SQL/Binding Parameter tốt hơn*:
            // WHERE (? IS NULL OR name LIKE ? OR productid LIKE ?)
            // BIND: ps.setString(1, searchKeyword); ps.setString(2, searchPattern); ps.setString(3, searchPattern);
            // => Nếu searchKeyword là null, điều kiện đầu sẽ là NULL IS NULL, 2 điều kiện sau là name LIKE NULL (False)
            // => Nếu searchKeyword không null, điều kiện đầu sẽ là 'kw' IS NULL (False), 2 điều kiện sau sẽ là name LIKE '%kw%' (True/False)
            // Tuy nhiên, cách binding trong code gốc lại là:
            // String searchPattern = (searchKeyword != null && !searchKeyword.trim().isEmpty()) ? "%" + searchKeyword.trim() + "%" : null;
            // ps.setString(1, searchPattern);
            // ps.setString(2, searchPattern);
            // ps.setString(3, searchPattern);
            // Dù không tối ưu, tôi vẫn giữ logic bind parameter từ code gốc.

            if (searchPattern == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
                ps.setNull(2, java.sql.Types.VARCHAR);
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                ps.setString(1, searchPattern);
                ps.setString(2, searchPattern);
                ps.setString(3, searchPattern);
            }

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Phương thức cũ giữ lại cho tương thích
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT productid, name FROM product ORDER BY name";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productid"));
                product.setName(rs.getString("name"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product getProductById(String productId) {
        String sql = "SELECT * FROM product WHERE productid = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productid"));
                product.setName(rs.getString("name"));
                product.setAvgPrice(rs.getDouble("avgprice"));
                product.setDescription(rs.getString("description"));
                return product;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<ProductWithWarehouses> getProductsWithWarehouses(String search, int page, int pageSize) {
        List<ProductWithWarehouses> list = new ArrayList<>();
        Map<String, ProductWithWarehouses> map = new HashMap<>();

        String base =
                "WITH stock AS ( " +
                        "  SELECT " +
                        "    p.productid, " +
                        "    w.warehouseid, " +
                        "    w.name AS warehouse_name, " +
                        "    r.aisleid, " + // Thêm aisleid vào CTE
                        "    SUM(ISNULL(rl.quantity,0)) AS qty_in_warehouse " +
                        "  FROM product p " +
                        "  LEFT JOIN lotdetail ld  ON ld.product_id = p.productid " +
                        "  LEFT JOIN racklot rl    ON rl.lotdetail_id = ld.lotdetail_id " +
                        "  LEFT JOIN rack r        ON r.rackid = rl.rack_id " +
                        "  LEFT JOIN aisle ai      ON ai.aisleid = r.aisleid " +
                        "  LEFT JOIN area ar       ON ar.areaid = ai.areaid " +
                        "  LEFT JOIN warehouse w   ON w.warehouseid = ar.warehouseid " +
                        "  GROUP BY p.productid, w.warehouseid, w.name, r.aisleid " + // Thêm vào GROUP BY
                        ") " +
                        "SELECT p.productid, p.name, p.avgprice, p.description, " +
                        "      s.warehouseid, s.warehouse_name, s.aisleid, ai.name as aisleName, " +
                        "      ISNULL(s.qty_in_warehouse,0) AS qty, " +
                        "      (SELECT MIN(purchase_price) FROM lotdetail WHERE product_id = p.productid) as lowestPrice " +
                        "FROM product p " +
                        "LEFT JOIN stock s ON s.productid = p.productid " +
                        "LEFT JOIN aisle ai ON ai.aisleid = s.aisleid " + // JOIN với aisle để lấy tên
                        "WHERE ( ? IS NULL OR p.name LIKE '%' + ? + '%' OR p.productid LIKE '%' + ? + '%' ) " +
                        "ORDER BY p.name OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        String kw = (search == null || search.trim().isEmpty()) ? null : search.trim();
        int offset = Math.max(0, (page - 1)) * pageSize;

        try (PreparedStatement ps = connection.prepareStatement(base)) {
            if (kw == null) {
                ps.setNull(1, java.sql.Types.VARCHAR);
                ps.setNull(2, java.sql.Types.VARCHAR);
                ps.setNull(3, java.sql.Types.VARCHAR);
            } else {
                // Trong SQL Server, LIKE '%' + ? + '%' hoạt động tốt khi bind String
                ps.setString(1, kw);
                ps.setString(2, kw);
                ps.setString(3, kw);
            }
            ps.setInt(4, offset);
            ps.setInt(5, pageSize);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String pid = rs.getString("productid");
                    ProductWithWarehouses p = map.get(pid);
                    if (p == null) {
                        p = new ProductWithWarehouses();
                        p.setProductId(pid);
                        p.setName(rs.getString("name"));
                        p.setLowestPrice(rs.getDouble("lowestPrice"));
                        p.setAvgPrice(rs.getDouble("avgprice"));
                        p.setDescription(rs.getString("description"));
                        map.put(pid, p);
                    }
                    String wid = rs.getString("warehouseid");
                    if (wid != null) {
                        WarehouseStock ws = new WarehouseStock(
                                wid,
                                rs.getString("warehouse_name"),
                                rs.getInt("qty")
                        );
                        ws.setAisleId(rs.getString("aisleid"));
                        ws.setAisleName(rs.getString("aisleName"));
                        p.getWarehouses().add(ws);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.addAll(map.values());
        return list;
    }


    // Kiểm tra tồn ở 1 kho
    public int getQtyInWarehouse(String productId, String warehouseId) {
        String sql = "SELECT ISNULL(SUM(qty_in_warehouse),0) " +
                "FROM vw_ProductWarehouseStock WHERE productid=? AND warehouseid=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setString(2, warehouseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // Tìm RANDOM 1 kho khác có đủ số lượng
    public Warehouse findRandomWarehouseWithQty(String productId, int requiredQty, String excludeWarehouseId) {
        String sql =
                "SELECT TOP 1 WITH TIES w.warehouseid, w.name " +
                        "FROM vw_ProductWarehouseStock v " +
                        "JOIN warehouse w ON w.warehouseid = v.warehouseid " +
                        "WHERE v.productid = ? AND v.qty_in_warehouse >= ? " +
                        (excludeWarehouseId != null ? "AND v.warehouseid <> ? " : "") +
                        "ORDER BY NEWID()";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setInt(2, requiredQty);

            int paramIndex = 3;
            if (excludeWarehouseId != null) {
                ps.setString(paramIndex, excludeWarehouseId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Warehouse w = new Warehouse();
                    w.setWarehouseId(rs.getString(1));
                    w.setName(rs.getString(2));
                    return w;
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // Lấy danh sách rack theo FIFO (lot cũ nhất trước) của 1 sản phẩm tại 1 kho
    public List<RackLotStock> getRackLotsFIFO(String productId, String warehouseId) {
        List<RackLotStock> result = new ArrayList<>();
        String sql =
                "SELECT rl.racklot_id, rl.rack_id, rl.lotdetail_id, rl.quantity, " +
                        "      ld.lotdetail_id, r.aisleid, ai.name as aisle_name, w.warehouseid " +
                        "FROM racklot rl " +
                        "JOIN lotdetail ld ON ld.lotdetail_id = rl.lotdetail_id " +
                        "JOIN rack r ON r.rackid = rl.rack_id " +
                        "JOIN aisle ai ON ai.aisleid = r.aisleid " +
                        "JOIN area ar ON ar.areaid = ai.areaid " +
                        "JOIN warehouse w ON w.warehouseid = ar.warehouseid " +
                        "WHERE ld.product_id = ? AND w.warehouseid = ? AND rl.quantity > 0 " +
                        "ORDER BY ld.lotdetail_id ASC, rl.racklot_id ASC";  // Sắp xếp theo lot ID (cũ trước)

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, productId);
            ps.setString(2, warehouseId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    RackLotStock stock = new RackLotStock();
                    stock.setRacklotId(rs.getString("racklot_id"));
                    stock.setRackId(rs.getString("rack_id"));
                    stock.setLotdetailId(rs.getString("lotdetail_id"));
                    stock.setQuantity(rs.getInt("quantity"));
                    stock.setAisleId(rs.getString("aisleid"));
                    stock.setAisleName(rs.getString("aisle_name"));
                    stock.setWarehouseId(rs.getString("warehouseid"));
                    result.add(stock);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // Trừ tồn kho thực tế tại racklot
    public boolean deductRackLotQuantity(String racklotId, int qtyToDeduct) {
        String sql = "UPDATE racklot SET quantity = quantity - ? WHERE racklot_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, qtyToDeduct);
            ps.setString(2, racklotId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Trừ quantity_remaining trong lotdetail
    public boolean deductLotDetailRemaining(String lotdetailId, int qtyToDeduct) {
        String sql = "UPDATE lotdetail SET quantity_remaining = quantity_remaining - ? WHERE lotdetail_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, qtyToDeduct);
            ps.setString(2, lotdetailId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}