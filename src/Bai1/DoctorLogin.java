package Bai1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorLogin {

    public static boolean login(Connection conn, String doctorCode, String password) {
        String sql = "SELECT 1 FROM doctors WHERE doctor_code = ? AND password = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, doctorCode);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
//Phần 1 – Phân tích :
//SQL Injection xảy ra khi chương trình nối chuỗi tạo câu SQL, khiến dữ liệu người dùng nhập (ví dụ: ' OR '1'='1) bị hiểu như cú pháp SQL, làm thay đổi điều kiện WHERE và đăng nhập trái phép.
//PreparedStatement là “tấm khiên” vì nó tách câu lệnh SQL và dữ liệu đầu vào:
//SQL được viết cố định dạng: ... WHERE doctor_code = ? AND password = ?
//Giá trị người dùng nhập được bind vào dấu ? như dữ liệu thuần, không được phép trở thành từ khóa/điều kiện SQL.
//Cơ chế pre-compiled: Database biên dịch sẵn cấu trúc câu SQL (execution plan) trước, nên input chỉ được gán vào vị trí tham số → không thể chèn thêm OR, --, … để đổi logic truy vấn.