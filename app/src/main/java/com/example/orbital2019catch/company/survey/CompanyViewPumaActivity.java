package com.example.orbital2019catch.company.survey;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.orbital2019catch.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CompanyViewPumaActivity extends AppCompatActivity {

    DatabaseReference mRef;
    RecyclerView recyclerView;
    ArrayList<SurveyResults> results;
    SurveyAdapter adapter;
    TextView q1, q2, q3, q4, q5;
    Firebase mQ1Ref, mQ2Ref, mQ3Ref, mQ4Ref, mQ5Ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_survey);
        getQuestions();

        recyclerView = (RecyclerView) findViewById(R.id.company_survey_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        results = new ArrayList<SurveyResults>();
        mRef = FirebaseDatabase.getInstance().getReference("surveys/puma/answers");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    SurveyResults surveyResults = ds.getValue(SurveyResults.class);
                    results.add(surveyResults);
                }
                adapter = new SurveyAdapter(CompanyViewPumaActivity.this, results);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(CompanyViewPumaActivity.this, "Something went wrong when reading the survey results", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getQuestions() {
        q1 = (TextView) findViewById(R.id.company_survey_q1);
        q2 = (TextView) findViewById(R.id.company_survey_q2);
        q3 = (TextView) findViewById(R.id.company_survey_q3);
        q4 = (TextView) findViewById(R.id.company_survey_q4);
        q5 = (TextView) findViewById(R.id.company_survey_q5);

        mQ1Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/puma/questions/0/question");
        mQ2Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/puma/questions/1/question");
        mQ3Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/puma/questions/2/question");
        mQ4Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/puma/questions/3/question");
        mQ5Ref = new Firebase("https://orbital2019catch.firebaseio.com/surveys/puma/questions/4/question");

        mQ1Ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Q1: " + dataSnapshot.getValue(String.class);
                q1.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mQ2Ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Q2: " + dataSnapshot.getValue(String.class);
                q2.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mQ3Ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Q3: " + dataSnapshot.getValue(String.class);
                q3.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mQ4Ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Q4: " + dataSnapshot.getValue(String.class);
                q4.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mQ5Ref.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Q5: " + dataSnapshot.getValue(String.class);
                q5.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
