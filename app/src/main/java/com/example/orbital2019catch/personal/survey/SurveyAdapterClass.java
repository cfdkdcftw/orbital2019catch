package com.example.orbital2019catch.personal.survey;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class SurveyAdapterClass extends RecyclerView.Adapter<SurveyAdapterClass.MyViewHolder> {
    ArrayList<Survey> surveyList;
    public SurveyAdapterClass(ArrayList<Survey> surveyList) {
        this.surveyList = surveyList;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.survey_holder, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.surveyDescription.setText(surveyList.get(position).getName());
        holder.expiryDate.setText("Ends on " + surveyList.get(position).getExpiry());
        holder.earnRate.setText(surveyList.get(position).getCashout());
        holder.quota.setMax((int)surveyList.get(position).getMax());
        holder.quota.setProgress((int)surveyList.get(position).getCurr());
        holder.respondents.setText(""+ surveyList.get(position).getCurr() + " responded" );

        String brand = surveyList.get(position).getBrand();
        if (brand.equals("spotify")) {
            holder.surveyCompanyIcon.setImageResource(R.drawable.spotify_logo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Intent intent = new Intent(itemView.getContext(), SurveyLocalSpotify.class);
                    itemView.getContext().startActivity(intent);
                } });

        } else if (brand.equals("nike")) {
            holder.surveyCompanyIcon.setImageResource(R.drawable.nike_logo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Intent intent = new Intent(itemView.getContext(), SurveyFirebaseNike.class);
                    itemView.getContext().startActivity(intent);
                } });

        } else if (brand.equals("uniqlo")) {
            holder.surveyCompanyIcon.setImageResource(R.drawable.uniqlo);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    Intent intent = new Intent(itemView.getContext(), SurveyFirebaseUniqlo.class);
                    itemView.getContext().startActivity(intent);
                } });

        }
    }

    @Override
    public int getItemCount() {
        return surveyList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView surveyCompanyIcon;
        TextView surveyDescription, expiryDate, earnRate, respondents;
        ProgressBar quota;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            surveyCompanyIcon = itemView.findViewById(R.id.survey_company_icon);
            surveyDescription = itemView.findViewById(R.id.survey_description);
            expiryDate = itemView.findViewById(R.id.expiry_date);
            earnRate = itemView.findViewById(R.id.earn_rate);
            respondents = itemView.findViewById(R.id.respondents);
            quota = itemView.findViewById(R.id.quota);
        }
    }
}
