/**
 *
 * @author gurleen
 */

// SilverStatus represents silver membership level
// customer starts at silver and stays at silver until they reach 1000 pts
// part of state design pattern; customer's behaviour will change based on their status

package model;

public class SilverStatus implements CustomerStatus {
    
    private static final int THRESHOLD = 1000;
    
    @Override
    public String getStatusName() {
        return "Silver";
    }
    
    // no discount for silver, return original cost
    @Override
    public double getDiscount(double cost) {
        return cost;
    }
    
    // 10 pts per $1 spent
    @Override
    public int getPointsMultiplier() {
        return 10;
    }
    
    // upgrade to gold if 1000+ pts
    @Override 
    public void upgrade(Customer c) {
        if (c.getPoints() >= THRESHOLD) {
            c.setStatus(new GoldStatus());
        }
    }
    
    // already lowest status, nothing to do
    @Override
    public void downgrade(Customer c) {
    }
}