package com.example.development_01.core.data.firebase.models;

public class User {
    private String userName;
    private String email;
    //private String password;
    private String role;

    // Constructors
    public User() {}

    public User(String userName, String email, String role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    // Getters and setters
    public String getUserName() {
        return userName;
    }

    public void setUserName() {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
