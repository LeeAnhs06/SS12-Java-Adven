package Bai6.database;

import Bai3.DBContext;   // ✅ import DBContext của bạn

import java.sql.Connection;
import java.sql.SQLException;

public class MyDatabase {

    public static void initDatabase() {
        DBContext.initDatabase();
    }

    public static Connection getConnection() throws SQLException {
        return DBContext.getConnection();
    }
}