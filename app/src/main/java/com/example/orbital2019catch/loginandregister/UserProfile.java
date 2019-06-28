package com.example.orbital2019catch.loginandregister;

import com.example.orbital2019catch.profile.PaymentActivity;
import com.example.orbital2019catch.profile.PaymentRequest;

import java.util.ArrayList;

public class UserProfile {

    private String name;
    private String email;
    private double balance;

    public UserProfile() {}

    public UserProfile(String name, String email) {
        this.name = name;
        this.email = email;
        this.balance = 0;
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

}
