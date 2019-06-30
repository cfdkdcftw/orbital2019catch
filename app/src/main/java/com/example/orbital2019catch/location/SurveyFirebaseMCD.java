package com.example.orbital2019catch.location;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.loginandregister.UserProfile;
import com.example.orbital2019catch.survey.SurveyFirebaseNike;
import com.example.orbital2019catch.survey.SurveyResponse;
import com.example.orbital2019catch.survey.SurveysHomeActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class SurveyFirebaseMCD extends AppCompatActivity {

    private TextView mCompanyNameView;
    private ImageView mCompanyLogoView;
    private TextView mQuestion;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    private Firebase mQuestionRef;
    private Firebase mChoice1Ref;
    private Firebase mChoice2Ref;
    private Firebase mChoice3Ref;
    private Firebase mChoice4Ref;

    //  payment
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private double balance;

    private ArrayList<String> answers = new ArrayList<>();
    private int mQuestionNumber = 0;
    private String companyName = "McDonald's";

    DatabaseReference databaseSurvey = FirebaseDatabase.getInstance().getReference("surveys/mcd/answers");

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);
        mCompanyNameView = (TextView)findViewById(R.id.survey_company_name);
        mCompanyNameView.setText(companyName);
        mCompanyLogoView = (ImageView)findViewById(R.id.survey_company_logo);
        mCompanyLogoView.setImageResource(R.drawable.mcd);
        mQuestion = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        updateQuestion();

        // Button Listener for Button 1
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mButtonChoice1.getText().toString());
                updateQuestion();
            }
        });

        // Button Listener for Button 2
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mButtonChoice2.getText().toString());
                updateQuestion();
            }
        });

        // Button Listener for Button 3
        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mButtonChoice3.getText().toString());
                updateQuestion();
            }
        });

        // Button Listener for Button 4
        mButtonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mButtonChoice4.getText().toString());
                updateQuestion();
            }
        });
    }

    public void updateQuestion() {
        if (mQuestionNumber == 5) {
            Toast.makeText(this, "Thank you for completing the survey!", Toast.LENGTH_SHORT).show();
            uploadUserInput();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
        } else {
            mQuestionRef = new Firebase("https://orbital2019catch.firebaseio.com/surveys/mcd/questions/" + mQuestionNumber + "/question");

            mQuestionRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.getValue(String.class);
                    mQuestion.setText(question);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice1Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/mcd/questions/" + mQuestionNumber + "/choice1");
            mChoice1Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice1 = dataSnapshot.getValue(String.class);
                    mButtonChoice1.setText(choice1);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice2Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/mcd/questions/" + mQuestionNumber + "/choice2");
            mChoice2Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice2 = dataSnapshot.getValue(String.class);
                    mButtonChoice2.setText(choice2);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice3Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/mcd/questions/" + mQuestionNumber + "/choice3");
            mChoice3Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice3 = dataSnapshot.getValue(String.class);
                    mButtonChoice3.setText(choice3);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            mChoice4Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/mcd/questions/" + mQuestionNumber + "/choice4");
            mChoice4Ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String choice4 = dataSnapshot.getValue(String.class);
                    mButtonChoice4.setText(choice4);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            mQuestionNumber++;
        }
    }

    private void uploadUserInput() {
        SurveyResponse surveyResponse = new SurveyResponse(answers.get(0), answers.get(1), answers.get(2),
                answers.get(3), answers.get(4));
        String id = databaseSurvey.push().getKey();
        databaseSurvey.child(id).setValue(surveyResponse);
        // addCredits(); null obj ref for balance
        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                balance = userProfile.getBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SurveyFirebaseMCD.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        DatabaseReference balanceRef = mDatabase.getReference(mAuth.getUid()).child("balance");
        balanceRef.setValue(balance + 0.3);

    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
