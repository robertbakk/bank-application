package model;

import java.util.UUID;

public class User {

    private String id;

    private String username;

    private String password;

    private String name;

    private boolean admin;

    public User(String username, String password, String name, boolean admin) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }

    public User(String id, String username, String password, String name, boolean admin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.admin = admin;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public boolean isAdmin() {
        return admin;
    }

}
