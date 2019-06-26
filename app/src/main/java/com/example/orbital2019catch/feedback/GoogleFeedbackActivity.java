package com.example.orbital2019catch.feedback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.survey.SurveysHomeActivity;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleFeedbackActivity extends Activity{

    private TextView companyName;
    private ImageView companyLogo;
    private Button submit;
    private TextView question;
    private EditText userInput;
    private String surveyName = "Google";
    private Firebase mQuestionRef = new Firebase("https://orbital2019catch.firebaseio.com/feedback/google/question");

    DatabaseReference databaseFeedback = FirebaseDatabase.getInstance().getReference("feedback/google");

    public GoogleFeedbackActivity() {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        companyName = (TextView) findViewById(R.id.feedback_company_name);
        companyLogo = (ImageView) findViewById(R.id.feedback_company_logo);
        companyLogo.setImageResource(R.drawable.googleg_standard_color_18);
        submit = (Button) findViewById(R.id.submit_feedback);
        userInput = (EditText) findViewById(R.id.feedback_user_input);
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
        String surveyName = companyName.getText().toString().trim();
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
                Toast.makeText(this, "Feedback successfully submitted!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, FeedbackHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        }
    }

    @Override // over-riding so that back does not lead to the previously done survey
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
