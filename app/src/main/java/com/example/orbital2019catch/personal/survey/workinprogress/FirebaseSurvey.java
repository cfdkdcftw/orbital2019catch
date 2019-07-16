package com.example.orbital2019catch.personal.survey.workinprogress;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseSurvey extends Survey {

    private String surveyName;
    private List<String> questions = new ArrayList<>();
    private List<String> choices = new ArrayList<>();

    private Firebase mQuestionRef;
    private Firebase mChoice1Ref;
    private Firebase mChoice2Ref;
    private Firebase mChoice3Ref;
    private Firebase mChoice4Ref;


    public FirebaseSurvey(String companyName, String surveyName, String description, double payout, String logoURL) {
        super(companyName, surveyName, description, payout, logoURL);
        this.surveyName = surveyName;
        getFirebaseSurvey();
    }

    public String getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }

    public String getChoice1(int questionNumber) {
        return questions.get(0 + questionNumber * 4);
    }

    public String getChoice2(int questionNumber) {
        return questions.get(1 + questionNumber * 4);
    }

    public String getChoice3(int questionNumber) {
        return questions.get(2 + questionNumber * 4);
    }

    public String getChoice4(int questionNumber) {
        return questions.get(3 + questionNumber * 4);
    }

    public void getFirebaseSurvey() {
        for (int i = 0; i < 5; i++) {
            mQuestionRef = new Firebase("https://orbital2019catch.firebaseio.com/surveys/" + surveyName + "/" + i + "/question");
            mQuestionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    questions.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice1Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/" + surveyName + "/" + i + "/choice1");
            mChoice1Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choices.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice2Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/" + surveyName + "/" + i + "/choice2");
            mChoice2Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choices.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice3Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/" + surveyName + "/" + i + "/choice3");
            mChoice3Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choices.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice4Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/" + surveyName + "/" + i + "/choice4");
            mChoice4Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    choices.add(dataSnapshot.getValue(String.class));
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }
    }
}
