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

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.livechallenge.BambuserPlayerActivity;
import com.example.orbital2019catch.loginandregister.LoginActivity;
import com.example.orbital2019catch.profile.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CompanyMainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button profileSettingsBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private TextView displayName;
    private String email;
    private CardView liveChallengeCard;

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
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            sendToLogin();
        } else {
            this.email = currentUser.getEmail();
            setContentView(R.layout.activity_company_main);
            displayName = findViewById(R.id.user_display_name);
            mDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                    displayName.setText(userProfile.getName() + "!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(CompanyMainActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
                }
            });
            liveChallengeCard = (CardView) findViewById(R.id.liveChallengeCard);
            liveChallengeCard.setOnClickListener(this);
            profileSettingsBtn = (Button) findViewById(R.id.profile_settings_btn);
            profileSettingsBtn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
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
            default:
                break;
        }
    }

    @Override // ovrerriding so that back button cannot be clicked
    public void onBackPressed() {
    }
}
