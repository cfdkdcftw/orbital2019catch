package com.example.orbital2019catch.personal.profile;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.orbital2019catch.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class PaymentHistoryActivity extends AppCompatActivity {

    private TextView emptyTextView;
    private ListView listView;
    private PaymentArrayAdapter paymentArrayAdapter;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference mRef;
    private ArrayList<PaymentRequest> requests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        listView = (ListView) findViewById(R.id.payment_list_view);
        paymentArrayAdapter = new PaymentArrayAdapter(getApplicationContext(), R.layout.payment_item);
        Parcelable state = listView.onSaveInstanceState();
        listView.setAdapter(paymentArrayAdapter);
        listView.onRestoreInstanceState(state);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mRef = mDatabase.getReference("payments/" + mAuth.getCurrentUser().getUid());
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    PaymentRequest paymentRequest = ds.getValue(PaymentRequest.class);
                    addRequest(paymentRequest);
                    Log.d("payment", paymentRequest.getPhoneNumber());
                }

                if (requests.size() == 0) {
                    emptyTextView = (TextView) findViewById(R.id.payment_empty_text);
                    emptyTextView.setText("There are no past transactions available to view!");
                }

                int idx = requests.size() - 1;
                for (int i = 0; i < requests.size(); i++) {
                    paymentArrayAdapter.add(requests.get(idx));
                    idx--;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("mRef.addValueEventListener", "cancelled");
            }
        });
    }

    private void addRequest(PaymentRequest paymentRequest) {
        requests.add(paymentRequest);
        Log.d("addition", String.valueOf(requests.size()));
    }

    public void onPause() {
        super.onPause();
        // to disable animation when back button is clicked
        overridePendingTransition(0, 0);
    }
}
