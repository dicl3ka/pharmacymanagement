package org.mavensample.model;

import java.sql.Date;
public class Medicine {
    private int id;
    private String name;
    private String barcode;
    private double price;
    private int stockQuantity;
    private Date expiryDate;
    private int categoryId;
    private int supplierId;
    
    public Medicine(){

    }
    public Medicine(int id,String name,String barcode, double price, int stockQuantity, Date expiryDate, int categoryId, int supplierId){
        this.id= id;
        this.name=name;
        this.barcode=barcode;
        this.price=price;
        this.stockQuantity=stockQuantity;
        this.expiryDate=expiryDate;
        this.categoryId=categoryId;
        this.supplierId=supplierId;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;

    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;

    }
    public String getBarcode(){
        return barcode;
    }
    public void setBarcode(String barcode){
        this.barcode=barcode;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
   public int getStockQuantity(){
    return stockQuantity;
   }
   public void setStockQuantity(int stockQuantity){
    this.stockQuantity=stockQuantity;

   }
    public Date getExpiryDate(){
        return expiryDate;
    }
   public void setExpiryDate(Date expiryDate){
    this.expiryDate=expiryDate;
   }
  public int getCategoryId(){
    return categoryId;
  }
public void setCategoryId(int categoryId){
    this.categoryId=categoryId;
}
public int getSupplierId(){
    return supplierId;
}
public void setSupplierId(int supplierId){
    this.supplierId=supplierId;
}

}
