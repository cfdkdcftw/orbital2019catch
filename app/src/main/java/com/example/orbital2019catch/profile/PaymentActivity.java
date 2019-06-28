package com.example.orbital2019catch.profile;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import static com.example.orbital2019catch.App.CHANNEL_1_ID;

public class PaymentActivity  extends AppCompatActivity {

    private TextView profileBalance;
    private String phoneNumber, amount;
    private double amountInDouble;
    private EditText mPhoneField, mAmountField;
    private Button mPaymentBtn;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private double balance;
    private NotificationManagerCompat notificationManagerCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        notificationManagerCompat = NotificationManagerCompat.from(this);

        profileBalance = (TextView) findViewById(R.id.balance_display);
        DatabaseReference databaseReference = mDatabase.getReference(mAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile = dataSnapshot.getValue(UserProfile.class);
                balance = userProfile.getBalance();
                profileBalance.setText(String.format("$%.2f", balance));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaymentActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        mPhoneField = (EditText) findViewById(R.id.phone_number);
        mAmountField = (EditText) findViewById(R.id.payment_amount);
        mPaymentBtn = (Button) findViewById(R.id.request_payment);

        mPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = mPhoneField.getText().toString().trim();
                amount = mAmountField.getText().toString().trim();
                amountInDouble = Double.parseDouble(amount);
                if (validate()) {
                    sendNotification();
                    // upload payment details to data base
                    sendRequest();
                }
            }
        });
    }

    private void sendRequest() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference("payments");
        PaymentRequest paymentRequest = new PaymentRequest(phoneNumber, amountInDouble);
        myRef.setValue(paymentRequest);

        updateUserBalance();
        Toast.makeText(PaymentActivity.this.getApplicationContext(), "Successfully requested for payment!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }

    private void updateUserBalance() {
        DatabaseReference balanceRef = mDatabase.getReference(mAuth.getUid()).child("balance");
        balanceRef.setValue(balance - amountInDouble);
    }

    private Boolean validate() {
        Boolean result = false;

        if (phoneNumber.isEmpty()) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Please enter phone number.", Toast.LENGTH_LONG).show();
        } else if (amount.isEmpty()) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Please enter amount to be withdrawn.", Toast.LENGTH_LONG).show();
        } else if (phoneNumber.length() != 8) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Phone number must be 8 digits.", Toast.LENGTH_LONG).show();
        } else if (balance < amountInDouble) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "You have insufficient balance.", Toast.LENGTH_LONG).show();
        } else if (amountInDouble <= 0) {
            Toast.makeText(PaymentActivity.this.getApplicationContext(), "Please enter a valid amount.", Toast.LENGTH_LONG).show();
        }
        else {
            result = true;
        }

        return result;
    }

    public void sendNotification() {
        String title = "DBSBank";
        String message = String.format("You have received SGD %.2f from CATCH to your account via PayNow.", amountInDouble);
        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_chat_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                .setColor(Color.BLUE)
                .setBadgeIconType(R.drawable.ic_chat_black_24dp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();
        notificationManagerCompat.notify(m, notification);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
