package com.example.orbital2019catch.loginandregister;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.company.CompanyMainActivity;
import com.example.orbital2019catch.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private String name, email, password, role;
    private EditText mNameField, mEmailField, mPasswordField;
    private Button mRegisterBtn;
    private ProgressBar mRegisterProgress;
    private FirebaseAuth mAuth;
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mRegisterProgress = (ProgressBar) findViewById(R.id.registerProgress);

        mNameField = (EditText) findViewById(R.id.register_name);
        mEmailField = (EditText) findViewById(R.id.register_email);
        mPasswordField = (EditText) findViewById(R.id.register_password);
        mRegisterBtn = (Button) findViewById(R.id.register_btn);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*registerNewUser();*/
                name = mNameField.getText().toString().trim();
                email = mEmailField.getText().toString().trim();
                password = mPasswordField.getText().toString().trim();

                if (validate()) {
                    // upload user data to data base
                    registerUserAccount();
                }
            }
        });

    }

    private Boolean validate() {
        Boolean result = false;

        if (name.isEmpty()) {
            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please enter name.", Toast.LENGTH_LONG).show();
        } else if (email.isEmpty()) {
            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please enter email.", Toast.LENGTH_LONG).show();
        } else if (password.isEmpty()) {
            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please enter password.", Toast.LENGTH_LONG).show();
        } else if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Password must have at least 6 characters.", Toast.LENGTH_LONG).show();
        } else if (!checked){
            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Please select your account type.", Toast.LENGTH_LONG).show();
        } else {
            result = true;
        }
        return result;
    }

    private void registerUserAccount() {
        mRegisterProgress.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // send user data to database
                            sendUserData();

                            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                            mRegisterProgress.setVisibility(View.INVISIBLE);

                            if (role.equals("user")) {
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                Intent intent = new Intent(RegisterActivity.this, CompanyMainActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0,0);
                            }
                        }
                        else {
                            Toast.makeText(RegisterActivity.this.getApplicationContext(), "Registration failed!" + task.getException().toString(), Toast.LENGTH_LONG).show();
                            mRegisterProgress.setVisibility(View.INVISIBLE);
                        }
                    }
                });
    }

    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference myRef = firebaseDatabase.getReference(mAuth.getUid());

        UserProfile userProfile = new UserProfile(name, email, role);
        myRef.setValue(userProfile);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_company:
                if (checked)
                    role = "company";
                    break;
            case R.id.radio_user:
                if (checked)
                    role = "user";
                    break;
        }
    }

}
