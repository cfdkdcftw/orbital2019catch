package com.example.orbital2019catch.survey.workinprogress;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.survey.SurveysHomeActivity;

import java.net.URL;
import java.util.ArrayList;

public class SurveyActivity extends AppCompatActivity {

    private Survey mSurvey;
    // if we want to track question number
    // private TextView mQuestionNumberView;
    private ImageView mCompanyLogoView;
    private TextView mCompanyNameView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;

    public void setmSurvey(Survey mSurvey) {
        this.mSurvey = mSurvey;
    }

    private Button mButtonChoice4;

    private ArrayList<String> answers = new ArrayList<>();
    private int mQuestionNumber = 0;

    protected void onCreate(Bundle savedInstanceState, Survey survey) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys);
        mSurvey = survey;
        mCompanyLogoView = (ImageView)findViewById(R.id.survey_company_logo);
        try {
            URL url = new URL(mSurvey.getLogoURL());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            mCompanyLogoView.setImageBitmap(bmp);
        } catch (Exception e) {
            Log.d("Logo URL don't work", e.getMessage());
        } finally {
            mCompanyLogoView.setImageResource(R.drawable.survey);
        }
        mCompanyNameView = (TextView)findViewById(R.id.survey_company_name);
        mCompanyNameView.setText(survey.getCompanyName());
        // mQuestionNumberView = (TextView)findViewById(R.id.question_number);
        // mQuestionNumberView.setText(Integer.toString(mQuestionNumber));
        mQuestionView = (TextView)findViewById(R.id.question);
        mButtonChoice1 = (Button)findViewById(R.id.choice1);
        mButtonChoice2 = (Button)findViewById(R.id.choice2);
        mButtonChoice3 = (Button)findViewById(R.id.choice3);
        mButtonChoice4 = (Button)findViewById(R.id.choice4);
        updateQuestion();

        // Button Listener for Button 1
        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice1(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 2
        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice2(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 3
        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice3(mQuestionNumber -1));
                updateQuestion();
            }
        });

        // Button Listener for Button 4
        mButtonChoice4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.add(mSurvey.getChoice4(mQuestionNumber-1));
                updateQuestion();
            }
        });
    }

    private void updateQuestion() {
        if (mQuestionNumber == 5) {
            Toast.makeText(SurveyActivity.this, "Thank you for completing the survey!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SurveysHomeActivity.class);
            startActivity(intent);
            overridePendingTransition(0,0);
        } else {
            // if we want to track question number
            // mQuestionNumberView.setText(Integer.toString(mQuestionNumber));
            mQuestionView.setText(mSurvey.getQuestion(mQuestionNumber));
            mButtonChoice1.setText(mSurvey.getChoice1(mQuestionNumber));
            mButtonChoice2.setText(mSurvey.getChoice2(mQuestionNumber));
            mButtonChoice3.setText(mSurvey.getChoice3(mQuestionNumber));
            mButtonChoice4.setText(mSurvey.getChoice4(mQuestionNumber));
            mQuestionNumber++;
        }
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
