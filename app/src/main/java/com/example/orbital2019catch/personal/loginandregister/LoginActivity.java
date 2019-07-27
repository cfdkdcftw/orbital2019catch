package com.example.orbital2019catch.personal.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.company.CompanyMainActivity2;
import com.example.orbital2019catch.personal.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.company.CompanyMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText mEmailField, mPasswordField;
    private Button mLoginBtn, mRegisterBtn;
    private TextView mForgotPassword;
    private ProgressBar mLoginProgress;
    private FirebaseAuth mAuth;
    private String role;
    private DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mLoginProgress = (ProgressBar) findViewById(R.id.loginProgress);

        mEmailField = (EditText) findViewById(R.id.login_email);
        mPasswordField = (EditText) findViewById(R.id.login_password);
        mLoginBtn = (Button) findViewById(R.id.login_btn);
        mRegisterBtn = (Button) findViewById(R.id.login_register_btn);
        mForgotPassword = (TextView) findViewById(R.id.login_forgot_btn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loginUserAccount();
                if (validate()) {
                    loginUserAccount();
                }
            }
        });

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SelectAccountTypeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });    }

    private Boolean validate() {
        Boolean result = false;

        String email, password;
        email = mEmailField.getText().toString();
        password = mPasswordField.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Please enter email and password.", Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Please enter email.", Toast.LENGTH_LONG).show();
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this.getApplicationContext(), "Please enter password.", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }

        return result;
    }

    private void loginUserAccount() {
        String email, password;
        email = mEmailField.getText().toString();
        password = mPasswordField.getText().toString();

        mLoginProgress.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this.getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                            mLoginProgress.setVisibility(View.INVISIBLE);
                            mRef = FirebaseDatabase.getInstance().getReference("users/" + mAuth.getUid()).child("role");
                            mRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue(String.class).equals("company")) {
                                        // is company account
                                        Intent intent = new Intent(getApplicationContext(), CompanyMainActivity2.class);
                                        startActivity(intent);
                                        overridePendingTransition(0,0);
                                    } else {
                                        // is personal account
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        overridePendingTransition(0,0);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            }
                        else {
                            Toast.makeText(LoginActivity.this.getApplicationContext(), "Login failed!", Toast.LENGTH_LONG).show();
                            mLoginProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    @Override // ovrerriding so that back button cannot be clicked
    public void onBackPressed() {
    }

    public void onRadioButtonClicked(View view) {
    }
}
