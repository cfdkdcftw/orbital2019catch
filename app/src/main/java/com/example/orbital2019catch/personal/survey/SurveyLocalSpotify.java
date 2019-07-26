package com.example.orbital2019catch.personal.survey;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SurveyLocalSpotify extends AppCompatActivity {

    private SurveyLibrarySpotify mSurvey =  new SurveyLibrarySpotify();
    // if we want to track question number
    // private TextView mQuestionNumberView;
    private ImageView mCompanyLogoView;
    private TextView mCompanySurveyDesc;
    private TextView mCompanyNameView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonChoice4;

    //  payment
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private double balance;
    private double amount = 1.0;

    private ArrayList<String> answers = new ArrayList<>();
    private String companyName = mSurvey.getCompanyName();
    private int companyLogoId = mSurvey.getCompanyLogoId();
    private int mQuestionNumber = 0;
    DatabaseReference databaseSurvey = FirebaseDatabase.getInstance().getReference("surveys/spotify/answers");

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference quotaRef = db.collection("surveys").document("spotify");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);
        mCompanyLogoView = (ImageView)findViewById(R.id.survey_company_logo);
        mCompanyLogoView.setImageResource(companyLogoId);
        mCompanySurveyDesc = (TextView) findViewById(R.id.survey_description);
        mCompanySurveyDesc.setText("Spotify Consumer Patterns Survey");
        mCompanyNameView = (TextView)findViewById(R.id.survey_company_name);
        mCompanyNameView.setText(companyName);
        // mQuestionNumberView = (TextView)findViewById(R.id.question_number);
        // mQuestionNumberView.setText(Integer.toString(mQuestionNumber));
        mQuestionView = (TextView)findViewById(R.id.question);
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
                answers.add(mSurvey.getChoice1(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 2
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice2(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 3
        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice3(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 4
        mButtonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice4(mQuestionNumber-1));
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        if (mQuestionNumber == 5) {
            uploadUserInput();
            updateDatabase();

            Toast.makeText(SurveyLocalSpotify.this, "Thank you for completing the survey!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, SurveysHomeActivity.class);
            finish();
            startActivity(intent);
            overridePendingTransition(0,0);
        } else {
            // if we want to track question number
            // mQuestionNumberView.setText(Integer.toString(mQuestionNumber));
            mQuestionView.setText(mSurvey.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mSurvey.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mSurvey.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mSurvey.getChoice3(mQuestionNumber));
            mButtonChoice4.setText(mSurvey.getChoice4(mQuestionNumber));
            mQuestionNumber++;
        }
    }

    private void uploadUserInput() {
        SurveyResponse surveyResponse = new SurveyResponse(answers.get(0), answers.get(1), answers.get(2),
                answers.get(3), answers.get(4));
        String id = databaseSurvey.push().getKey();
        databaseSurvey.child(id).setValue(surveyResponse);
        // addCredits(); null obj ref for balance
        DatabaseReference databaseReference = mDatabase.getReference("users/" + mAuth.getUid());
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                balance = userProfile.getBalance();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SurveyLocalSpotify.this, databaseError.getCode(), Toast.LENGTH_LONG).show();
            }
        });

        DatabaseReference balanceRef = mDatabase.getReference("users/" + mAuth.getUid()).child("balance");
        balanceRef.setValue(balance + 1.0);

    }

    private void updateDatabase() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        String userID = mAuth.getUid();
        quotaRef.update("curr", FieldValue.increment(1));
        quotaRef.update("usersWhoCompleted", FieldValue.arrayUnion(userID));
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
