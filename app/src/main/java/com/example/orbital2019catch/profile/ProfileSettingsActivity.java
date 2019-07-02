package com.example.orbital2019catch.profile;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.loginandregister.LoginActivity;
import com.example.orbital2019catch.loginandregister.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileSettingsActivity extends AppCompatActivity {

    private Button mLogoutBtn, mPaymentBtn, mPaymentHistoryBtn;
    private TextView profileName, profileEmail, profileBalance;

    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            // handle the logged in user
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        profileName = findViewById(R.id.username_display);
        profileEmail = findViewById(R.id.email_display);
        profileBalance = findViewById(R.id.balance_display);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                profileName.setText(userProfile.getName());
                profileEmail.setText(userProfile.getEmail());
                profileBalance.setText(String.format("$%.2f", userProfile.getBalance()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ProfileSettingsActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

        mLogoutBtn = (Button) findViewById(R.id.logout_btn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileSettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        mPaymentBtn = (Button) findViewById(R.id.paymentBtn);
        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileSettingsActivity.this, PaymentActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        mPaymentHistoryBtn = (Button) findViewById(R.id.paymentHistoryBtn);
        mPaymentHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ProfileSettingsActivity.this, PaymentHistoryActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
