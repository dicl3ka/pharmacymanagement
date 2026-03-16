package org.mavensample.model;

import java.sql.Date;
public class MedicineBuilder {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private int stockQuantity;
    private Date expiryDate;
    private int categoryId;
    private int supplierId;

    public MedicineBuilder setId(int id){
        this.id =id;
        return this;
    }
    public MedicineBuilder setName(String name){
        this.name = name;
        return this;
    }
    public MedicineBuilder setBarcode(String barcode){
        this.barcode =barcode;
        return this;
    
    }
    public MedicineBuilder setPrice(double price){
        this.price =price;
        return this;
    }
    public MedicineBuilder setStockQuantity(int stockQuantity){
        this.stockQuantity=stockQuantity;
        return this;
    }
    public MedicineBuilder setExpiryDate(Date expiryDate){
        this.expiryDate = expiryDate;
        return this;
    }
   public MedicineBuilder setCategoryId(int categoryId){
    this.categoryId= categoryId;
    return this;
   }
   public MedicineBuilder setSupplierId(int supplierId){
    this.supplierId =supplierId;
    return this;
   }
    public Medicine build(){
        if(name == null || name.isBlank()){
            throw new IllegalStateException("Medicine name is required.");

        }
        if(barcode == null || barcode.isBlank()){
            throw new IllegalStateException("Barcode is required.");

        }
        if(price<0){
            throw new IllegalStateException("Price cannot be negative.");
        }
        if(stockQuantity<0){
            throw new IllegalStateException("Stock quantity cannot be negative");

        }
        Medicine medicine= new Medicine();
        medicine.setId(id);
        medicine.setName(name);
        medicine.setBarcode(barcode);
        medicine.setPrice(price);
        medicine.setStockQuantity(stockQuantity);
        medicine.setExpiryDate(expiryDate);
        medicine.setCategoryId(categoryId);
        medicine.setSupplierId(supplierId);
        
        return medicine;

    }

}
