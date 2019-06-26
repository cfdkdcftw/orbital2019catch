package com.example.orbital2019catch.profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.loginandregister.RegisterActivity;
import com.example.orbital2019catch.loginandregister.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity  extends AppCompatActivity {

    private String phoneNumber, amount;
    private EditText mPhoneField, mAmountField;
    private Button mPaymentBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        getBalance();
        mPhoneField = (EditText) findViewById(R.id.phone_number);
        mAmountField = (EditText) findViewById(R.id.payment_amount);
        mPaymentBtn = (Button) findViewById(R.id.request_payment);

        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mPhoneField.getText().toString().trim();
                amount = mAmountField.getText().toString().trim();
                if (validate()) {
                    // upload payment details to data base
                    sendRequest();
                }
            }
        });
    }

    private void sendRequest() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("payments");
        UserProfile userProfile = new UserProfile(phoneNumber, amount);
        myRef.setValue(userProfile);
        updateUserBalance();
        Toast.makeText(PaymentActivity.this.getApplicationContext(), "Successfully requested for payment!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private void updateUserBalance() {
        DatabaseReference balanceRef = mDatabase.getReference(mAuth.getUid()).child("balance");
        balanceRef.setValue(Double.parseDouble(balance) - Double.parseDouble(amount));
    }

    private void getBalance() {
        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                balance = String.format("%.2f", userProfile.getBalance());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private Boolean validate() {
        Boolean result = false;

        if (phoneNumber.isEmpty()) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Please enter phone number.", Toast.LENGTH_LONG).show();
        } else if (amount.isEmpty()) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Please enter amount to be withdrawn.", Toast.LENGTH_LONG).show();
        } else if (phoneNumber.length() != 8) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Phone number must be 8 digits.", Toast.LENGTH_LONG).show();
        } // else if (Double.parseDouble(amount) > Double.parseDouble(balance)) {
          //  Toast.makeText(PaymentActivity.this.getApplicationContext(), "You have insufficient balance.", Toast.LENGTH_LONG).show();
          // }
        else {
            result = true;
        }

        return result;
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
