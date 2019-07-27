package com.example.orbital2019catch.company;

import android.content.Intent;
import android.os.Bundle;

import com.example.orbital2019catch.company.feedback.CraftFeedbackActivity;
import com.example.orbital2019catch.company.feedback.GoogleViewFeedbackActivity;
import com.example.orbital2019catch.company.survey.CraftSurveyActivity;
import com.example.orbital2019catch.company.survey.PumaViewSurveyActivity;
import com.example.orbital2019catch.personal.loginandregister.LoginActivity;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.firebase.client.Firebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyMainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView createSurveyBtn, viewCurrSurveysBtn, editCurrSurveysBtn,
            createFeedbackBtn, editFeedbackBtn, viewFeedbackBtn,
            goLiveBtn, archivedLiveBtn;
    private TextView mDisplayName;
    FirebaseAuth mAuth;
    private String email, displayName, companyName;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(CompanyMainActivity2.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {
            setContentView(R.layout.activity_company_main2);
            mDisplayName = (TextView) findViewById(R.id.user_display_name);
            this.email = currentUser.getEmail();
            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = mDatabase.getReference("users/" + mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    companyName = userProfile.getCompanyName();
                    mDisplayName.setText(userProfile.getName());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CompanyMainActivity2.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
            createSurveyBtn = (TextView) findViewById(R.id.create_new_survey_btn);
            createSurveyBtn.setOnClickListener(this);
            viewCurrSurveysBtn = (TextView) findViewById(R.id.view_curr_survey_btn);
            viewCurrSurveysBtn.setOnClickListener(this);
            editCurrSurveysBtn = (TextView) findViewById(R.id.edit_curr_survey_btn);
            editCurrSurveysBtn.setOnClickListener(this);
            createFeedbackBtn = (TextView) findViewById(R.id.create_feedback_qn_btn);
            createFeedbackBtn.setOnClickListener(this);
            editFeedbackBtn = (TextView) findViewById(R.id.edit_feedback_qn_btn);
            editFeedbackBtn.setOnClickListener(this);
            viewFeedbackBtn = (TextView) findViewById(R.id.view_feedback_btn);
            viewFeedbackBtn.setOnClickListener(this);
            goLiveBtn = (TextView) findViewById(R.id.go_live_btn);
            goLiveBtn.setOnClickListener(this);
            archivedLiveBtn = (TextView) findViewById(R.id.view_archived_broadcasts_btn);
            archivedLiveBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick (View v){
        Intent intent;
        switch (v.getId()) {
            case R.id.view_curr_survey_btn:
                // if company == spotify -> new spotify intent
                if (companyName.equalsIgnoreCase("Puma")) {
                    intent = new Intent(this, PumaViewSurveyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
                } else {
                    Toast.makeText(CompanyMainActivity2.this, "Your company has no surveys available to view!", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.view_feedback_btn:
                if (companyName.equalsIgnoreCase("google")) {
                    intent = new Intent(this, GoogleViewFeedbackActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
                } else {
                    Toast.makeText(CompanyMainActivity2.this, "Your company has no feedback available to view!", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.go_live_btn:
                intent = new Intent(this, WowzaBroadcastActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.create_new_survey_btn:
                intent = new Intent(this, CraftSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.create_feedback_qn_btn:
                intent = new Intent(this, CraftFeedbackActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }
}
