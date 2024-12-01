package com.example.finall.entity;
public enum Roles {
    USER, ADMIN;
    public String getAuthority() {
        return name();
    }
}
