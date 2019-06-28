package com.example.orbital2019catch.location;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.feedback.Feedback;
import com.example.orbital2019catch.feedback.FeedbackHomeActivity;
import com.example.orbital2019catch.loginandregister.UserProfile;
import com.example.orbital2019catch.survey.SurveyLocalSpotify;
import com.example.orbital2019catch.survey.SurveysHomeActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JewelFeedbackActivity extends Activity{

    private TextView companyName;
    private ImageView companyLogo;
    private Button submit;
    private TextView question;
    private EditText userInput;
    private String surveyName = "Jewel";
    private Firebase mQuestionRef = new Firebase("https://orbital2019catch.firebaseio.com/feedback/jewel/question");

    DatabaseReference databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback/jewel");

    //  payment
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    String balance;

    public JewelFeedbackActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        companyName = (TextView) findViewById(R.id.feedback_company_name);
        companyLogo = (ImageView) findViewById(R.id.feedback_company_logo);
        companyLogo.setImageResource(R.drawable.jewel);
        submit = (Button) findViewById(R.id.submit_feedback);
        userInput = (EditText) findViewById(R.id.feedback_user_input);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String qnsString = dataSnapshot.getValue(String.class);
                question.setText(qnsString);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        question = (TextView) findViewById(R.id.feedback_question);

        companyName.setText(surveyName);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFeedback();
            }
        });
    }

    private void addFeedback() {
        String inputString = userInput.getText().toString();
        if (TextUtils.isEmpty(inputString)) {
            Toast.makeText(this, "Please enter your feedback!", Toast.LENGTH_LONG).show();
        } else {
            if (inputString.length() < 140) {
                Toast.makeText(this, "Please enter a minimum of 140 characters!", Toast.LENGTH_LONG).show();
            } else {
                String id = databaseFeedback.push().getKey();
                Feedback feedback = new Feedback(inputString);
                databaseFeedback.child(id).setValue(feedback);
                // addCredits(); null obj ref for balance
                Toast.makeText(this, "Feedback successfully submitted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }
    }

    private void addCredits() {
        getBalance();
        DatabaseReference balanceRef = mDatabase.getReference(mAuth.getUid()).child("balance");
        balanceRef.setValue(Double.parseDouble(balance) + 1.0);
    }

    private void getBalance() {
        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                balance = String.format("%.2f", userProfile.getBalance());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(JewelFeedbackActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override // over-riding so that back does not lead to the previously done survey
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
