package model;

/**
 *
 * @author Ayush
 */

public interface CustomerStatus {
    String getStatusName();
    double getDiscount(double cost);
    int getPointsMultiplier();
    void upgrade(Customer c);
    void downgrade(Customer c);
}