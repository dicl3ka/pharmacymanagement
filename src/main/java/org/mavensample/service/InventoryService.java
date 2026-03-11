package org.pharmacy.service;

import org.pharmacy.dao.MedicineDao;
import org.pharmacy.model.Medicine;

public class InventoryService {

    private MedicineDao medicineDao;

    public InventoryService() {
        medicineDao = new MedicineDao();
    }

    // Yeni ilaç eklemesi
    public void addMedicine(Medicine medicine) {
        medicineDao.addMedicine(medicine);
    }

    // Tüm ilaçları listeliyicejk
    public void listMedicines() {
        medicineDao.getAllMedicines();
    }

    // Stok güncelleme
    public void updateStock(int id, int newStock) {
        medicineDao.updateMedicineStock(id, newStock);
    }

    // İlaç silme
    public void deleteMedicine(int id) {
        medicineDao.deleteMedicine(id);
    }
}
