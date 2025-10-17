package dal;

import model.Product;
import model.Warehouse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO extends DBContext {

    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouse";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseId(rs.getString("warehouseid"));
                warehouse.setName(rs.getString("name"));
                warehouse.setLocation(rs.getString("location"));
                warehouse.setDescription(rs.getString("description"));
                warehouses.add(warehouse);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return warehouses;
    }

    public List<Product> getProductsByWarehouseId(String warehouseId) {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT DISTINCT p.productid, p.name AS productName, r.aisleid AS aisleid\n" +
                "FROM product p\n" +
                "JOIN lotdetail ld ON p.productid = ld.product_id\n" +
                "JOIN racklot rl ON rl.lotdetail_id = ld.lotdetail_id\n" +
                "JOIN aisle a ON a.aisleid = r.aisleid\n" +
                "JOIN area ar ON ar.areaid = a.areaid\n" +
                "JOIN warehouse w ON w.warehouseid = ar.warehouseid\n" +
                "WHERE w.warehouseid = ?;";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, warehouseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("productid"));
                product.setName(rs.getString("productName"));
                product.setAisleId(rs.getString("aisleid"));
                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

}
