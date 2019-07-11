package com.example.orbital2019catch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class UserBalanceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_balance);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
