package org.mavensample.ui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;

import org.mavensample.model.Medicine;
import org.mavensample.model.User;
import org.mavensample.service.InventoryService;

public class StaffFrame extends JFrame {

    private User user;
    private InventoryService service;

    private JTable table;
    private DefaultTableModel model;

    private JTextField idField;
    private JTextField nameField;
    private JTextField stockField;
    private JTextField quantityField;

    private JTextArea outputArea;

    public StaffFrame(User user) {
        this.user = user;
        this.service = new InventoryService();

        setTitle("Staff Panel - " + user.getUsername());
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(createTablePanel(), BorderLayout.CENTER);
        add(createControlPanel(), BorderLayout.EAST);
        add(createOutputPanel(), BorderLayout.SOUTH);

        loadMedicines();

        setVisible(true);
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Medicine List"));

        model = new DefaultTableModel();
        model.setColumnIdentifiers(new String[]{"ID", "Name", "Stock"});

        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        table.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> fillFields());

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder("Operations"));

        idField = new JTextField();
        idField.setEditable(false);

        nameField = new JTextField();
        nameField.setEditable(false);

        stockField = new JTextField();
        stockField.setEditable(false);

        quantityField = new JTextField();

        panel.add(new JLabel("ID:"));
        panel.add(idField);

        panel.add(new JLabel("Name:"));
        panel.add(nameField);

        panel.add(new JLabel("Stock:"));
        panel.add(stockField);

        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);

        panel.add(Box.createVerticalStrut(15));

        JButton stockInBtn = new JButton("Stock In");
        JButton stockOutBtn = new JButton("Stock Out");
        JButton lowStockBtn = new JButton("Low Stock");
        JButton refreshBtn = new JButton("Refresh");

        stockInBtn.addActionListener(e -> stockIn());
        stockOutBtn.addActionListener(e -> stockOut());
        lowStockBtn.addActionListener(e -> showLowStock());
        refreshBtn.addActionListener(e -> loadMedicines());

        panel.add(stockInBtn);
        panel.add(stockOutBtn);
        panel.add(lowStockBtn);
        panel.add(refreshBtn);

        return panel;
    }

    private JScrollPane createOutputPanel() {
        outputArea = new JTextArea(6, 20);
        outputArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Output"));

        return scrollPane;
    }

    private void loadMedicines() {
        model.setRowCount(0);

        List<Medicine> list = service.listMedicines();

        for (Medicine m : list) {
            model.addRow(new Object[]{
                    m.getId(),
                    m.getName(),
                    m.getStockQuantity()
            });
        }

        writeOutput("Medicine list loaded.");
    }

    private void fillFields() {
        int row = table.getSelectedRow();

        if (row == -1) return;

        idField.setText(model.getValueAt(row, 0).toString());
        nameField.setText(model.getValueAt(row, 1).toString());
        stockField.setText(model.getValueAt(row, 2).toString());
    }

    private void stockIn() {
        try {
            int id = Integer.parseInt(idField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            service.stockIn(id, user.getId(), qty);
            loadMedicines();

            writeOutput("Stock In → ID: " + id + " + " + qty);
        } catch (Exception e) {
            writeOutput("Error: " + e.getMessage());
        }
    }

    private void stockOut() {
        try {
            int id = Integer.parseInt(idField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            service.stockOut(id, user.getId(), qty);
            loadMedicines();

            writeOutput("Stock Out → ID: " + id + " - " + qty);
        } catch (Exception e) {
            writeOutput("Error: " + e.getMessage());
        }
    }

    private void showLowStock() {
        List<Medicine> lowList = service.getLowStockMedicines();

        if (lowList.isEmpty()) {
            writeOutput("No low stock medicines.");
            return;
        }

        writeOutput("Low Stock:");
        for (Medicine m : lowList) {
            writeOutput(m.getName() + " → " + m.getStockQuantity());
        }
    }

    private void writeOutput(String msg) {
        outputArea.append(msg + "\n");
    }
}
