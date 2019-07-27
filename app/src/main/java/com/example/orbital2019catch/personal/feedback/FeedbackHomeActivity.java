package com.example.orbital2019catch.personal.feedback;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.orbital2019catch.personal.MainActivity;
import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.survey.Survey;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Nullable;

public class FeedbackHomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button brandFilter;
    Spinner categorySpinner;
    public static int BRAND_FILTER_STATE = 0, CATEGORY_FILTER_STATE = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<BrandFeedback> originalFeedbacks = new ArrayList<>();;
    private ArrayList<BrandFeedback> currFeedbacks = new ArrayList<>();;
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
        categorySpinner = (Spinner) findViewById(R.id.categoryFilterBtn);

        brandFilter.setOnClickListener(this);

        String[] categoriesArr = new String[]{"Technology", "Fashion", "F&B"};
        List<String> categories = new ArrayList<>();
        categories.add(0,"All Categories");
        categories.addAll(Arrays.asList(categoriesArr));

        // style and populate spinner
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter(this, R.layout.feedback_spinner_layout, categories);

        // dropdown layout style
        dataAdapter.setDropDownViewResource(R.layout.feedback_spinner_dropdown_layout);

        // attaching data adapter to spinner
        categorySpinner.setAdapter(dataAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("All Categories")) {
                    if (!originalFeedbacks.isEmpty() && recyclerView.getChildCount() != originalFeedbacks.size()) {
                        currFeedbacks = cloneArray(originalFeedbacks);
                        FeedbackAdapterClass feedbackAdapterClass = new FeedbackAdapterClass(originalFeedbacks);
                        recyclerView.setAdapter(feedbackAdapterClass);
                    }
                } else {
                    String category = parent.getItemAtPosition(position).toString();
                    ArrayList<BrandFeedback> selected = new ArrayList<>();
                    for (BrandFeedback f : originalFeedbacks) {
                        if (f.getCategories().contains(category)) {
                            selected.add(f);
                        }
                    }
                    currFeedbacks = cloneArray(selected);
                    FeedbackAdapterClass feedbackAdapterClass = new FeedbackAdapterClass(selected);
                    recyclerView.setAdapter(feedbackAdapterClass);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
