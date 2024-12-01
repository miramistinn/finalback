package com.example.finall.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class User {
    private String email;
    private boolean isAdmin;

    private static User instance;
    private User(String email, boolean isAdmin) {
        this.email = email;
        this.isAdmin = isAdmin;
    }

    public static User getInstance() {
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User("", false);
                }
            }
        }
        return instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    //как меенять
    //User user = User.getInstance();
    //user.setId(1);
    //user.setAdmin(true);
}