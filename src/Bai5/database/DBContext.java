package Bai5.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBContext {

    private static final String HOST = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "SS12";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Leanh123";

    public static void initDatabase() {
        try (Connection conn = DriverManager.getConnection(HOST, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement()) {

            String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            stmt.executeUpdate(sql);

            System.out.println("✅ Database checked/created!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 👉 Kết nối vào database
    public static Connection getConnection() throws SQLException {
        String url = HOST + DB_NAME;
        return DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
    }
}