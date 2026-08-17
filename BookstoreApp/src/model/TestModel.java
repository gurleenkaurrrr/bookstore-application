package model;

public class TestModel {
    public static void main(String[] args) {
        
        BookStore store = BookStore.getInstance();
        
        store.addBook("COE428", 50.0);
        store.addBook("COE528", 100.0);
        store.addBook("MTH314", 200.0);
        store.addBook("ELE404", 500.0);
        store.addBook("CMN432", 75.0);
        System.out.println("Books added: " + store.getBooks().size()); // should print 5
        
        store.addCustomer("ayush", "passwrd");
        System.out.println("Customers added: " + store.getCustomers().size()); // should print 1
        
        User owner = store.authenticate("admin", "admin");
        System.out.println("Owner login: " + (owner != null ? "SUCCESS" : "FAILED")); // SUCCESS
        User customer = store.authenticate("ayush", "pass123");
        System.out.println("Customer login: " + (customer != null ? "SUCCESS" : "FAILED")); // SUCCESS
        User bad = store.authenticate("ayush", "wrongpass");
        System.out.println("Bad login: " + (bad != null ? "SUCCESS" : "FAILED")); // FAILED
        
        Customer ayush = (Customer) store.authenticate("ayush", "pass123");
        System.out.println("Initial Status: " + ayush.getStatus().getStatusName()); // Silver
        System.out.println("Initial Points: " + ayush.getPoints()); // 0
        
        double cost1 = 700.0;
        ayush.addPoints((int)(cost1 * ayush.getStatus().getPointsMultiplier()));
        System.out.println("Points after $700 purchase: " + ayush.getPoints()); // 7000
        System.out.println("Status (should be Gold): " + ayush.getStatus().getStatusName()); // Gold
        
        // redeem points and a buy COE428 50 dollar
        double cost2 = ayush.redeemPoints(50.0);
        ayush.addPoints((int)(cost2 * ayush.getStatus().getPointsMultiplier()));
        System.out.println("Cost after redeem (should be 0.0): " + cost2); // 0.0
        System.out.println("Points remaining (should be 2000): " + ayush.getPoints()); // 2000
        System.out.println("Status (should be Gold): " + ayush.getStatus().getStatusName()); // Gold
        
        // redeem points, buy COE528 
        double cost3 = ayush.redeemPoints(100.0);
        ayush.addPoints((int)(cost3 * ayush.getStatus().getPointsMultiplier()));
        System.out.println("Cost after redeem (should be 80.0): " + cost3); // 80.0
        System.out.println("Points remaining (should be 800): " + ayush.getPoints()); // 800
        System.out.println("Status (should be Silver): " + ayush.getStatus().getStatusName()); // Silver
        
        store.saveToFiles();
        System.out.println("Saved to files successfully");
        
        store.getBooks().clear();
        store.getCustomers().clear();
        store.loadFromFiles();
        System.out.println("Books loaded after reload: " + store.getBooks().size()); // 5
        System.out.println("Customers loaded after reload: " + store.getCustomers().size()); // ONE
        
        System.out.println("All tests done!");
    }
}