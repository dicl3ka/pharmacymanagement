package org.mavensample.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mavensample.database.DBConnection;
import org.mavensample.model.StockTransaction;

public class TransactionDAO {

    public void addTransaction(StockTransaction transaction) {
        String sql = "INSERT INTO stock_transactions(medicine_id, user_id, type, quantity, transaction_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, transaction.getMedicineId());
            statement.setInt(2, transaction.getUserId());
            statement.setString(3, transaction.getType());
            statement.setInt(4, transaction.getQuantity());
            statement.setTimestamp(5, transaction.getTransactionDate());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Transaction recorded successfully.");
            }

        } catch (SQLException e) {
            System.out.println("Error while recording transaction.");
            e.printStackTrace();
        }
    }

    public List<StockTransaction> getAllTransactions() {
        String sql = "SELECT * FROM stock_transactions";
        List<StockTransaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                transactions.add(readTransaction(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public StockTransaction getTransactionById(int id) {
        String sql = "SELECT * FROM stock_transactions WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return readTransaction(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<StockTransaction> getTransactionsByMedicineId(int medicineId) {
        String sql = "SELECT * FROM stock_transactions WHERE medicine_id = ?";
        List<StockTransaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, medicineId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(readTransaction(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public List<StockTransaction> getTransactionsByUserId(int userId) {
        String sql = "SELECT * FROM stock_transactions WHERE user_id = ?";
        List<StockTransaction> transactions = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    transactions.add(readTransaction(resultSet));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return transactions;
    }

    public void deleteTransaction(int id) {
        String sql = "DELETE FROM stock_transactions WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                System.out.println("Transaction deleted successfully.");
            } else {
                System.out.println("Transaction not found.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private StockTransaction readTransaction(ResultSet resultSet) throws SQLException {
        StockTransaction transaction = new StockTransaction();
        transaction.setId(resultSet.getInt("id"));
        transaction.setMedicineId(resultSet.getInt("medicine_id"));
        transaction.setUserId(resultSet.getInt("user_id"));
        transaction.setType(resultSet.getString("type"));
        transaction.setQuantity(resultSet.getInt("quantity"));
        transaction.setTransactionDate(resultSet.getTimestamp("transaction_date"));
        return transaction;
    }
}
