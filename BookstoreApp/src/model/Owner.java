package model;


/**
 *
 * @author gurleen
 */

// Owner is admin of Bookstore app
// Only one owner, hence using Singleton pattern (only one owner can be created)


public class Owner extends User {
    
    private static Owner instance;
    
    private Owner() {
        super("admin", "admin");
    }
    
    public static Owner getInstance() {
        if (instance == null) {
            instance = new Owner();
        }
        return instance;
    }
}