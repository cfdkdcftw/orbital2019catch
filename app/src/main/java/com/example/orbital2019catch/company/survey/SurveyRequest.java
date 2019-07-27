package com.example.orbital2019catch.company.survey;

public class SurveyRequest {

    private String company;
    private String userEmail;
    private String userName;
    private String qns;
    private String payout;
    private String feedbackType;

    public SurveyRequest() {

    }

    public SurveyRequest(String company, String userEmail, String userName, String payout, String feedbackType, String[] qns1, String[] qns2, String[] qns3, String[] qns4, String[] qns5) {
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
