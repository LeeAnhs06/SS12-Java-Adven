package Bai2;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateVitals {

    public static int updateVitals(Connection conn, int patientId, double temp, int heartRate) {
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, temp);
            ps.setInt(2, heartRate);
            ps.setInt(3, patientId);

            return ps.executeUpdate();  
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
//Phần 1 – Phân tích
//Khi nối chuỗi bằng Statement, số double có thể bị chuyển thành chuỗi theo Locale của hệ điều hành (ví dụ Pháp/Việt dùng 37,5), đưa vào SQL sẽ dễ sai cú pháp vì SQL thường cần dấu chấm 37.5.
//PreparedStatement.setDouble() và setInt() không gửi số dưới dạng chuỗi theo định dạng Locale, mà gửi giá trị đúng kiểu dữ liệu (numeric) cho JDBC/DB.
//Vì vậy lập trình viên không cần lo dấu . hay ,, DB sẽ nhận đúng kiểu số và cập nhật chính xác.
