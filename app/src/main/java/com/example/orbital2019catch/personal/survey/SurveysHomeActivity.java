package com.example.orbital2019catch.personal.survey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Nullable;

public class SurveysHomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button brandFilter, expiryFilter, payoutFilter;
    ImageView detailedFilter;
    String[] categories;
    Integer[] categoryIndexes;
    boolean[] checkedCategories;
    ArrayList<Integer> userSelectedSurveys;
    ViewPager viewPager;

    public static int BRAND_FILTER_STATE = 0, EXPIRY_FILTER_STATE = 0, PAYOUT_FILTER_STATE = 0;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Survey> originalSurveys;
    private ArrayList<Survey> currSurveys;
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
                originalSurveys = new ArrayList<>();
                currSurveys = new ArrayList<>();
                for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                    originalSurveys.add(snapshot.toObject(Survey.class));
                    currSurveys.add(snapshot.toObject(Survey.class));
                }
                SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(currSurveys);
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

        brandFilter = (Button) findViewById(R.id.brandNameFilterBtn);
        expiryFilter = (Button) findViewById(R.id.expiryDateFilterBtn);
        payoutFilter = (Button) findViewById(R.id.payoutFilterBtn);
        detailedFilter = (ImageView) findViewById(R.id.detailedFilterBtn);

        brandFilter.setOnClickListener(this);
        expiryFilter.setOnClickListener(this);
        payoutFilter.setOnClickListener(this);
        detailedFilter.setOnClickListener(this);

        categories = new String[]{"Lifestyle", "Sports", "Service", "Fashion"};
        categoryIndexes = new Integer[]{0, 1, 2, 3};
        checkedCategories = new boolean[categories.length];
        for (int i = 0; i < categories.length; i++) {
            checkedCategories[i] = true;
        }
        userSelectedSurveys = new ArrayList<Integer>(Arrays.asList(categoryIndexes));

        // for image slider
        viewPager = (ViewPager)findViewById(R.id.imageSlider);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            SurveysHomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() == 0) {
                        viewPager.setCurrentItem(1);
                    } else if (viewPager.getCurrentItem() == 1) {
                        viewPager.setCurrentItem(2);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }

    private void search(String str) {
        ArrayList<Survey> list = new ArrayList<>();
        for (Survey s : originalSurveys) {
            if (s.getName().toLowerCase().contains(str.toLowerCase())) {
                list.add(s);
            }
        }
        currSurveys = cloneArray(list);
        SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(list);
        recyclerView.setAdapter(surveyAdapterClass);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.detailedFilterBtn) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(SurveysHomeActivity.this);
            mBuilder.setTitle("Select Brand Categories");
            mBuilder.setMultiChoiceItems(categories, checkedCategories, new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                    if (isChecked) {
                        if (!userSelectedSurveys.contains((Integer)position)) {
                            userSelectedSurveys.add((Integer)position);
                        } else {
                            userSelectedSurveys.remove((Integer)position);
                        }
                    } else {
                        if (userSelectedSurveys.contains((Integer)position)) {
                            userSelectedSurveys.remove((Integer)position);
                        }
                    }
                }
            });
            mBuilder.setCancelable(false);
            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Survey> selectedSurveys = new ArrayList<>();
                    for (Integer i : userSelectedSurveys) {
                        String cat = categories[i];
                        for (Survey s : originalSurveys) {
                            if (s.getCategories().contains(cat) && !selectedSurveys.contains(s)) {
                                selectedSurveys.add(s);
                            }
                        }
                    }
                    currSurveys = cloneArray(selectedSurveys);
                    SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(selectedSurveys);
                    recyclerView.setAdapter(surveyAdapterClass);
                }
            });
            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            mBuilder.setNeutralButton("Select All", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (int i = 0; i < checkedCategories.length; i++) {
                        checkedCategories[i] = true;
                        userSelectedSurveys = new ArrayList<Integer>(Arrays.asList(categoryIndexes));
                        currSurveys = cloneArray(originalSurveys);
                        SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(originalSurveys);
                        recyclerView.setAdapter(surveyAdapterClass);
                    }
                }
            });
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        } else if (v.getId() == R.id.brandNameFilterBtn) {
            if (BRAND_FILTER_STATE == 0) {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        return s1.getBrand().compareTo(s2.getBrand());
                    }
                });
            } else {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        return s2.getBrand().compareTo(s1.getBrand());
                    }
                });
            }
            SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(currSurveys);
            recyclerView.setAdapter(surveyAdapterClass);
            BRAND_FILTER_STATE = 1 - BRAND_FILTER_STATE;
        } else if (v.getId() == R.id.expiryDateFilterBtn) {
            if (EXPIRY_FILTER_STATE == 0) {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        return s1.getExpiryDF().compareTo(s2.getExpiryDF());
                    }
                });
            } else {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        return s2.getExpiryDF().compareTo(s1.getExpiryDF());
                    }
                });
            }
            SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(currSurveys);
            recyclerView.setAdapter(surveyAdapterClass);
            EXPIRY_FILTER_STATE = 1 - EXPIRY_FILTER_STATE;
        } else if (v.getId() == R.id.payoutFilterBtn) {
            if (PAYOUT_FILTER_STATE == 0) {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        if (s1.getCashout() - s2.getCashout() > 0) {
                            return -1;
                        } else if (s1.getCashout() - s2.getCashout() < 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            } else {
                Collections.sort(currSurveys, new Comparator<Survey>() {
                    @Override
                    public int compare(Survey s1, Survey s2) {
                        if (s2.getCashout() - s1.getCashout() > 0) {
                            return -1;
                        } else if (s2.getCashout() - s1.getCashout() < 0) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });
            }
            SurveyAdapterClass surveyAdapterClass = new SurveyAdapterClass(currSurveys);
            recyclerView.setAdapter(surveyAdapterClass);
            PAYOUT_FILTER_STATE = 1 - PAYOUT_FILTER_STATE;
        }
    }

    public ArrayList<Survey> cloneArray (ArrayList<Survey> toClone) {
        ArrayList<Survey> newList = new ArrayList<>();
        for (Survey s : toClone) {
            newList.add(s);
        }
        return newList;
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

