
package Bai6.dao;

import Bai6.database.MyDatabase;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;

public class RevenueDao {

    public BigDecimal calculatePrescriptionTotal(int prescriptionId) throws Exception {
        String sql = "{ call CalculatePrescriptionTotal(?, ?) }";

        try (Connection conn = MyDatabase.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, prescriptionId);                 // IN
            cs.registerOutParameter(2, Types.DECIMAL);    // OUT

            cs.execute();
            return cs.getBigDecimal(2);
        }
    }

    public BigDecimal getDailyRevenue(LocalDate date) throws Exception {
        String sql = "{ call GetDailyRevenue(?, ?) }";

        try (Connection conn = MyDatabase.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setDate(1, Date.valueOf(date));            // IN
            cs.registerOutParameter(2, Types.DECIMAL);    // OUT

            cs.execute();
            return cs.getBigDecimal(2);
        }
    }
}