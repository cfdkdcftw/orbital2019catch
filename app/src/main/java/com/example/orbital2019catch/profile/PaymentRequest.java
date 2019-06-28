package com.example.orbital2019catch.profile;

public class PaymentRequest {
    private String phoneNumber;
    private double amount;

    public PaymentRequest() {
    }

    public PaymentRequest(String phoneNumber, double amount) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getAmount() {
        return amount;
    }
}
