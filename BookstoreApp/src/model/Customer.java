package model;

/**
 *
 * @author Ayush
 */

public class Customer extends User {
    private int points;
    private CustomerStatus status;

    public Customer(String username, String password) {
        super(username, password);
        this.points = 0;
        this.status = new SilverStatus(); // always starts at silver
    }

    public int getPoints() { return points; }

    public void addPoints(int pts) {
        this.points += pts;
        status.upgrade(this); // check if should upgrade after adding points
    }

    // returns how much customer actually pays after redeeming points
    // every 100 pts = $1 off, cost cannot go below $0
    public double redeemPoints(double cost) {
        // max dollars we can discount based on points (100 pts = $1)
        double maxDiscount = points / 100.0;

        // can't discount more than the total cost
        double discount = Math.min(maxDiscount, cost);

        // deduct only the points actually used
        int pointsUsed = (int)(discount * 100);
        this.points -= pointsUsed;

        double newCost = cost - discount;
        status.downgrade(this);
        return newCost;
    }
    public CustomerStatus getStatus() { return status; }
    public void setStatus(CustomerStatus s) { this.status = s; }

    public void updateStatus() {
        status.upgrade(this);
        status.downgrade(this);
    }

    @Override
    public String toString() {
        return username + "," + password + "," + points;
    }
}