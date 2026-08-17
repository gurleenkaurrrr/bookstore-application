/**
 *
 * @author gurleen
 */

// Base class for both owner and customer
// holds basic info that any user of the app will need (username and pw)


package model;

public class User {
    
    protected String username;
    protected String password;
 
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public String getUsername() {
        return username;    
    }
    
    public String getPassword() {
        return password;
    }
    
    // check if typed in pw matches actual
    boolean authenticate(String pw) {
        return this.password.equals(pw);
    }
}