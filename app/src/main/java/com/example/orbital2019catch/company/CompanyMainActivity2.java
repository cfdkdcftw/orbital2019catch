package com.example.orbital2019catch.company;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.orbital2019catch.R;

public class CompanyMainActivity2 extends AppCompatActivity implements View.OnClickListener {

    TextView createSurveyBtn, viewCurrSurveysBtn, editCurrSurveysBtn,
    createFeedbackBtn, editFeedbackBtn, viewFeedbackBtn,
    goLiveBtn, archivedLiveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_main2);

        createSurveyBtn = (TextView) findViewById(R.id.create_new_survey_btn);
        viewCurrSurveysBtn = (TextView) findViewById(R.id.view_curr_survey_btn);
        editCurrSurveysBtn = (TextView) findViewById(R.id.edit_curr_survey_btn);
        createFeedbackBtn = (TextView) findViewById(R.id.create_feedback_qn_btn);
        editFeedbackBtn = (TextView) findViewById(R.id.edit_feedback_qn_btn);
        viewFeedbackBtn = (TextView) findViewById(R.id.view_feedback_btn);
        goLiveBtn = (TextView) findViewById(R.id.go_live_btn);
        archivedLiveBtn = (TextView) findViewById(R.id.view_archived_broadcasts_btn);



    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.create_new_survey_btn :
//                intent = new Intent(this, ProfileSettingsActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0,0);
                break;
            default:
                break;
        }
    }

}
