package entity;

public class User {
    private int id;
    private String username;
    private String password;
    private boolean isAdmin;
    
    public User(int id, String username, String password, boolean isAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    // getter和setter方法
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean isAdmin() { return isAdmin; }
    // ...
} 