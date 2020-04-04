package com.example.android.bhojajimaharajgasagency;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PreviousOrdersAdapter extends ArrayAdapter<Order_Details> {

    public PreviousOrdersAdapter(Context context, int resource, List<Order_Details> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);
        TextView deliveryTextview = (TextView) convertView.findViewById(R.id.deliveryDateTextView);

        Order_Details order = getItem(position);

        dateTextView.setText(order.getmDate());
        timeTextView.setText(order.getmTime());
        deliveryTextview.setText(order.getmExpectedDeliveryDay());

        return convertView;

    }
}

