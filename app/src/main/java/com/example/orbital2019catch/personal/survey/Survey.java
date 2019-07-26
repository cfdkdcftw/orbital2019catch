package com.example.orbital2019catch.personal.survey;

import java.util.List;

public class Survey {
    private String name, expiry, cashout, brand;
    private List<String> usersWhoCompleted;
    private long max, curr;

    public Survey () {}

    public Survey(String name, String expiry, String cashout, String brand, List<String> usersWhoCompleted, long max, long curr) {
        this.name = name;
        this.expiry = expiry;
        this.cashout = cashout;
        this.brand = brand;
        this.usersWhoCompleted = usersWhoCompleted;
        this.max = max;
        this.curr = curr;
    }

    public String getName() {
        return name;
    }

    public String getExpiry() {
        return expiry;
    }

    public String getCashout() {
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
}
