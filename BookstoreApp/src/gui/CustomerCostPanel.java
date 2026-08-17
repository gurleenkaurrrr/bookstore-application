package gui;

import bookstoreapp.BookStoreApp;
import javax.swing.*;
import java.awt.*;

public class CustomerCostPanel extends JPanel {

    private JLabel costLabel;
    private JLabel pointsStatusLabel;

    public CustomerCostPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets    = new Insets(14, 20, 14, 20);
        gbc.gridwidth = 1;
        gbc.anchor    = GridBagConstraints.CENTER;

        costLabel = new JLabel("Total Cost: ");
        costLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        gbc.gridy = 0;
        add(costLabel, gbc);

        pointsStatusLabel = new JLabel("Points: 0, Status: Silver");
        pointsStatusLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridy = 1;
        add(pointsStatusLabel, gbc);

        JButton logoutBtn = new JButton("Logout");
        gbc.gridy = 2;
        add(logoutBtn, gbc);

        logoutBtn.addActionListener(e -> {
            LoginPanel.clearCurrentCustomer();
            BookStoreApp.getInstance().showPanel(BookStoreApp.LOGIN);
        });
    }

    /** Populate labels before this panel is shown. */
    public void setData(double totalCost, int points, String status) {
        // Format cost: show as integer if it's a whole number, else 2 decimal places
        String costStr = (totalCost == Math.floor(totalCost))
                ? String.valueOf((int) totalCost)
                : String.format("%.2f", totalCost);

        costLabel.setText("Total Cost: " + costStr);
        pointsStatusLabel.setText("Points: " + points + ", Status: " + status);
    }
}