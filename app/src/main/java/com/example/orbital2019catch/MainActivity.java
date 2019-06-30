package com.example.orbital2019catch;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.feedback.FeedbackHomeActivity;
import com.example.orbital2019catch.livechallenge.LiveChallengeActivity;
import com.example.orbital2019catch.location.LocationBasedActivity;
import com.example.orbital2019catch.loginandregister.LoginActivity;
import com.example.orbital2019catch.loginandregister.UserProfile;
import com.example.orbital2019catch.profile.ProfileSettingsActivity;
import com.example.orbital2019catch.qrcode.QrCodeActivity;
import com.example.orbital2019catch.survey.SurveysHomeActivity;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button profileSettingsBtn, userBalanceButton;
    private CardView surveysCard, feedbackCard, liveChallengeCard, locationBasedActivitiesCard,
            qrCodeScannerCard;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private TextView displayName, displayBalance;
    String email;

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {
            this.email = currentUser.getEmail();
        }
        setContentView(R.layout.activity_main);

        // display welcome message to user
        displayName = findViewById(R.id.user_display_name);
        displayBalance = findViewById(R.id.user_display_balance);
        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                displayName.setText(userProfile.getName() + "!");
                displayBalance.setText(String.format("$%.2f", userProfile.getBalance()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        // to connect to firebase for surveys
        Firebase.setAndroidContext(this);

        // defining buttons
        profileSettingsBtn = (Button) findViewById(R.id.profile_settings_btn);

        // add onClickListener to buttons
        profileSettingsBtn.setOnClickListener(this);

        // defining cards
        surveysCard = (CardView) findViewById(R.id.surveysCard);
        feedbackCard = (CardView) findViewById(R.id.feedbackCard);
        liveChallengeCard = (CardView) findViewById(R.id.liveChallengeCard);
        locationBasedActivitiesCard = (CardView) findViewById(R.id.locationBasedActivitiesCard);
        qrCodeScannerCard = (CardView) findViewById(R.id.qrCodeScannerCard);

        // add onClickListener to cards
        surveysCard.setOnClickListener(this);
        feedbackCard.setOnClickListener(this);
        liveChallengeCard.setOnClickListener(this);
        locationBasedActivitiesCard.setOnClickListener(this);
        qrCodeScannerCard.setOnClickListener(this);

        // for news flash recycler view
        getImages();

    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.profile_settings_btn :
                intent = new Intent(this, ProfileSettingsActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.user_balance_btn :
                intent = new Intent(this, UserBalanceActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.surveysCard :
                intent = new Intent(this, SurveysHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.feedbackCard :
                intent = new Intent(this, FeedbackHomeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.liveChallengeCard :
                intent = new Intent(this, LiveChallengeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.locationBasedActivitiesCard :
                intent = new Intent(this, LocationBasedActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            case R.id.qrCodeScannerCard :
                intent = new Intent(this, QrCodeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                break;
            default:
                break;
        }
    }

    private void getImages(){
        mImageUrls.add("https://i.ibb.co/HH8wZcW/Add-a-heading.png");
        mImageUrls.add("https://i.ibb.co/kg0g281/Ma-La-Xiang-Guo.png");
        mImageUrls.add("https://i.ibb.co/x7PNHWP/Ma-La-Xiang-Guo-1.png");

        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.newsFlashRecylerView);
        recyclerView.setLayoutManager(layoutManager);
        NewsFlashRecyclerViewAdapter recyclerViewAdapter = new NewsFlashRecyclerViewAdapter(this, mImageUrls);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override // ovrerriding so that back button cannot be clicked
    public void onBackPressed() {
    }
}
