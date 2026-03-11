package org.pharmacy.dao;

import org.pharmacy.dbconnection.DBConnection;
import org.pharmacy.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDao {

    // Category ekleme
    public void addCategory(Category category) {

        String sql = "INSERT INTO categories(category_name) VALUES (?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, category.getCategoryName());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Category added successfully.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Category listeleme
    public void getAllCategories() {

        String sql = "SELECT * FROM categories";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("category_name");

                System.out.println("ID: " + id + " Category: " + name);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Category silme
    public void deleteCategory(int id) {

        String sql = "DELETE FROM categories WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Category deleted.");
            } else {
                System.out.println("Category not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
