package com.example.orbital2019catch.personal.loginandregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.orbital2019catch.R;

public class SelectAccountTypeActivity extends AppCompatActivity implements View.OnClickListener{

    Button personalLayout;
    Button businessLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account_type);
        Button personalLayout = (Button) findViewById(R.id.personal_account_btn);
        Button businessLayout = (Button) findViewById(R.id.company_account_btn);
        personalLayout.setOnClickListener(this);
        businessLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.personal_account_btn:
                intent = new Intent(this, RegisterPersonalActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.company_account_btn:
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
