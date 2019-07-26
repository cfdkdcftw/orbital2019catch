package com.example.orbital2019catch.personal.feedback;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orbital2019catch.R;
import com.example.orbital2019catch.personal.survey.Survey;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeedbackAdapterClass extends RecyclerView.Adapter<FeedbackAdapterClass.MyViewHolder> {
    ArrayList<BrandFeedback> feedbackList;

    public FeedbackAdapterClass(ArrayList<BrandFeedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String brand = feedbackList.get(position).getBrand();
        holder.feedbackCompanyName.setText(brand);
        if (brand.equals("Google")) {
            holder.feedbackCompanyLogo.setImageResource(R.drawable.googleg_standard_color_18);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View itemView) {
                    Intent intent = new Intent(itemView.getContext(), GoogleFeedbackActivity.class);
                    itemView.getContext().startActivity(intent);

                } });

        }
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView feedbackCompanyLogo;
        TextView feedbackCompanyName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            feedbackCompanyLogo = itemView.findViewById(R.id.feedback_company_logo);
            feedbackCompanyName = itemView.findViewById(R.id.feedback_company_name);
        }
    }

}
