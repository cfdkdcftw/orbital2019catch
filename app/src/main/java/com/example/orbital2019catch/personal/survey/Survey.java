package com.example.orbital2019catch.personal.survey;

import java.util.Date;
import java.util.List;

public class Survey {
    private String name, expiry, brand;
    private List<String> usersWhoCompleted, categories;
    private double cashout;
    private long max, curr;
    private Date expiryDF;

    public Survey () {}

    public Survey(String name, String expiry, double cashout, String brand, List<String> usersWhoCompleted, List<String> categories, long max, long curr, Date expiryDF) {
        this.name = name;
        this.expiry = expiry;
        this.cashout = cashout;
        this.brand = brand;
        this.usersWhoCompleted = usersWhoCompleted;
        this.categories = categories;
        this.max = max;
        this.curr = curr;
        this.expiryDF = expiryDF;
    }

    public String getName() {
        return name;
    }

    public String getExpiry() {
        return expiry;
    }

    public double getCashout() {
        return cashout;
    }

    public long getMax() {
        return max;
    }

    public long getCurr() {
        return curr;
    }

    public String getBrand() {
        return brand;
    }

    public List<String> getUsersWhoCompleted() { return usersWhoCompleted; }

    public Date getExpiryDF() { return expiryDF; }

    public List<String> getCategories() { return categories; }
}
