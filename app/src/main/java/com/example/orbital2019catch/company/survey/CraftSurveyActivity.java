package com.example.orbital2019catch.company.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.company.CompanyMainActivity;
import com.example.orbital2019catch.company.survey.CraftSurveyActivity;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.TimeZone;

public class CraftSurveyActivity extends AppCompatActivity {

    private RadioButton mRadioNormal, mRadioMaps, mRadioQr;
    private Button mSubmitSurveyForm;
    private boolean isEmptyField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft_survey);
        /*
        mAuth = FirebaseAuth.getInstance();
        mQnsField = (EditText) findViewById(R.id.craft_survey_question);
        mPayoutField = (EditText) findViewById(R.id.craft_survey_payout);
        mSurveyType = (RadioGroup) findViewById(R.id.craft_survey_type);
        mRadioNormal = (RadioButton) findViewById(R.id.craft_survey_radio_normal);
        mRadioMaps = (RadioButton) findViewById(R.id.craft_survey_radio_maps);
        mRadioQr = (RadioButton) findViewById(R.id.craft_survey_radio_qr_code);
        mSubmitSurveyForm = (Button) findViewById(R.id.craft_survey_button);
        mSubmitProgress = (ProgressBar) findViewById(R.id.craft_survey_progress);

        mSubmitSurveyForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qns = mQnsField.getText().toString().trim();
                payout = mPayoutField.getText().toString().trim();
                if (validate()) {
                    sendSurveyRequest();
                }
            }
        });
        */
    }

    /*
    private void sendSurveyRequest() {
        mSubmitProgress.setVisibility(View.VISIBLE);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getUid());
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                company = userProfile.getCompanyName();
                userName = userProfile.getName();
                userEmail = userProfile.getEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mRef = FirebaseDatabase.getInstance().getReference("craft/survey/" + company + "/" + Calendar.getInstance(TimeZone.getTimeZone("GMT+8")));
        SurveyRequest surveyRequest = new SurveyRequest(company, userEmail, userName, qns, payout, type);
        mRef.setValue(surveyRequest);
        Toast.makeText(CraftSurveyActivity.this.getApplicationContext(), "You have submitted a new Survey Form request!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CompanyMainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    */

    private boolean validate() {
        boolean result = false;

        return result;
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    public void onRadioButtonClicked(View view) {
        /*
        checked = true;
        switch (view.getId()) {
            case R.id.craft_survey_radio_maps:
                type = "maps";
                break;
            case R.id.craft_survey_radio_normal:
                type = "normal";
                break;
            case R.id.craft_survey_radio_qr_code:
                type = "qr";
                break;
        }
        */
    }
}