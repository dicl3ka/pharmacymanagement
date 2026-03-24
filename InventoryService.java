package org.mavensample.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mavensample.dao.MedicineDAO;
import org.mavensample.dao.TransactionDAO;
import org.mavensample.model.Medicine;
import org.mavensample.model.StockTransaction;

public class InventoryService {

    private final MedicineDAO medicineDao;
    private final TransactionDAO transactionDao;

    public InventoryService() {
        this.medicineDao = new MedicineDAO();
        this.transactionDao = new TransactionDAO();
    }

    // Yeni ilaç ekleme
    public boolean addMedicine(Medicine medicine) {
    return medicineDao.addMedicine(medicine);
}

    // Tüm ilaçları listeleme
    public List<Medicine> listMedicines() {
        return medicineDao.getAllMedicines();
    }

    // İlaç silme
    public void deleteMedicine(int id) {
        medicineDao.deleteMedicine(id);
    }

    // Direkt stok güncelleme
    public void updateStock(int id, int newStock) {
        medicineDao.updateMedicineStock(id, newStock);
    }

    // Low stock kontrol
    public boolean isLowStock(Medicine medicine) {
        return medicine.getStockQuantity() <= medicine.getMinimumStockLevel();
    }

    public List<Medicine> getLowStockMedicines() {
        List<Medicine> allMedicines = medicineDao.getAllMedicines();
        List<Medicine> lowStockMedicines = new ArrayList<>();

        for (Medicine medicine : allMedicines) {
            if (isLowStock(medicine)) {
                lowStockMedicines.add(medicine);
            }
        }

        return lowStockMedicines;
    }

    // STOCK IN
    public void stockIn(int medicineId, int userId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        Medicine medicine = medicineDao.getMedicineById(medicineId);

        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        int oldStock = medicine.getStockQuantity();
        int newStock = oldStock + quantity;

        medicineDao.updateMedicineStock(medicineId, newStock);

        StockTransaction transaction = new StockTransaction(
                medicineId,
                userId,
                StockTransaction.TYPE_IN,
                quantity,
                new Timestamp(System.currentTimeMillis())
        );

        transactionDao.addTransaction(transaction);

        System.out.println("Stock IN successful.");
        System.out.println("User ID: " + userId);
        System.out.println("Medicine: " + medicine.getName());
        System.out.println("Old stock: " + oldStock);
        System.out.println("Added quantity: " + quantity);
        System.out.println("New stock: " + newStock);
    }

    // STOCK OUT
    public void stockOut(int medicineId, int userId, int quantity) {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        Medicine medicine = medicineDao.getMedicineById(medicineId);

        if (medicine == null) {
            System.out.println("Medicine not found.");
            return;
        }

        int oldStock = medicine.getStockQuantity();

        if (oldStock < quantity) {
            System.out.println("Not enough stock!");
            return;
        }

        int newStock = oldStock - quantity;

        medicineDao.updateMedicineStock(medicineId, newStock);

        StockTransaction transaction = new StockTransaction(
                medicineId,
                userId,
                StockTransaction.TYPE_OUT,
                quantity,
                new Timestamp(System.currentTimeMillis())
        );

        transactionDao.addTransaction(transaction);

        System.out.println("Stock OUT successful.");
        System.out.println("User ID: " + userId);
        System.out.println("Medicine: " + medicine.getName());
        System.out.println("Old stock: " + oldStock);
        System.out.println("Removed quantity: " + quantity);
        System.out.println("New stock: " + newStock);

        if (newStock <= medicine.getMinimumStockLevel()) {
            System.out.println("LOW STOCK WARNING for " + medicine.getName());
            System.out.println("Last transaction user ID: " + userId);
        }
    }
}