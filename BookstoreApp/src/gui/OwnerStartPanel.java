package gui;

import bookstoreapp.BookStoreApp;
import javax.swing.*;
import java.awt.*;

public class OwnerStartPanel extends JPanel {

    public OwnerStartPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets    = new Insets(10, 60, 10, 60);
        gbc.fill      = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;

        JButton booksBtn     = new JButton("Books");
        JButton customersBtn = new JButton("Customers");
        JButton logoutBtn    = new JButton("Logout");

        Dimension btnSize = new Dimension(160, 35);
        booksBtn.setPreferredSize(btnSize);
        customersBtn.setPreferredSize(btnSize);
        logoutBtn.setPreferredSize(btnSize);

        gbc.gridy = 0; add(booksBtn,     gbc);
        gbc.gridy = 1; add(customersBtn, gbc);
        gbc.gridy = 2; add(logoutBtn,    gbc);

        booksBtn.addActionListener(e ->
                BookStoreApp.getInstance().showPanel(BookStoreApp.OWNER_BOOKS));

        customersBtn.addActionListener(e ->
                BookStoreApp.getInstance().showPanel(BookStoreApp.OWNER_CUSTOMERS));

        logoutBtn.addActionListener(e ->
                BookStoreApp.getInstance().showPanel(BookStoreApp.LOGIN));
    }
}