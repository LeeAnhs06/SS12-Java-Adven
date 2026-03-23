package Bai5.presentation;

import Bai5.business.FeeService;
import Bai5.business.PatientService;
import Bai5.dao.Inpatient;
import Bai5.database.DBContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

public class RHMSConsole {

    private final Scanner sc = new Scanner(System.in);
    private final PatientService patientService = new PatientService();
    private final FeeService feeService = new FeeService();

    public static void main(String[] args) {
        new RHMSConsole().run();
    }

    public void run() {
        try {
            DBContext.initDatabase();
            patientService.init();

            while (true) {
                System.out.println("\n===== RHMS =====");
                System.out.println("1. Danh sách bệnh nhân");
                System.out.println("2. Tiếp nhận bệnh nhân mới");
                System.out.println("3. Cập nhật bệnh án");
                System.out.println("4. Xuất viện & Tính phí");
                System.out.println("5. Thoát");
                System.out.print("Chọn: ");

                String choice = sc.nextLine().trim();

                switch (choice) {
                    case "1" -> showList();
                    case "2" -> admit();
                    case "3" -> updateDiagnosis();
                    case "4" -> dischargeAndFee();
                    case "5" -> { System.out.println("Thoát."); return; }
                    default -> System.out.println("Sai lựa chọn.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showList() throws Exception {
        List<Inpatient> list = patientService.getAllPatients();
        System.out.printf("%-5s %-10s %-25s %-5s %-20s%n", "ID", "Mã BN", "Tên", "Tuổi", "Khoa");
        for (Inpatient p : list) {
            System.out.printf("%-5d %-10s %-25s %-5d %-20s%n",
                    p.getId(), p.getCode(), p.getFullName(), p.getAge(), p.getDepartment());
        }
    }

    private void admit() throws Exception {
        System.out.print("Mã BN: ");
        String code = sc.nextLine().trim();

        System.out.print("Tên BN: ");
        String name = sc.nextLine().trim();

        System.out.print("Tuổi: ");
        int age = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Khoa: ");
        String dept = sc.nextLine().trim();

        System.out.print("Bệnh án: ");
        String diagnosis = sc.nextLine();

        boolean ok = patientService.admit(code, name, age, dept, diagnosis);
        System.out.println(ok ? "Đã tiếp nhận." : " Thất bại.");
    }

    private void updateDiagnosis() throws Exception {
        System.out.print("ID bệnh nhân: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Bệnh án mới: ");
        String diagnosis = sc.nextLine();

        boolean ok = patientService.updateDiagnosis(id, diagnosis);
        System.out.println(ok ? "Đã cập nhật." : "Không tìm thấy.");
    }

    private void dischargeAndFee() throws Exception {
        System.out.print("ID bệnh nhân xuất viện: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        LocalDate admitDate = patientService.getAdmitDate(id);
        if (admitDate == null) {
            System.out.println("Không tìm thấy.");
            return;
        }

        int days = (int) ChronoUnit.DAYS.between(admitDate, LocalDate.now());
        if (days < 0) days = 0;

        BigDecimal fee = feeService.calculateDischargeFee(days);

        boolean discharged = patientService.discharge(id);
        if (!discharged) {
            System.out.println("Xuất viện thất bại.");
            return;
        }

        System.out.println("Số ngày nằm viện: " + days);
        System.out.println("Tổng viện phí: " + fee);
    }
}