package com.example.orbital2019catch.company.feedback;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.orbital2019catch.R;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {

    Context context;
    ArrayList<FeedbackResults> results;

    public FeedbackAdapter(Context c, ArrayList<FeedbackResults> feedbackResults) {
        context = c;
        results = feedbackResults;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.card_company_view_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        int count = position + 1;
        holder.name.setText("User " + count);
        holder.answer.setText(results.get(position).getFeedback());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, answer;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.card_company_view_feedback_user_name);
            answer = (TextView) itemView.findViewById(R.id.card_company_view_feedback_answer);
        }
    }
}
