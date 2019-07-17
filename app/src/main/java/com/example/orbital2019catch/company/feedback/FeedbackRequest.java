package com.example.orbital2019catch.company.feedback;

public class FeedbackRequest {

    private String company;
    private String userEmail;
    private String userName;
    private String qns;
    private String payout;
    private String feedbackType;

    public FeedbackRequest() {

    }

    public FeedbackRequest(String company, String userEmail, String userName, String qns, String payout, String feedbackType) {
        this.company = company;
        this.userEmail = userEmail;
        this.userName = userName;
        this.qns = qns;
        this.payout = payout;
        this.feedbackType = feedbackType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQns() {
        return qns;
    }

    public void setQns(String qns) {
        this.qns = qns;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    public String getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(String feedbackType) {
        this.feedbackType = feedbackType;
    }
}
