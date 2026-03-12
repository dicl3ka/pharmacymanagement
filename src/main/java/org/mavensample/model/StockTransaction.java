package org.mavensample.model;

import java.sql.Timestamp;

public class StockTransaction {
    public static final String TYPE_IN = "in";
    public static final String TYPE_OUT = "out";

    private int id;
    private int medicineId;
    private int userId;
    private String type; // "in" or "out"
    private int quantity;
    private Timestamp transactionDate;

    public StockTransaction() {
    }

    public StockTransaction(int id, int medicineId, int userId, String type, int quantity, Timestamp transactionDate) {
        this.id = id;
        this.medicineId = medicineId;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public StockTransaction(int medicineId, int userId, String type, int quantity, Timestamp transactionDate) {
        this.medicineId = medicineId;
        this.userId = userId;
        this.type = type;
        this.quantity = quantity;
        this.transactionDate = transactionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(int medicineId) {
        this.medicineId = medicineId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        if (type == null) {
            this.type = null;
            return;
        }

        String normalized = type.trim().toLowerCase();
        if (!TYPE_IN.equals(normalized) && !TYPE_OUT.equals(normalized)) {
            throw new IllegalArgumentException("type must be 'in' or 'out'");
        }

        this.type = normalized;
    }

    public boolean isIn() {
        return TYPE_IN.equals(type);
    }

    public boolean isOut() {
        return TYPE_OUT.equals(type);
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Timestamp getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Timestamp transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "StockTransaction{" +
                "id=" + id +
                ", medicineId=" + medicineId +
                ", userId=" + userId +
                ", type='" + type + '\'' +
                ", quantity=" + quantity +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
