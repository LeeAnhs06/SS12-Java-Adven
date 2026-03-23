package Bai6.business;

import Bai6.dao.RevenueDao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RevenueBusiness {
    private final RevenueDao dao = new RevenueDao();

    public BigDecimal getPrescriptionTotal(int prescriptionId) throws Exception {
        return dao.calculatePrescriptionTotal(prescriptionId);
    }

    public BigDecimal getRevenueByDate(LocalDate date) throws Exception {
        return dao.getDailyRevenue(date);
    }
}