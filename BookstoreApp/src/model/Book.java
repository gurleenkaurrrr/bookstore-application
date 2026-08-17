package model;

/**
 *
 * @author Ayush
 */


public class Book {
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() { return name; }
    public void setName(String n) { this.name = n; }
    public double getPrice() { return price; }
    public void setPrice(double p) { this.price = p; }

    @Override
    public String toString() {
        return name + "," + price;
    }
}