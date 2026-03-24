package org.mavensample.ui;

import org.mavensample.model.Medicine;
import org.mavensample.model.MedicineBuilder;
import org.mavensample.model.User;
import org.mavensample.service.InventoryService;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.List;

public class AdminFrame extends JFrame {

    private User user;
    private InventoryService service;

    private JTable medicineTable;
    private DefaultTableModel tableModel;

    private JTextField idField;
    private JTextField nameField;
    private JTextField barcodeField;
    private JTextField priceField;
    private JTextField stockField;
    private JTextField minStockField;
    private JTextField expiryDateField;
    private JTextField categoryIdField;
    private JTextField supplierIdField;
    private JTextField searchField;

    private JTextArea outputArea;

    public AdminFrame(User user) {
        this.user = user;
        this.service = new InventoryService();

        setTitle("Admin Panel - " + user.getUsername());
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createOutputPanel(), BorderLayout.SOUTH);

        loadMedicines();

        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("PHARMACY ADMIN PANEL");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");
        JButton logoutButton = new JButton("Logout");

        searchButton.addActionListener(e -> searchMedicine());

        refreshButton.addActionListener(e -> {
            loadMedicines();
            clearFields();
            medicineTable.clearSelection();
            writeOutput("Medicine table refreshed.");
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        rightPanel.add(new JLabel("Search:"));
        rightPanel.add(searchField);
        rightPanel.add(searchButton);
        rightPanel.add(refreshButton);
        rightPanel.add(logoutButton);

        topPanel.add(titleLabel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.add(createTablePanel());
        centerPanel.add(createFormPanel());
        return centerPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Medicine Table"));

        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{
                "ID", "Name", "Barcode", "Price", "Stock", "Min Stock", "Expiry Date", "Category ID", "Supplier ID"
        });

        medicineTable = new JTable(tableModel);
        medicineTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        medicineTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                fillFieldsFromSelectedRow();
            }
        });

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        return tablePanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Medicine Operations"));

        JPanel fieldsPanel = new JPanel(new GridLayout(9, 2, 8, 8));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        idField = new JTextField();
        idField.setEditable(false);

        nameField = new JTextField();
        barcodeField = new JTextField();
        priceField = new JTextField();
        stockField = new JTextField();
        minStockField = new JTextField();
        expiryDateField = new JTextField();
        categoryIdField = new JTextField();
        supplierIdField = new JTextField();

        fieldsPanel.add(new JLabel("ID:"));
        fieldsPanel.add(idField);

        fieldsPanel.add(new JLabel("Name:"));
        fieldsPanel.add(nameField);

        fieldsPanel.add(new JLabel("Barcode:"));
        fieldsPanel.add(barcodeField);

        fieldsPanel.add(new JLabel("Price:"));
        fieldsPanel.add(priceField);

        fieldsPanel.add(new JLabel("Stock:"));
        fieldsPanel.add(stockField);

        fieldsPanel.add(new JLabel("Min Stock:"));
        fieldsPanel.add(minStockField);

        fieldsPanel.add(new JLabel("Expiry Date (yyyy-mm-dd):"));
        fieldsPanel.add(expiryDateField);

        fieldsPanel.add(new JLabel("Category ID:"));
        fieldsPanel.add(categoryIdField);

        fieldsPanel.add(new JLabel("Supplier ID:"));
        fieldsPanel.add(supplierIdField);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add Medicine");
        JButton deleteButton = new JButton("Delete Medicine");
        JButton updateStockButton = new JButton("Update Medicine");
        JButton lowStockButton = new JButton("Show Low Stock");
        JButton resetButton = new JButton("Reset Fields");

        addButton.addActionListener(e -> addMedicine());
        deleteButton.addActionListener(e -> deleteMedicine());
        updateStockButton.addActionListener(e -> updateStock());
        lowStockButton.addActionListener(e -> showLowStock());

        resetButton.addActionListener(e -> {
            clearFields();
            medicineTable.clearSelection();
            writeOutput("Fields have been cleared.");
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateStockButton);
        buttonPanel.add(lowStockButton);
        buttonPanel.add(resetButton);

        formPanel.add(fieldsPanel, BorderLayout.CENTER);
        formPanel.add(buttonPanel, BorderLayout.SOUTH);

        return formPanel;
    }

    private JScrollPane createOutputPanel() {
        outputArea = new JTextArea(8, 20);
        outputArea.setEditable(false);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        return scrollPane;
    }

    private void loadMedicines() {
        tableModel.setRowCount(0);

        List<Medicine> medicines = service.listMedicines();

        for (Medicine medicine : medicines) {
            tableModel.addRow(new Object[]{
                    medicine.getId(),
                    medicine.getName(),
                    medicine.getBarcode(),
                    medicine.getPrice(),
                    medicine.getStockQuantity(),
                    medicine.getMinimumStockLevel(),
                    medicine.getExpiryDate(),
                    medicine.getCategoryId(),
                    medicine.getSupplierId()
            });
        }
    }

    private void fillFieldsFromSelectedRow() {
        int selectedRow = medicineTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        idField.setText(tableModel.getValueAt(selectedRow, 0).toString());
        nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        barcodeField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        priceField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        stockField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        minStockField.setText(tableModel.getValueAt(selectedRow, 5).toString());
        expiryDateField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        categoryIdField.setText(tableModel.getValueAt(selectedRow, 7).toString());
        supplierIdField.setText(tableModel.getValueAt(selectedRow, 8).toString());

        writeOutput("Selected medicine ID: " + idField.getText());
    }

    private void addMedicine() {
    try {
        Medicine medicine = new MedicineBuilder()
                .setName(nameField.getText().trim())
                .setBarcode(barcodeField.getText().trim())
                .setPrice(Double.parseDouble(priceField.getText().trim()))
                .setStockQuantity(Integer.parseInt(stockField.getText().trim()))
                .setMinimumStockLevel(Integer.parseInt(minStockField.getText().trim()))
                .setExpiryDate(java.sql.Date.valueOf(expiryDateField.getText().trim()))
                .setCategoryId(Integer.parseInt(categoryIdField.getText().trim()))
                .setSupplierId(Integer.parseInt(supplierIdField.getText().trim()))
                .build();

        boolean success = service.addMedicine(medicine);

        if (success) {
            loadMedicines();
            clearFields();
            medicineTable.clearSelection();
            writeOutput("Medicine added successfully.");
            JOptionPane.showMessageDialog(this, "Medicine added successfully.");
        } else {
            writeOutput("Medicine could not be added. Check barcode, category ID, supplier ID, and date.");
            JOptionPane.showMessageDialog(this,
                    "Medicine could not be added.\nCheck barcode, category ID, supplier ID, and date.");
        }

    } catch (Exception e) {
        writeOutput("Add Medicine Error: " + e.getMessage());
        JOptionPane.showMessageDialog(this, "Add Medicine Error: " + e.getMessage());
    }
}

    private void deleteMedicine() {
        try {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a medicine first.");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            service.deleteMedicine(id);
            loadMedicines();
            clearFields();
            medicineTable.clearSelection();
            writeOutput("Medicine deleted successfully. ID: " + id);
        } catch (Exception e) {
            writeOutput("Delete Medicine Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to delete medicine.");
        }
    }

    private void updateStock() {
        try {
            if (idField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select a medicine first.");
                return;
            }

            int id = Integer.parseInt(idField.getText());
            int newStock = Integer.parseInt(stockField.getText());

            service.updateStock(id, newStock);
            loadMedicines();
            writeOutput("Stock updated successfully. ID: " + id + ", New Stock: " + newStock);
        } catch (Exception e) {
            writeOutput("Update Stock Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to update stock.");
        }
    }

    private void showLowStock() {
        try {
            List<Medicine> lowStockMedicines = service.getLowStockMedicines();

            if (lowStockMedicines.isEmpty()) {
                writeOutput("There are no low stock medicines.");
                JOptionPane.showMessageDialog(this, "There are no low stock medicines.");
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("Low Stock Medicines:\n");

            for (Medicine medicine : lowStockMedicines) {
                sb.append("ID: ").append(medicine.getId())
                        .append(", Name: ").append(medicine.getName())
                        .append(", Stock: ").append(medicine.getStockQuantity())
                        .append("\n");
            }

            writeOutput(sb.toString());
            JOptionPane.showMessageDialog(this, sb.toString());
        } catch (Exception e) {
            writeOutput("Low Stock Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Failed to load low stock medicines.");
        }
    }

    private void searchMedicine() {
        String keyword = searchField.getText().trim().toLowerCase();

        if (keyword.isEmpty()) {
            loadMedicines();
            writeOutput("Search cleared. All medicines loaded.");
            return;
        }

        tableModel.setRowCount(0);

        List<Medicine> medicines = service.listMedicines();
        int foundCount = 0;

        for (Medicine medicine : medicines) {
            String name = medicine.getName().toLowerCase();
            String barcode = medicine.getBarcode().toLowerCase();

            if (name.contains(keyword) || barcode.contains(keyword)) {
                tableModel.addRow(new Object[]{
                        medicine.getId(),
                        medicine.getName(),
                        medicine.getBarcode(),
                        medicine.getPrice(),
                        medicine.getStockQuantity(),
                        medicine.getMinimumStockLevel(),
                        medicine.getExpiryDate(),
                        medicine.getCategoryId(),
                        medicine.getSupplierId()
                });
                foundCount++;
            }
        }

        clearFields();
        medicineTable.clearSelection();
        writeOutput("Search completed. Found " + foundCount + " matching medicine(s).");
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        barcodeField.setText("");
        priceField.setText("");
        stockField.setText("");
        minStockField.setText("");
        expiryDateField.setText("");
        categoryIdField.setText("");
        supplierIdField.setText("");
    }

    private void writeOutput(String message) {
        outputArea.append(message + "\n");
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}
