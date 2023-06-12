package com.example.signup_login_realtime;

public class HelperClass {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelepon() {
        return telepon;
    }

    public void setTelepon(String telepon) {
        this.telepon = telepon;
    }

    public HelperClass(String name, String email, String username, String password, String telepon) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.telepon = telepon;
    }

    String name, email, username, password, telepon;

}