package com.example.orbital2019catch.personal.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.orbital2019catch.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentArrayAdapter extends ArrayAdapter {

    private List<PaymentRequest> requests = new ArrayList<PaymentRequest>();

    public PaymentArrayAdapter(Context context, int resource) {
        super(context, resource);
    }

    static class ItemViewHolder {
        TextView date;
        TextView time;
        TextView amount;
        TextView phoneNumber;
    }

    public void add(PaymentRequest request) {
        requests.add(request);
        super.add(request);
    }

    @Override
    public int getCount() {
        return this.requests.size();
    }

    @Override
    public PaymentRequest getItem(int position) {
        return this.requests.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ItemViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.payment_item, parent, false);
            viewHolder = new ItemViewHolder();
            viewHolder.date = (TextView) row.findViewById(R.id.payment_item_date);
            viewHolder.time = (TextView) row.findViewById(R.id.payment_item_time);
            viewHolder.amount = (TextView) row.findViewById(R.id.payment_item_amount);
            viewHolder.phoneNumber = (TextView) row.findViewById(R.id.payment_item_phone);
            row.setTag(viewHolder);
        } else {
            viewHolder = (ItemViewHolder) row.getTag();
        }

        PaymentRequest stat = getItem(position);
        viewHolder.date.setText(stat.getDate());
        viewHolder.time.setText(stat.getTime());
        viewHolder.amount.setText(String.format("$%.2f", stat.getAmount()));
        viewHolder.phoneNumber.setText(stat.getPhoneNumber());
        return row;
    }
}