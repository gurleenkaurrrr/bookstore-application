package gui;

import bookstoreapp.BookStoreApp;
import model.*;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    private JTextField     usernameField;
    private JPasswordField passwordField;
    private JLabel         errorLabel;

    // Holds the logged-in customer so CustomerStartPanel can reference it
    private static Customer currentCustomer;

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill   = GridBagConstraints.HORIZONTAL;

        // Title
        JLabel title = new JLabel("Welcome to the BookStore App");
        title.setFont(new Font("SansSerif", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        // Username
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Username:"), gbc);
        usernameField = new JTextField(15);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password
        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login button
        JButton loginBtn = new JButton("Login");
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        gbc.fill  = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginBtn, gbc);

        // Error label
        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        add(errorLabel, gbc);

        loginBtn.addActionListener(e -> doLogin());

        // Allow pressing Enter in password field
        passwordField.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        User user = BookStore.getInstance().authenticate(username, password);
        if (user == null) {
            errorLabel.setText("Invalid username or password.");
            return;
        }

        errorLabel.setText(" ");
        usernameField.setText("");
        passwordField.setText("");

        BookStoreApp app = BookStoreApp.getInstance();
        if (user instanceof Owner) {
            app.showPanel(BookStoreApp.OWNER_START);
        } else {
            currentCustomer = (Customer) user;
            app.showPanel(BookStoreApp.CUSTOMER_START);
        }
    }

    public static Customer getCurrentCustomer() { return currentCustomer; }
    public static void clearCurrentCustomer()   { currentCustomer = null; }
}