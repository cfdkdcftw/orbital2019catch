package com.example.orbital2019catch.loginandregister;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends Activity {

    EditText mEmail;
    Button mChangePwBtn;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        mEmail = (EditText) findViewById(R.id.forgot_email);
        mChangePwBtn = (Button) findViewById(R.id.forgot_change_password);

        mAuth = FirebaseAuth.getInstance();

        mChangePwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this,"Please enter your email!", Toast.LENGTH_SHORT).show();
                } else
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Please check your email to reset your password!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ForgotPasswordActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


            }
        });
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
