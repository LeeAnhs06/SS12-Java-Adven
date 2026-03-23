package Bai4;

import Bai3.DBContext;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoadResultsPrepared {

    static List<TestResult> buildSample(int n) {
        List<TestResult> list = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            list.add(new TestResult("Result #" + i));
        }
        return list;
    }

    public static void main(String[] args) {
        try {
            DBContext.initDatabase();

            List<TestResult> list = buildSample(1000);

            try (Connection conn = DBContext.getConnection();
                 Statement st = conn.createStatement()) {
                st.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS Results(
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        data VARCHAR(255) NOT NULL
                    )
                """);
                st.executeUpdate("TRUNCATE TABLE Results");
            }

            long start = System.currentTimeMillis();

            String sql = "INSERT INTO Results(data) VALUES (?)";

            try (Connection conn = DBContext.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                for (TestResult tr : list) {
                    ps.setString(1, tr.getData());
                    ps.executeUpdate();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("PreparedStatement time = " + (end - start) + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}