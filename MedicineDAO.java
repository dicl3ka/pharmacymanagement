package org.mavensample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mavensample.database.DBConnection;
import org.mavensample.model.Medicine;

public class MedicineDAO {

   public boolean addMedicine(Medicine med) {

    String sql = "INSERT INTO medicines " +
            "(name, barcode, price, stock_quantity, minimum_stock_level, expiry_date, category_id, supplier_id) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, med.getName());
        statement.setString(2, med.getBarcode());
        statement.setDouble(3, med.getPrice());
        statement.setInt(4, med.getStockQuantity());
        statement.setInt(5, med.getMinimumStockLevel());
        statement.setDate(6, med.getExpiryDate());
        statement.setInt(7, med.getCategoryId());
        statement.setInt(8, med.getSupplierId());

        int rowsInserted = statement.executeUpdate();

        statement.close();
        connection.close();

        return rowsInserted > 0;

    } catch (SQLException e) {
        System.out.println("Error while adding medicine!");
        e.printStackTrace();
        return false;
    }
}
    public List<Medicine> getAllMedicines() {

        List<Medicine> medicines = new ArrayList<>();
        String sql = "SELECT * FROM medicines";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                medicines.add(readMedicine(resultSet));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return medicines;
    }

    public Medicine getMedicineById(int id) {

    String sql = "SELECT * FROM medicines WHERE id = ?";

    try {
        Connection connection = DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Medicine medicine = readMedicine(resultSet);

            resultSet.close();
            statement.close();
            connection.close();

            return medicine;
        }

        resultSet.close();
        statement.close();
        connection.close();

    } catch (SQLException e) {
        System.out.println("Error while fetching medicine by id.");
        e.printStackTrace();
    }

    return null;
}

    public void deleteMedicine(int id) {

        String sql = "DELETE FROM medicines WHERE id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Medicine deleted successfully.");
            } else {
                System.out.println("No medicine found.");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error while deleting medicine.");
            e.printStackTrace();
        }
    }

    public void updateMedicineStock(int id, int newStock) {

        String sql = "UPDATE medicines SET stock_quantity = ? WHERE id = ?";

        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, newStock);
            statement.setInt(2, id);

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Medicine stock updated.");
            } else {
                System.out.println("No medicine found.");
            }

            statement.close();
            connection.close();

        } catch (SQLException e) {
            System.out.println("Error while updating stock.");
            e.printStackTrace();
        }
    }

    private Medicine readMedicine(ResultSet resultSet) throws SQLException {

        Medicine medicine = new Medicine();

        medicine.setId(resultSet.getInt("id"));
        medicine.setName(resultSet.getString("name"));
        medicine.setBarcode(resultSet.getString("barcode"));
        medicine.setPrice(resultSet.getDouble("price"));
        medicine.setStockQuantity(resultSet.getInt("stock_quantity"));
        medicine.setMinimumStockLevel(resultSet.getInt("minimum_stock_level"));
        medicine.setExpiryDate(resultSet.getDate("expiry_date"));
        medicine.setCategoryId(resultSet.getInt("category_id"));
        medicine.setSupplierId(resultSet.getInt("supplier_id"));

        return medicine;
    }
}