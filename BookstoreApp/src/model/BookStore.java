package model;

/**
 *
 * @author Ayush
 */

import java.util.ArrayList;
import java.io.*;

public class BookStore {
    private ArrayList<Book> books;
    private ArrayList<Customer> customers;
    private static BookStore instance;

    private BookStore() {
        books = new ArrayList<>();
        customers = new ArrayList<>();
    }

    public static BookStore getInstance() {
        if (instance == null) {
            instance = new BookStore();
        }
        return instance;
    }

    public void addBook(String name, double price) {
        books.add(new Book(name, price));
    }

    public void removeBook(String name) {
        books.removeIf(b -> b.getName().equals(name));
    }

    public void addCustomer(String username, String password) {
        customers.add(new Customer(username, password));
    }

    public void removeCustomer(String username) {
        customers.removeIf(c -> c.getUsername().equals(username));
    }

    // returns Owner if admin login, Customer if customer login, null if failed
    public User authenticate(String username, String password) {
        Owner owner = Owner.getInstance();
        if (owner.getUsername().equals(username) && owner.authenticate(password)) {
            return owner;
        }
        for (Customer c : customers) {
            if (c.getUsername().equals(username) && c.authenticate(password)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Book> getBooks() { return books; }
    public ArrayList<Customer> getCustomers() { return customers; }

    public void saveToFiles() {
        try {
            PrintWriter bw = new PrintWriter(new FileWriter("books.txt"));
            for (Book b : books) bw.println(b.toString());
            bw.close();

            PrintWriter cw = new PrintWriter(new FileWriter("customers.txt"));
            for (Customer c : customers) cw.println(c.toString());
            cw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFiles() {
        try {
            File bf = new File("books.txt");
            if (bf.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(bf));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    books.add(new Book(parts[0], Double.parseDouble(parts[1])));
                }
                br.close();
            }

            File cf = new File("customers.txt");
            if (cf.exists()) {
                BufferedReader cr = new BufferedReader(new FileReader(cf));
                String line;
                while ((line = cr.readLine()) != null) {
                    String[] parts = line.split(",");
                    Customer c = new Customer(parts[0], parts[1]);
                    // use setStatus workaround to avoid double-upgrading on load
                    int savedPoints = Integer.parseInt(parts[2]);
                    c.addPoints(savedPoints);
                    customers.add(c);
                }
                cr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}