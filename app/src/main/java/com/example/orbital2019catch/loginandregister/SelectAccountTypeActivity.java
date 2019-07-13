package com.example.orbital2019catch.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.deprecated.UserBalanceActivity;
import com.example.orbital2019catch.profile.ProfileSettingsActivity;

public class SelectAccountTypeActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout personalLayout;
    LinearLayout businessLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);
        LinearLayout personalLayout = (LinearLayout) findViewById(R.id.select_personal_layout);
        LinearLayout businessLayout = (LinearLayout) findViewById(R.id.select_business_layout);
        personalLayout.setOnClickListener(this);
        businessLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.select_personal_layout:
                intent = new Intent(this, RegisterPersonalActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.select_business_layout:
                intent = new Intent(this, RegisterBusinessActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }

    @Override // ovrerriding so that back button cannot be clicked
    public void onBackPressed() {
    }
}
