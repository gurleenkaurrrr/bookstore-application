package gui;

import bookstoreapp.BookStoreApp;
import model.Book;
import model.BookStore;
import model.Customer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class CustomerStartPanel extends JPanel {

    private JLabel            welcomeLabel;
    private DefaultTableModel tableModel;
    private JTable            table;
    private JLabel            errorLabel;

    public CustomerStartPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        welcomeLabel = new JLabel(" ");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price", "Select"}, 0) {
            @Override
            public Class<?> getColumnClass(int col) {
                return col == 2 ? Boolean.class : Object.class;
            }
            @Override
            public boolean isCellEditable(int r, int c) {
                return c == 2; // only checkbox column is editable
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel bottom = new JPanel(new BorderLayout());

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 4));
        JButton buyBtn    = new JButton("Buy");
        JButton redeemBtn = new JButton("Redeem points and Buy");
        JButton logoutBtn = new JButton("Logout");
        btnPanel.add(buyBtn);
        btnPanel.add(redeemBtn);
        btnPanel.add(logoutBtn);

        bottom.add(errorLabel, BorderLayout.NORTH);
        bottom.add(btnPanel,   BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);

        buyBtn.addActionListener(e -> handleBuy(false));
        redeemBtn.addActionListener(e -> handleBuy(true));
        logoutBtn.addActionListener(e -> {
            LoginPanel.clearCurrentCustomer();
            BookStoreApp.getInstance().showPanel(BookStoreApp.LOGIN);
        });
    }

    private void handleBuy(boolean redeem) {
        // Collect selected books
        ArrayList<Book> selected = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            Boolean checked = (Boolean) tableModel.getValueAt(i, 2);
            if (checked != null && checked) {
                String bookName = (String) tableModel.getValueAt(i, 0);
                for (Book b : BookStore.getInstance().getBooks()) {
                    if (b.getName().equals(bookName)) {
                        selected.add(b);
                        break;
                    }
                }
            }
        }

        if (selected.isEmpty()) {
            errorLabel.setText("Please select at least one book.");
            return;
        }
        errorLabel.setText(" ");

        Customer customer = LoginPanel.getCurrentCustomer();
        double totalCost = 0;
        for (Book b : selected) totalCost += b.getPrice();

        double finalCost;
        if (redeem) {
            finalCost = customer.redeemPoints(totalCost);
        } else {
            finalCost = totalCost;
        }

        int multiplier = customer.getStatus().getPointsMultiplier();
        customer.addPoints((int)(finalCost * multiplier));

        BookStoreApp.getInstance().showCustomerCost(
                finalCost,
                customer.getPoints(),
                customer.getStatus().getStatusName()
        );
    }

    /** Reload data called whenever panel becomes visible. */
    public void refresh() {
        Customer customer = LoginPanel.getCurrentCustomer();
        if (customer == null) return;

        welcomeLabel.setText("Welcome " + customer.getUsername()
                + ".  You have " + customer.getPoints()
                + " points.  Your status is " + customer.getStatus().getStatusName() + ".");

        tableModel.setRowCount(0);
        for (Book b : BookStore.getInstance().getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), b.getPrice(), false});
        }
        errorLabel.setText(" ");
    }
}