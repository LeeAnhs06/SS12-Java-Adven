package Bai5.business;

import Bai5.database.DBContext;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class FeeService {

    public BigDecimal calculateDischargeFee(int days) throws Exception {
        String sql = "{ call CALCULATE_DISCHARGE_FEE(?, ?) }";

        try (Connection conn = DBContext.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, days); // IN
            cs.registerOutParameter(2, Types.DECIMAL); // OUT

            cs.execute();
            return cs.getBigDecimal(2);
        }
    }
}