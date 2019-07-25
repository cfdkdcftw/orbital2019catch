package com.example.orbital2019catch.personal.survey;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.orbital2019catch.personal.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.survey.workinprogress.SurveyArrayAdapter;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class SurveysHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView survey1, survey2, survey3;
    private TextView r1, r2, r3;
    private ProgressBar pb1, pb2, pb3;
    private Firebase survey1Quota, survey2Quota, survey3Quota;
    private ListView listView;
    private SurveyArrayAdapter surveyArrayAdapter;
    private FirebaseDatabase mDatabase;

    private static final String KEY_MAX = "max";
    private static final String KEY_CURR = "curr";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference quotaRef1 = db.collection("surveys").document("spotify");
    private DocumentReference quotaRef2 = db.collection("surveys").document("nike");
    private DocumentReference quotaRef3 = db.collection("surveys").document("uniqlo");

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

        survey1 = (CardView) findViewById(R.id.surveysCard1);
        survey1.setOnClickListener(this);

        survey2 = (CardView) findViewById(R.id.surveysCard2);
        survey2.setOnClickListener(this);

        survey3 = (CardView) findViewById(R.id.surveysCard3);
        survey3.setOnClickListener(this);

        r1 = (TextView) findViewById(R.id.respondents1);
        r2 = (TextView) findViewById(R.id.respondents2);
        r3 = (TextView) findViewById(R.id.respondents3);

        pb1 = (ProgressBar) findViewById(R.id.quota1);
        pb2 = (ProgressBar) findViewById(R.id.quota2);
        pb3 = (ProgressBar) findViewById(R.id.quota3);

        quotaRef1.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            long max = documentSnapshot.getLong(KEY_MAX);
                            long curr = documentSnapshot.getLong(KEY_CURR);
                            pb1.setMax((int)max);
                            pb1.setProgress((int)curr);
                            r1.setText(""+ curr + " responded" );
                        }
                    }
                });

        quotaRef2.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            long max = documentSnapshot.getLong(KEY_MAX);
                            long curr = documentSnapshot.getLong(KEY_CURR);
                            pb2.setMax((int)max);
                            pb2.setProgress((int)curr);
                            r2.setText(""+ curr + " responded" );
                        }
                    }
                });

        quotaRef3.get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            long max = documentSnapshot.getLong(KEY_MAX);
                            long curr = documentSnapshot.getLong(KEY_CURR);
                            pb3.setMax((int)max);
                            pb3.setProgress((int)curr);
                            r3.setText(""+ curr + " responded" );
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.surveysCard1:
                intent = new Intent(this, SurveyLocalSpotify.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.surveysCard2:
                intent = new Intent(this, SurveyFirebaseNike.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
            case R.id.surveysCard3:
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

