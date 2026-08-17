/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author gurleen
 */

// GoldStatus represents gold status level
// customer reached gold level with 1000 pts or more

// part of state design pattern

public class GoldStatus implements CustomerStatus {
 
    // if pts drop below 100, they go back to silver
    private static final int THRESHOLD = 1000;
    
    // returning status name to display in UI
    @Override
    public String getStatusName() {
        return "Gold";
    }
    
    // no extra discount given to gold customers beyond points redemption
    // return original cost without changing
    @Override
    public double getDiscount(double cost) {
        return cost;
    }
    
    @Override
    public int getPointsMultiplier() {
        return 10;
    }
    
    // highest status is gold, so nothing to upgrade to
    @Override 
    public void upgrade(Customer c) {
    }
    
    // checking if pts drop below 1000, if yes downgrade to silver
    @Override
    public void downgrade(Customer c) {
        if (c.getPoints() < THRESHOLD) {
            c.setStatus( new SilverStatus());
        }
    }
}