package com.example.orbital2019catch.personal.survey;

import com.example.orbital2019catch.R;

public class SurveyLibrarySpotify {

    private String companyName = "Spotify";

    private int companyLogoId = R.drawable.spotify_logo;

    private String mQuestions [] = {
            "How would you rate the value of Spotify?",
            "How easy was it to navigate our through Spotify?",
            "Compared to our competitors, is our product quality better, worse or the same?",
            "How likely are you to continue subscribing to Spotify?",
            "How likely are you to recommend Spotify to a friend?"
    };

    private String mChoices[][] = {
            {"Poor", "Average", "Good", "Extreme value for money"},
            {"Very difficult", "Difficult", "Easy", "Extremely easy"},
            {"Worse", "The same", "Better than average", "The best"},
            {"Not likely at all", "Not likely", "Likely", "Very likely"},
            {"Not likely at all", "Not likely", "Likely", "Very likely"}
    };

    public String getQuestion(int questionNumber) {
        return mQuestions[questionNumber];
    }

    public String getChoice1(int questionNumber) {
        return mChoices[questionNumber][0];
    }

    public String getChoice2(int questionNumber) {
        return mChoices[questionNumber][1];
    }

    public String getChoice3(int questionNumber) {
        return mChoices[questionNumber][2];
    }

    public String getChoice4(int questionNumber) {
        return mChoices[questionNumber][3];
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public int getCompanyLogoId() {
        return this.companyLogoId;
    }
}
