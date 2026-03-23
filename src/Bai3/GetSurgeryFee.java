package Bai3;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class GetSurgeryFee {

    public static BigDecimal getSurgeryFee(Connection conn, int surgeryId) throws Exception {
        String sql = "{ call GET_SURGERY_FEE(?, ?) }";

        try (CallableStatement cstmt = conn.prepareCall(sql)) {
            cstmt.setInt(1, surgeryId);                 // IN
            cstmt.registerOutParameter(2, Types.DECIMAL); // OUT

            cstmt.execute();

            return cstmt.getBigDecimal(2);
        }
    }

    public static void main(String[] args) {
        try {
            DBContext.initDatabase();

            try (Connection conn = DBContext.getConnection()) {
                int surgeryId = 505;
                BigDecimal cost = getSurgeryFee(conn, surgeryId);

                System.out.println("Chi phí phẫu thuật (surgery_id=" + surgeryId + ") = " + cost);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}