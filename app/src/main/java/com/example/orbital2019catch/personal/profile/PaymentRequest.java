package com.example.orbital2019catch.personal.profile;

public class PaymentRequest {
    private String phoneNumber;
    private double amount;
    private String date;
    private String time;

    public PaymentRequest() {
    }

    public PaymentRequest(String phoneNumber, double amount, String date, String time) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
        this.date = date;
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
