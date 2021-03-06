package com.example.orbital2019catch.personal;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.company.CompanyMainActivity;
import com.example.orbital2019catch.company.CompanyMainActivity2;
import com.example.orbital2019catch.deprecated.UserBalanceActivity;
import com.example.orbital2019catch.personal.feedback.FeedbackHomeActivity;
import com.example.orbital2019catch.personal.livechallenge.LiveChallengeActivity;
import com.example.orbital2019catch.personal.livechallenge.WowzaPlayerActivity;
import com.example.orbital2019catch.personal.location.LocationBasedActivity;
import com.example.orbital2019catch.personal.loginandregister.LoginActivity;
import com.example.orbital2019catch.personal.profile.PaymentActivity;
import com.example.orbital2019catch.personal.profile.PaymentRequest;
import com.example.orbital2019catch.personal.profile.UserProfile;
import com.example.orbital2019catch.personal.profile.ProfileSettingsActivity;
import com.example.orbital2019catch.personal.qrcode.QrCodeActivity;
import com.example.orbital2019catch.personal.survey.SurveysHomeActivity;
import com.example.orbital2019catch.personal.survey.ViewPagerAdapter;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button profileSettingsBtn;
    private RelativeLayout walletBalance;
    private CardView surveysCard, feedbackCard, liveChallengeCard, locationBasedActivitiesCard,
            qrCodeScannerCard;
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private TextView displayName, displayBalance;
    String email;
    ViewPager viewPager;

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
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {
            mDatabase = FirebaseDatabase.getInstance();
            mRef = mDatabase.getReference("users/" + mAuth.getUid()).child("role");
            mRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue(String.class).equals("company")) {
                        // is company account
                        Intent intent = new Intent(getApplicationContext(), CompanyMainActivity2.class);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        this.email = currentUser.getEmail();
        setContentView(R.layout.activity_main);
        // display welcome message to user
        displayName = findViewById(R.id.user_display_name);
        displayBalance = findViewById(R.id.user_display_balance);
        DatabaseReference databaseReference = mDatabase.getReference("users/" + mAuth.getUid());
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

        // defining cards
        surveysCard = (CardView) findViewById(R.id.surveysCard);
        feedbackCard = (CardView) findViewById(R.id.feedbackCard);
        liveChallengeCard = (CardView) findViewById(R.id.liveChallengeCard);
        locationBasedActivitiesCard = (CardView) findViewById(R.id.locationBasedActivitiesCard);
        qrCodeScannerCard = (CardView) findViewById(R.id.qrCodeScannerCard);

        // defining buttons
        profileSettingsBtn = (Button) findViewById(R.id.profile_settings_btn);
        walletBalance = (RelativeLayout) findViewById(R.id.user_balance_btn);

        // add onClickListener to buttons
        profileSettingsBtn.setOnClickListener(this);
        walletBalance.setOnClickListener(this);

        // add onClickListener to cards
        surveysCard.setOnClickListener(this);
        feedbackCard.setOnClickListener(this);
        liveChallengeCard.setOnClickListener(this);
        locationBasedActivitiesCard.setOnClickListener(this);
        qrCodeScannerCard.setOnClickListener(this);

        // for news flash recycler view
        getImages();

        // for image slider
        viewPager = (ViewPager)findViewById(R.id.imageSliderMainActivity);
        ViewPagerAdapterMainActivity viewPagerAdapterMainActivity = new ViewPagerAdapterMainActivity(this);
        viewPager.setAdapter(viewPagerAdapterMainActivity);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MainActivity.MyTimerTask(), 2000, 4000);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
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
                intent = new Intent(this, PaymentActivity.class);
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
        mImageUrls.add("https://i.ibb.co/Nt0GY3J/3.png");
        mImageUrls.add("https://i.ibb.co/3YKNxm4/1.png");
        mImageUrls.add("https://i.ibb.co/syj3P93/4.png");
        mImageUrls.add("https://i.ibb.co/pnQWRRK/2.png");
        mImageUrls.add("https://i.ibb.co/b2VB1Bd/5.png");
        mImageUrls.add("https://i.ibb.co/c6b2v7B/6.png");
        mImageUrls.add("https://i.ibb.co/wMVrhxY/7.png");

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
