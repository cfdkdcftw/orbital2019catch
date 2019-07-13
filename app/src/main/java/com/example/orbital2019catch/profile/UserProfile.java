package com.example.orbital2019catch.profile;

import com.example.orbital2019catch.profile.PaymentActivity;
import com.example.orbital2019catch.profile.PaymentRequest;

import java.util.ArrayList;

public class UserProfile {

    private String name;
    private String email;
    private double balance;
    private String role;
    private String companyName;

    public UserProfile() {}

    public UserProfile(String name, String email, String role, String companyName) {
        this.name = name;
        this.email = email;
        this.balance = 0;
        this.role = role;
        this.companyName = companyName;
    }

    public UserProfile(String name, String email, String role) {
        this.name = name;
        this.email = email;
        this.balance = 0;
        this.role = role;
        this.companyName = "personal account";
    }

    public UserProfile(String name, String email, double balance) {
        this.name = name;
        this.email = email;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void addBalance(double amount) {
        this.balance += amount;
    }

    public double getBalance() {
        return this.balance;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
