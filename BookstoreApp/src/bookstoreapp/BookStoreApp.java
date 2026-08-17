package bookstoreapp;

import gui.*;
import model.BookStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class BookStoreApp extends JFrame {

    private static BookStoreApp instance;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public static final String LOGIN           = "LOGIN";
    public static final String OWNER_START     = "OWNER_START";
    public static final String OWNER_BOOKS     = "OWNER_BOOKS";
    public static final String OWNER_CUSTOMERS = "OWNER_CUSTOMERS";
    public static final String CUSTOMER_START  = "CUSTOMER_START";
    public static final String CUSTOMER_COST   = "CUSTOMER_COST";

    private LoginPanel           loginPanel;
    private OwnerStartPanel      ownerStartPanel;
    private OwnerBooksPanel      ownerBooksPanel;
    private OwnerCustomersPanel  ownerCustomersPanel;
    private CustomerStartPanel   customerStartPanel;
    private CustomerCostPanel    customerCostPanel;

    public BookStoreApp() {
        instance = this;
        BookStore.getInstance().loadFromFiles();

        setTitle("Bookstore App");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        // Save on window close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                BookStore.getInstance().saveToFiles();
                System.exit(0);
            }
        });

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);

        loginPanel           = new LoginPanel();
        ownerStartPanel      = new OwnerStartPanel();
        ownerBooksPanel      = new OwnerBooksPanel();
        ownerCustomersPanel  = new OwnerCustomersPanel();
        customerStartPanel   = new CustomerStartPanel();
        customerCostPanel    = new CustomerCostPanel();

        mainPanel.add(loginPanel,           LOGIN);
        mainPanel.add(ownerStartPanel,      OWNER_START);
        mainPanel.add(ownerBooksPanel,      OWNER_BOOKS);
        mainPanel.add(ownerCustomersPanel,  OWNER_CUSTOMERS);
        mainPanel.add(customerStartPanel,   CUSTOMER_START);
        mainPanel.add(customerCostPanel,    CUSTOMER_COST);

        add(mainPanel);
        showPanel(LOGIN);
        setVisible(true);
    }

    public static BookStoreApp getInstance() { return instance; }

    public void showPanel(String name) {
        if (name.equals(OWNER_BOOKS))           ownerBooksPanel.refresh();
        if (name.equals(OWNER_CUSTOMERS))       ownerCustomersPanel.refresh();
        if (name.equals(CUSTOMER_START))        customerStartPanel.refresh();
        cardLayout.show(mainPanel, name);
    }

    // Called by LoginPanel after successful customer login
    public void showCustomerCost(double totalCost, int points, String status) {
        customerCostPanel.setData(totalCost, points, status);
        cardLayout.show(mainPanel, CUSTOMER_COST);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BookStoreApp::new);
    }
}