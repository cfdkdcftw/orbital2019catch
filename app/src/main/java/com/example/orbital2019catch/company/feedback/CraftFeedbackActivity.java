package com.example.orbital2019catch.company.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.company.CompanyMainActivity;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.type.Date;

import java.time.LocalTime;
import java.util.Calendar;
import java.util.TimeZone;

public class CraftFeedbackActivity extends AppCompatActivity {

    private EditText mQnsField, mPayoutField;
    private RadioGroup mFeedbackType;
    private RadioButton mRadioNormal, mRadioMaps, mRadioQr;
    private Button mSubmitFeedbackForm;
    private ProgressBar mSubmitProgress;
    private String qns, payout, type, company, userName, userEmail;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private boolean checked;

    // need company, user email, payout, question, location marker? or qr code?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_craft_feedback);
        mAuth = FirebaseAuth.getInstance();
        mQnsField = (EditText) findViewById(R.id.craft_feedback_question);
        mPayoutField = (EditText) findViewById(R.id.craft_feedback_payout);
        mFeedbackType = (RadioGroup) findViewById(R.id.craft_feedback_type);
        mRadioNormal = (RadioButton) findViewById(R.id.craft_feedback_radio_normal);
        mRadioMaps = (RadioButton) findViewById(R.id.craft_feedback_radio_maps);
        mRadioQr = (RadioButton) findViewById(R.id.craft_feedback_radio_qr_code);
        mSubmitFeedbackForm = (Button) findViewById(R.id.craft_feedback_button);
        mSubmitProgress = (ProgressBar) findViewById(R.id.craft_feedback_progress);

        mSubmitFeedbackForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qns = mQnsField.getText().toString().trim();
                payout = mPayoutField.getText().toString().trim();
                if (validate()) {
                    sendFeedbackRequest();
                }
            }
        });
    }

    private void sendFeedbackRequest() {
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
        mRef = FirebaseDatabase.getInstance().getReference("craft/feedback/" + company + "/" + Calendar.getInstance(TimeZone.getTimeZone("GMT+8")));
        FeedbackRequest feedbackRequest = new FeedbackRequest(company, userEmail, userName, qns, payout, type);
        mRef.setValue(feedbackRequest);
        Toast.makeText(CraftFeedbackActivity.this.getApplicationContext(), "You have submitted a new Feedback Form request!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, CompanyMainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private boolean validate() {
        boolean result = false;

        if (qns.isEmpty()) {
            Toast.makeText(CraftFeedbackActivity.this.getApplicationContext(), "Please enter your question!", Toast.LENGTH_LONG).show();
        } else if (payout.isEmpty()) {
            Toast.makeText(CraftFeedbackActivity.this.getApplicationContext(), "Please enter the payout amount!", Toast.LENGTH_LONG).show();
        } else if (!checked) {
            Toast.makeText(CraftFeedbackActivity.this.getApplicationContext(), "Please select a survey type!", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }
        return result;
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    public void onRadioButtonClicked(View view) {
        checked = true;
        switch (view.getId()) {
            case R.id.craft_feedback_radio_maps:
                type = "maps";
                break;
            case R.id.craft_feedback_radio_normal:
                type = "normal";
                break;
            case R.id.craft_feedback_radio_qr_code:
                type = "qr";
                break;
        }
    }
}
