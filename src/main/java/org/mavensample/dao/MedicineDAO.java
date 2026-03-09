package org.mavensample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mavensample.database.DBConnection;
import org.mavensample.model.Medicine;


public class MedicineDAO {
    public void addMedicine (Medicine med){
        String sql="INSERT INTO medicines(name, barcode, price, stock_quantity, expiry_date, category_id, supplier_id) VALUES (?,?,?,?,?,?,?)";
        try(Connection connection=DBConnection.getConnection();
    PreparedStatement statement=connection.prepareStatement(sql)){

         statement.setString(1, med.getName());
            statement.setString(2, med.getBarcode());
            statement.setDouble(3, med.getPrice());
            statement.setInt(4, med.getStockQuantity());
            statement.setDate(5, med.getExpiryDate());
            statement.setInt(6, med.getCategoryId());
            statement.setInt(7, med.getSupplierId());

int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Medicine added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error while adding medicine!");
            e.printStackTrace();
        }
    }
  public void getAllMedicines() {

    String sql = "SELECT * FROM medicines";

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement statement = connection.prepareStatement(sql);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {

            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String barcode = resultSet.getString("barcode");
            double price = resultSet.getDouble("price");
            int stock = resultSet.getInt("stock_quantity");

            System.out.println("ID: " + id +
                    " Name: " + name +
                    " Barcode: " + barcode +
                    " Price: " + price +
                    " Stock: " + stock);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    public void deleteMedicine(int id){
        String sql="DELETE FROM medicines WHERE id=?";
        try(Connection connection= DBConnection.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql)){
       statement.setInt(1,id);
       int rowsDeleted= statement.executeUpdate();

       if (rowsDeleted>0){
        System.out.println("Medicine deleted succesfully.");

       }else{
        System.out.println("No medicine found.");

       }

    
    
    
    }
    catch(SQLException e){
        System.out.println("Error while deleting medicine.");
        e.printStackTrace();
    }
    }
    public void updateMedicineStock (int id, int newStock){
        String sql="UPDATE medicines SET stock_quantity = ? WHERE id = ?";
        try(Connection connection= DBConnection.getConnection();
    PreparedStatement statement = connection.prepareStatement(sql)){
         statement.setInt(1,newStock);
         statement.setInt(2,id);
          int rowsUpdated=statement.executeUpdate();
          if(rowsUpdated>0){
            System.out.println("Medicine stock updated.");

          }else{
            System.out.println("No medicine found.");

          }

    } catch (SQLException e){
        System.out.println("Error while updating");
        e.printStackTrace();
    }
    }
    }