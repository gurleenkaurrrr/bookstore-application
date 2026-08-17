package gui;

import bookstoreapp.BookStoreApp;
import model.Customer;
import model.BookStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OwnerCustomersPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable            table;
    private JTextField        usernameField;
    private JTextField        passwordField;
    private JLabel            errorLabel;

    public OwnerCustomersPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        tableModel = new DefaultTableModel(new String[]{"Username", "Password", "Points"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(460, 180));
        add(scrollPane, BorderLayout.NORTH);

        JPanel middle = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        middle.add(new JLabel("Username:"));
        usernameField = new JTextField(10);
        middle.add(usernameField);
        middle.add(new JLabel("Password:"));
        passwordField = new JTextField(10);
        middle.add(passwordField);
        JButton addBtn = new JButton("Add");
        middle.add(addBtn);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);

        JPanel middleWrapper = new JPanel(new BorderLayout());
        middleWrapper.add(middle,     BorderLayout.CENTER);
        middleWrapper.add(errorLabel, BorderLayout.SOUTH);
        add(middleWrapper, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 4));
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn   = new JButton("Back");
        bottom.add(deleteBtn);
        bottom.add(backBtn);
        add(bottom, BorderLayout.SOUTH);

        addBtn.addActionListener(e -> addCustomer());

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                errorLabel.setText("Please select a row to delete.");
                return;
            }
            String uname = (String) tableModel.getValueAt(row, 0);
            BookStore.getInstance().removeCustomer(uname);
            tableModel.removeRow(row);
            errorLabel.setText(" ");
        });

        backBtn.addActionListener(e -> {
            errorLabel.setText(" ");
            BookStoreApp.getInstance().showPanel(BookStoreApp.OWNER_START);
        });
    }

    private void addCustomer() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            errorLabel.setText("Both Username and Password are required.");
            return;
        }

        // Prevent duplicate usernames
        for (Customer c : BookStore.getInstance().getCustomers()) {
            if (c.getUsername().equalsIgnoreCase(username)) {
                errorLabel.setText("A customer with that username already exists.");
                return;
            }
        }

        BookStore.getInstance().addCustomer(username, password);
        tableModel.addRow(new Object[]{username, password, 0});
        usernameField.setText("");
        passwordField.setText("");
        errorLabel.setText(" ");
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Customer c : BookStore.getInstance().getCustomers()) {
            tableModel.addRow(new Object[]{c.getUsername(), c.getPassword(), c.getPoints()});
        }
        errorLabel.setText(" ");
    }
}