package com.example.orbital2019catch.company.survey;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orbital2019catch.R;

import java.util.ArrayList;

public class SurveyAdapter extends RecyclerView.Adapter<SurveyAdapter.MyViewHolder> {

    Context context;
    ArrayList<SurveyResults> results;

    public SurveyAdapter(Context c, ArrayList<SurveyResults> surveyResults) {
        context = c;
        results = surveyResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_company_view_survey, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int count = position + 1;
        holder.name.setText("User " + count);
        holder.q1.setText(results.get(position).getQ1());
        holder.q2.setText(results.get(position).getQ2());
        holder.q3.setText(results.get(position).getQ3());
        holder.q4.setText(results.get(position).getQ4());
        holder.q5.setText(results.get(position).getQ5());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, q1, q2, q3, q4, q5;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.card_company_view_survey_user_name);
            q1 = (TextView) itemView.findViewById(R.id.card_company_view_survey_q1);
            q2 = (TextView) itemView.findViewById(R.id.card_company_view_survey_q2);
            q3 = (TextView) itemView.findViewById(R.id.card_company_view_survey_q3);
            q4 = (TextView) itemView.findViewById(R.id.card_company_view_survey_q4);
            q5 = (TextView) itemView.findViewById(R.id.card_company_view_survey_q5);
        }
    }
}
