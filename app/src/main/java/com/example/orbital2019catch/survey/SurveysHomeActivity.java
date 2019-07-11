package com.example.orbital2019catch.survey;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.orbital2019catch.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.survey.workinprogress.SurveyArrayAdapter;


public class SurveysHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button survey1, survey2, survey3;
    private ListView listView;
    private SurveyArrayAdapter surveyArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys_home);
        /*
        listView = (ListView) findViewById(R.id.survey_list_view);
        surveyArrayAdapter = new SurveyArrayAdapter(getApplicationContext(), R.layout.single_survey_list_view);
        CSVReader csvReader = new CSVReader();
        InputStream inputStreamSurveys = getResources().openRawResource(R.raw.firebase_survey);
        List<String[]> surveyData = csvReader.read(inputStreamSurveys);
        List<Survey> surveys = new ArrayList<>();
        for (int i = 0; i < surveyData.size(); i++){
            String[] surveyInfo = surveyData.get(i);
            Survey currSurvey = new FirebaseSurvey(surveyInfo[0], surveyInfo[1],
                    surveyInfo[2], Double.parseDouble(surveyInfo[3]), surveyInfo[4]);
            surveys.add(currSurvey);
            surveyArrayAdapter.add(currSurvey);
        }

        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(surveyArrayAdapter);
        listView.onRestoreInstanceState(state);
        // list the card views in sequence then set the survey before starting the intent

        */
        // survey1
        survey1 = (Button) findViewById(R.id.start_feedback_btn1);
        survey1.setOnClickListener(this);

        // survey2
        survey2 = (Button) findViewById(R.id.survey_btn2);
        survey2.setOnClickListener(this);

        survey2 = (Button) findViewById(R.id.survey_btn3);
        survey2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.start_feedback_btn1:
                intent = new Intent(this, SurveyLocalSpotify.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.survey_btn2:
                intent = new Intent(this, SurveyFirebaseNike.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.survey_btn3:
                intent = new Intent(this, SurveyFirebaseUniqlo.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            default:
                break;
        }
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    @Override // over-riding so that back does not lead to the previously done survey
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}

