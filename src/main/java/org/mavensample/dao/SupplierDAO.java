package org.mavensample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mavensample.database.DBConnection;
import org.mavensample.model.Supplier;

public class SupplierDAO {

    public void addSupplier(Supplier supplier) {
        String sql = "INSERT INTO suppliers(name, phone, address) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getAddress());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Supplier added successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Error while adding supplier!");
            e.printStackTrace();
        }
    }

    public void getAllSuppliers() {
        String sql = "SELECT * FROM suppliers";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String phone = resultSet.getString("phone");
                String address = resultSet.getString("address");

                System.out.println("ID: " + id +
                        " Name: " + name +
                        " Phone: " + phone +
                        " Address: " + address);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSupplier(int id) {
        String sql = "DELETE FROM suppliers WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Supplier deleted successfully.");
            } else {
                System.out.println("Supplier not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error while deleting supplier.");
            e.printStackTrace();
        }
    }

    public void updateSupplier(int id, Supplier supplier) {
        String sql = "UPDATE suppliers SET name = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, supplier.getName());
            statement.setString(2, supplier.getPhone());
            statement.setString(3, supplier.getAddress());
            statement.setInt(4, id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Supplier updated successfully.");
            } else {
                System.out.println("Supplier not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error while updating supplier.");
            e.printStackTrace();
        }
    }

    public Supplier getSupplierById(int id) {
        String sql = "SELECT * FROM suppliers WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Supplier supplier = new Supplier();
                    supplier.setId(resultSet.getInt("id"));
                    supplier.setName(resultSet.getString("name"));
                    supplier.setPhone(resultSet.getString("phone"));
                    supplier.setAddress(resultSet.getString("address"));
                    return supplier;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error while fetching supplier.");
            e.printStackTrace();
        }

        return null;
    }
}

