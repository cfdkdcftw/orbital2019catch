package com.example.orbital2019catch.personal.feedback;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.orbital2019catch.personal.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.survey.Survey;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.annotation.Nullable;

public class FeedbackHomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button brandFilter, categoryFilter;
    public static int BRAND_FILTER_STATE = 0, CATEGORY_FILTER_STATE = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<BrandFeedback> originalFeedbacks;
    private ArrayList<BrandFeedback> currFeedbacks;
    RecyclerView recyclerView;
    EditText searchBar;

//    CardView feedback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_home);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        searchBar = (EditText)findViewById(R.id.searchbar);

        db.collection("feedback").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                originalFeedbacks = new ArrayList<>();
                currFeedbacks = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    originalFeedbacks.add(snapshot.toObject(BrandFeedback.class));
                    currFeedbacks.add(snapshot.toObject(BrandFeedback.class));
                }
                FeedbackAdapterClass feedbackAdapterClass = new FeedbackAdapterClass(currFeedbacks);
                recyclerView.setAdapter(feedbackAdapterClass);
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

        brandFilter = (Button) findViewById(R.id.brandNameFilterBtn);
        categoryFilter = (Button) findViewById(R.id.categoryFilterBtn);

        brandFilter.setOnClickListener(this);
        categoryFilter.setOnClickListener(this);

//        feedback1 = (CardView) findViewById(R.id.feedbackCard1);
//        feedback1.setOnClickListener(this);
    }

    private void search(String str) {
        ArrayList<BrandFeedback> list = new ArrayList<>();
        for (BrandFeedback f : originalFeedbacks) {
            if (f.getBrand().toLowerCase().contains(str.toLowerCase())) {
                list.add(f);
            }
        }
        currFeedbacks = cloneArray(list);
        FeedbackAdapterClass feedbackAdapterClass = new FeedbackAdapterClass(list);
        recyclerView.setAdapter(feedbackAdapterClass);
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
//        Intent intent;
//
//        switch (v.getId()) {
//            case R.id.feedbackCard1:
//                intent = new Intent(this, GoogleFeedbackActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
//                break;
//            default:
//                break;
//        }
        if (v.getId() == R.id.brandNameFilterBtn) {
            if (BRAND_FILTER_STATE == 0) {
                Collections.sort(currFeedbacks, new Comparator<BrandFeedback>() {
                    @Override
                    public int compare(BrandFeedback f1, BrandFeedback f2) {
                        return f1.getBrand().compareTo(f2.getBrand());
                    }
                });
            } else {
                Collections.sort(currFeedbacks, new Comparator<BrandFeedback>() {
                    @Override
                    public int compare(BrandFeedback f1, BrandFeedback f2) {
                        return f2.getBrand().compareTo(f1.getBrand());
                    }
                });
            }
            FeedbackAdapterClass feedbackAdapterClass = new FeedbackAdapterClass(currFeedbacks);
            recyclerView.setAdapter(feedbackAdapterClass);
            BRAND_FILTER_STATE = 1 - BRAND_FILTER_STATE;
        }
    }

    public ArrayList<BrandFeedback> cloneArray (ArrayList<BrandFeedback> toClone) {
        ArrayList<BrandFeedback> newList = new ArrayList<>();
        for (BrandFeedback f : toClone) {
            newList.add(f);
        }
        return newList;
    }

    @Override // over-riding so that back does not lead to the previously done survey
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}
