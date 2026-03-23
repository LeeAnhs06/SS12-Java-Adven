package Bai5.dao;

import Bai5.database.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {

    public void ensureTable() throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS inpatients (
              id INT AUTO_INCREMENT PRIMARY KEY,
              code VARCHAR(20) NOT NULL UNIQUE,
              full_name VARCHAR(100) NOT NULL,
              age INT NOT NULL,
              department VARCHAR(100) NOT NULL,
              diagnosis TEXT,
              admit_date DATE NOT NULL
            )
            """;
        try (Connection conn = DBContext.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    public List<Inpatient> findAllBasic() throws SQLException {
        String sql = "SELECT id, code, full_name, age, department, admit_date FROM inpatients ORDER BY id DESC";
        List<Inpatient> list = new ArrayList<>();

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Inpatient p = new Inpatient();
                p.setId(rs.getInt("id"));
                p.setCode(rs.getString("code"));
                p.setFullName(rs.getString("full_name"));
                p.setAge(rs.getInt("age"));
                p.setDepartment(rs.getString("department"));
                p.setAdmitDate(rs.getDate("admit_date").toLocalDate());
                list.add(p);
            }
        }
        return list;
    }

    public int insert(Inpatient p) throws SQLException {
        String sql = """
            INSERT INTO inpatients(code, full_name, age, department, diagnosis, admit_date)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getCode());
            ps.setString(2, p.getFullName()); // ✅ tên có dấu nháy an toàn
            ps.setInt(3, p.getAge());
            ps.setString(4, p.getDepartment());
            ps.setString(5, p.getDiagnosis());
            ps.setDate(6, Date.valueOf(p.getAdmitDate()));

            return ps.executeUpdate();
        }
    }

    public int updateDiagnosisById(int id, String diagnosis) throws SQLException {
        String sql = "UPDATE inpatients SET diagnosis = ? WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, diagnosis);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    public Date findAdmitDateById(int id) throws SQLException {
        String sql = "SELECT admit_date FROM inpatients WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return rs.getDate("admit_date");
            }
        }
    }

    public int deleteById(int id) throws SQLException {
        String sql = "DELETE FROM inpatients WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }
}