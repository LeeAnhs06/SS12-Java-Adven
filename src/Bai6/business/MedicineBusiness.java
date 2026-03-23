package Bai6.business;

import Bai6.dao.MedicineDao;
import Bai6.model.Medicine;

import java.util.List;

public class MedicineBusiness {
    private final MedicineDao dao = new MedicineDao();

    public boolean addStock(int id, int addedQuantity) throws Exception {
        if (addedQuantity <= 0) return false;
        return dao.updateMedicineStock(id, addedQuantity) == 1;
    }

    public List<Medicine> searchByPrice(double minPrice, double maxPrice) throws Exception {
        if (minPrice > maxPrice) {
            double t = minPrice; minPrice = maxPrice; maxPrice = t;
        }
        return dao.findMedicinesByPriceRange(minPrice, maxPrice);
    }
}