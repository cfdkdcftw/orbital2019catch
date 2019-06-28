package com.example.orbital2019catch.profile;

public class PaymentRequest {

    private String phoneNumber;
    private String amount;

    public PaymentRequest() {
    }

    public PaymentRequest(String phoneNumber, String amount) {
        this.phoneNumber = phoneNumber;
        this.amount = amount;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAmount() {
        return amount;
    }
}
