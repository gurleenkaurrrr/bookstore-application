package gui;

import bookstoreapp.BookStoreApp;
import model.Book;
import model.BookStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OwnerBooksPanel extends JPanel {

    private DefaultTableModel tableModel;
    private JTable            table;
    private JTextField        nameField;
    private JTextField        priceField;
    private JLabel            errorLabel;

    public OwnerBooksPanel() {
        setLayout(new BorderLayout(5, 5));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        tableModel = new DefaultTableModel(new String[]{"Book Name", "Book Price"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(460, 180));
        add(scrollPane, BorderLayout.NORTH);

        JPanel middle = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 4));
        middle.add(new JLabel("Name:"));
        nameField = new JTextField(10);
        middle.add(nameField);
        middle.add(new JLabel("Price:"));
        priceField = new JTextField(6);
        middle.add(priceField);
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

        addBtn.addActionListener(e -> addBook());

        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                errorLabel.setText("Please select a row to delete.");
                return;
            }
            String bookName = (String) tableModel.getValueAt(row, 0);
            BookStore.getInstance().removeBook(bookName);
            tableModel.removeRow(row);
            errorLabel.setText(" ");
        });

        backBtn.addActionListener(e -> {
            errorLabel.setText(" ");
            BookStoreApp.getInstance().showPanel(BookStoreApp.OWNER_START);
        });
    }

    private void addBook() {
        String name = nameField.getText().trim();
        String priceStr = priceField.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty()) {
            errorLabel.setText("Both Name and Price are required.");
            return;
        }
        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            errorLabel.setText("Price must be a positive number.");
            return;
        }

        for (Book b : BookStore.getInstance().getBooks()) {
            if (b.getName().equalsIgnoreCase(name)) {
                errorLabel.setText("A book with that name already exists.");
                return;
            }
        }

        BookStore.getInstance().addBook(name, price);
        tableModel.addRow(new Object[]{name, price});
        nameField.setText("");
        priceField.setText("");
        errorLabel.setText(" ");
    }

    public void refresh() {
        tableModel.setRowCount(0);
        for (Book b : BookStore.getInstance().getBooks()) {
            tableModel.addRow(new Object[]{b.getName(), b.getPrice()});
        }
        errorLabel.setText(" ");
    }
}