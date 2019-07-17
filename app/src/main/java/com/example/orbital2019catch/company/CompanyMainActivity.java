package com.example.orbital2019catch.company;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orbital2019catch.company.feedback.CraftFeedbackActivity;
import com.example.orbital2019catch.company.feedback.GoogleViewFeedbackActivity;
import com.example.orbital2019catch.company.survey.CraftSurveyActivity;
import com.example.orbital2019catch.company.survey.PumaViewSurveyActivity;
import com.example.orbital2019catch.personal.NewsFlashRecyclerViewAdapter;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.loginandregister.LoginActivity;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyMainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button profileSettingsBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private TextView displayName;
    private String email;
    private CardView surveysCard, feedbackCard, liveChallengeCard, craftSurveysCard, craftFeedbackCard;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private String companyName;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(CompanyMainActivity.this, LoginActivity.class);
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
            this.email = currentUser.getEmail();
            setContentView(R.layout.activity_company_main);
            displayName = findViewById(R.id.user_display_name);
            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = mDatabase.getReference("users/" + mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    displayName.setText(userProfile.getName() + "!");
                    companyName = userProfile.getCompanyName();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CompanyMainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
            surveysCard = (CardView) findViewById(R.id.surveysCard);
            surveysCard.setOnClickListener(this);
            feedbackCard = (CardView) findViewById(R.id.feedbackCard);
            feedbackCard.setOnClickListener(this);
            liveChallengeCard = (CardView) findViewById(R.id.liveChallengeCard);
            liveChallengeCard.setOnClickListener(this);
            profileSettingsBtn = (Button) findViewById(R.id.profile_settings_btn);
            profileSettingsBtn.setOnClickListener(this);
            craftFeedbackCard = (CardView) findViewById(R.id.craftFeedbackCard);
            craftFeedbackCard.setOnClickListener(this);
            craftSurveysCard = (CardView) findViewById(R.id.craftSurveysCard);
            craftSurveysCard.setOnClickListener(this);
            getImages();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.surveysCard :
                // if company == spotify -> new spotify intent
                if (companyName.equalsIgnoreCase("Puma")) {
                    intent = new Intent(this, PumaViewSurveyActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
                } else {
                    Toast.makeText(CompanyMainActivity.this, "Your company has no surveys available to view!", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.feedbackCard :
                if (companyName.equalsIgnoreCase("google")) {
                    intent = new Intent(this, GoogleViewFeedbackActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                    break;
                } else {
                    Toast.makeText(CompanyMainActivity.this, "Your company has no feedback available to view!", Toast.LENGTH_LONG).show();
                    break;
                }
            case R.id.liveChallengeCard :
                intent = new Intent(this, WowzaBroadcastActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.profile_settings_btn :
                intent = new Intent(this, CompanyProfileSettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.craftSurveysCard :
                intent = new Intent(this, CraftSurveyActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.craftFeedbackCard :
                intent = new Intent(this, CraftFeedbackActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            default:
                break;
        }
    }

    private void getImages(){
        mImageUrls.add("https://i.ibb.co/XJtBfsj/NEW-AGE-LOGO.png");
        mImageUrls.add("https://i.ibb.co/VY8cVsK/Poster.png");
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.newsFlashRecylerView);
        recyclerView.setLayoutManager(layoutManager);
        NewsFlashRecyclerViewAdapter recyclerViewAdapter = new NewsFlashRecyclerViewAdapter(this, mImageUrls);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override // overriding so that back button cannot be clicked
    public void onBackPressed() {
    }
}
