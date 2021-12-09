package com.jumperbud.models;

public class User {
    public String fullName, age, email;
    public boolean admin;

    public User() {
    }

    public User(String fullName, String age, String email, boolean admin) {
        this.fullName = fullName;
        this.age = age;
        this.email = email;
        this.admin = admin;
    }
}
