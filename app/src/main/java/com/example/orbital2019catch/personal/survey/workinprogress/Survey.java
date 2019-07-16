package com.example.orbital2019catch.personal.survey.workinprogress;

public abstract class Survey {
    
    private String companyName;
    private String surveyName;
    private String description;
    private double payout;
    private String logoURL;

    public Survey(String companyName, String surveyName, String description, double payout, String logoURL) {
        this.companyName = companyName;
        this.surveyName = surveyName;
        this.description = description;
        this.payout = payout;
        this.logoURL = logoURL;
    }

    public String getLogoURL() {
        return this.logoURL;
    }

    public String getCompanyName(){
        return this.companyName;
    }

    public String getDescription() {
        return this.description;
    }

    public double getPayout() {
        return this.payout;
    }

    public boolean isDone() {
        // check against firebase to see if user has done the survey
        return false;
    }

    public String getQuestion(int questionNumber) {
        return "";
    }

    public String getChoice1(int questionNumber) {
        return "";
    }

    public String getChoice2(int questionNumber) {
        return "";
    }

    public String getChoice3(int questionNumber) {
        return "";
    }

    public String getChoice4(int questionNumber) {
        return "";
    }
}
