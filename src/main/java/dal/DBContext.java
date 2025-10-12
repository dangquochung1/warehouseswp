package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {
    protected Connection connection;

    public DBContext() {
        try {
            // Edit URL, username, password to authenticate with your MS SQL Server
            String url = "jdbc:sqlserver://localhost:1433;"
                    + "databaseName=warehouseDB;"
                    + "encrypt=true;"
                    + "trustServerCertificate=true;";
            String username = "sa";
            String password = "123";

            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException ex) {
            // Logging the exception detail for debugging
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, "Failed to connect to database.", ex);
        }
    }

    /**
     * Main method to test the database connection.
     * If you are running this in an IDE, ensure you have the SQL Server JDBC driver added to your project's libraries.
     */
    public static void main(String[] args) {
        // Create an instance of DBContext to attempt the connection
        DBContext db = new DBContext();

        System.out.println("--- BẮT ĐẦU KIỂM TRA KẾT NỐI DATABASE ---");

        if (db.connection != null) {
            System.out.println("✅ Kết nối cơ sở dữ liệu 'warehouseDB' thành công!");

            // Close the connection
            try {
                db.connection.close();
                System.out.println("Đã đóng kết nối.");
            } catch (SQLException e) {
                System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
            }
        } else {
            System.out.println("❌ Kết nối cơ sở dữ liệu thất bại.");
            System.out.println("Kiểm tra lại các yếu tố sau:");
            System.out.println("1. **Driver SQL Server (JDBC)** đã được thêm vào dự án.");
            System.out.println("2. **Thông tin đăng nhập** (sa/123) và **databaseName** (warehouseDB) chính xác.");
            System.out.println("3. **SQL Server** có đang chạy và cho phép kết nối từ xa trên cổng 1433.");
        }
    }
}