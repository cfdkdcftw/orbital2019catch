package com.example.orbital2019catch.company.feedback;

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

public class GoogleViewFeedbackActivity extends AppCompatActivity {

    DatabaseReference mRef;
    RecyclerView recyclerView;
    ArrayList<FeedbackResults> results;
    FeedbackAdapter adapter;
    TextView qns;
    Firebase mQRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_view_feedback);
        getQuestions();
        initRecyclerView();
        mRef = FirebaseDatabase.getInstance().getReference("feedback/google/answers");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren()) {
                    FeedbackResults feedbackResults = ds.getValue(FeedbackResults.class);
                    results.add(feedbackResults);
                }
                adapter = new FeedbackAdapter(GoogleViewFeedbackActivity.this, results);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GoogleViewFeedbackActivity.this, "Something went wrong when reading the survey results", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getQuestions() {
        qns = (TextView) findViewById(R.id.company_feedback_question);

        mQRef = new Firebase("https://orbital2019catch.firebaseio.com/feedback/google/question");

        mQRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String question = "Question: " + dataSnapshot.getValue(String.class);
                qns.setText(question);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        results = new ArrayList<FeedbackResults>();
        recyclerView = (RecyclerView) findViewById(R.id.company_feedback_recycler_view);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }
}

