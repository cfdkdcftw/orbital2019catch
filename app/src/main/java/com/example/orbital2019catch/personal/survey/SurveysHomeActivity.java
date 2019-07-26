package com.example.orbital2019catch.personal.survey;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class SurveysHomeActivity extends AppCompatActivity  {
    private ListView listView;
    private SurveyArrayAdapter surveyArrayAdapter;
    private FirebaseDatabase mDatabase;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Survey> surveys;
    RecyclerView recyclerView;
    EditText searchBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveys_home);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchBar = (EditText)findViewById(R.id.searchbar);

        db.collection("surveys").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                surveys = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    surveys.add(snapshot.toObject(Survey.class));
                }
                SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(surveys);
                recyclerView.setAdapter(surveyAdapterClass);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void search(String str) {
        ArrayList<Survey> list = new ArrayList<>();
        for (Survey s : surveys) {
            if (s.getName().toLowerCase().contains(str.toLowerCase())) {
                list.add(s);
            }
        }
        SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(list);
        recyclerView.setAdapter(surveyAdapterClass);
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

