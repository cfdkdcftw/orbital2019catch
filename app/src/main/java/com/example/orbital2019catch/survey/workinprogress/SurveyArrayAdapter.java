package com.example.orbital2019catch.survey.workinprogress;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.orbital2019catch.R;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SurveyArrayAdapter extends ArrayAdapter{

    private List<Survey> surveys = new ArrayList<>();
    private Context context;

    public SurveyArrayAdapter(Context context, int resource) {
            super(context, resource);
            this.context = context;
    }

    static class ItemViewHolder {
            TextView surveyDescription;
            Button button;
            ImageView companyLogo;
    }

    public void add(Survey object) {
        surveys.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.surveys.size();
    }

    @Override
    public Survey getItem(int position) {
        return this.surveys.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_survey_list_view, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.surveyDescription = (TextView) row.findViewById(R.id.survey_card_description);
            viewHolder.button = (Button) row.findViewById(R.id.survey_card_btn);
            viewHolder.companyLogo = (ImageView) row.findViewById(R.id.survey_card_logo);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) row.getTag();
        }
        Survey stat = getItem(position);
        viewHolder.surveyDescription.setText(stat.getDescription());
        viewHolder.button.setText("Earn " + String.format("$%0.2f", stat.getPayout()) + " now!");
        try {
            URL url = new URL(stat.getLogoURL());
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            viewHolder.companyLogo.setImageBitmap(bmp);
        } catch (Exception e) {
            Log.d("Logo URL don't work", e.getMessage());
        } finally {
            viewHolder.companyLogo.setImageResource(R.drawable.survey);
        }
        return row;
    }
}