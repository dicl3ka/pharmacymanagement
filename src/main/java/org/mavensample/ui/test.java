package org.mavensample.ui;

import org.mavensample.dao.MedicineDAO;


public class test {

    public static void main(String[] args) {

        MedicineDAO dao = new MedicineDAO();

        // =========================================
        // IlAC EKLEME 
        // =========================================
        //
        // Medicine med = new Medicine(
          //      0,
         //    "Parol",
         //       "869000111",
         //        45.50,
         //       100,
         //        Date.valueOf("2027-12-31"),
         //        1,
         //        1
        // );
         //dao.addMedicine(med);


        // =========================================
        // ILACLARI GORUNTULEME
        // =========================================
        //
        // dao.getAllMedicines();


        // =========================================
        // ILAC SILME
        // =========================================
        //
         dao.deleteMedicine(5);


        // =========================================
        // STOK GUNCELLEME
        // =========================================
        //
         //dao.updateMedicineStock(5, 50);
    }
}

