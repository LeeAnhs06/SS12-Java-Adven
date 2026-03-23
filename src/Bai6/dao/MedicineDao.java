package Bai6.dao;

import Bai6.database.MyDatabase;
import Bai6.model.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicineDao {

    public int updateMedicineStock(int id, int addedQuantity) throws Exception {
        String sql = "UPDATE medicines SET stock = stock + ? WHERE id = ?";

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addedQuantity);
            ps.setInt(2, id);

            return ps.executeUpdate();
        }
    }

    public List<Medicine> findMedicinesByPriceRange(double minPrice, double maxPrice) throws Exception {
        String sql = "SELECT id, name, price, stock FROM medicines WHERE price BETWEEN ? AND ? ORDER BY price";

        List<Medicine> list = new ArrayList<>();

        try (Connection conn = MyDatabase.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Medicine m = new Medicine();
                    m.setId(rs.getInt("id"));
                    m.setName(rs.getString("name"));
                    m.setPrice(rs.getBigDecimal("price"));
                    m.setStock(rs.getInt("stock"));
                    list.add(m);
                }
            }
        }
        return list;
    }
}