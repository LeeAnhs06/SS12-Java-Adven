package Bai6.presentation;

import Bai6.business.MedicineBusiness;
import Bai6.business.RevenueBusiness;
import Bai6.database.MyDatabase;
import Bai6.model.Medicine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainExerciseSumary {

    private static final Scanner sc = new Scanner(System.in);
    private static final MedicineBusiness medicineBusiness = new MedicineBusiness();
    private static final RevenueBusiness revenueBusiness = new RevenueBusiness();

    public static void main(String[] args) {
        MyDatabase.initDatabase();

        while (true) {
            System.out.println("\n===== BAI 6: Medicines & Revenue =====");
            System.out.println("1) Update medicine stock");
            System.out.println("2) Find medicines by price range");
            System.out.println("3) Calculate prescription total (OUT)");
            System.out.println("4) Get daily revenue (IN/OUT)");
            System.out.println("5) Exit");
            System.out.print("Choose: ");

            String c = sc.nextLine().trim();
            try {
                switch (c) {
                    case "1" -> uiUpdateStock();
                    case "2" -> uiFindByPrice();
                    case "3" -> uiPrescriptionTotal();
                    case "4" -> uiDailyRevenue();
                    case "5" -> { System.out.println("Bye!"); return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void uiUpdateStock() throws Exception {
        System.out.print("Medicine ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        System.out.print("Added quantity: ");
        int qty = Integer.parseInt(sc.nextLine().trim());

        boolean ok = medicineBusiness.addStock(id, qty);
        System.out.println(ok ? " Updated stock." : " Update failed.");
    }

    private static void uiFindByPrice() throws Exception {
        System.out.print("Min price: ");
        double min = Double.parseDouble(sc.nextLine().trim());

        System.out.print("Max price: ");
        double max = Double.parseDouble(sc.nextLine().trim());

        List<Medicine> list = medicineBusiness.searchByPrice(min, max);
        System.out.println("\n--- Result ---");
        System.out.printf("%-5s %-20s %-12s %-8s%n", "ID", "Name", "Price", "Stock");
        for (Medicine m : list) {
            System.out.printf("%-5d %-20s %-12s %-8d%n",
                    m.getId(), m.getName(), m.getPrice(), m.getStock());
        }
        if (list.isEmpty()) System.out.println("(empty)");
    }

    private static void uiPrescriptionTotal() throws Exception {
        System.out.print("Prescription ID: ");
        int id = Integer.parseInt(sc.nextLine().trim());

        BigDecimal total = revenueBusiness.getPrescriptionTotal(id);
        System.out.println("Total = " + total);
    }

    private static void uiDailyRevenue() throws Exception {
        System.out.print("Sale date (yyyy-MM-dd): ");
        LocalDate date = LocalDate.parse(sc.nextLine().trim());

        BigDecimal rev = revenueBusiness.getRevenueByDate(date);
        System.out.println("Revenue of " + date + " = " + rev);
    }
}