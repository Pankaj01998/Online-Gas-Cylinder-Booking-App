package com.example.android.bhojajimaharajgasagency;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class TrackOrdersAdapter extends ArrayAdapter<EntireOrderDetails> {

    public TrackOrdersAdapter(Context context, int resource, List<EntireOrderDetails> objects) {
        super(context, resource, objects);
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.list_item_track, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.timeTextView);

        EntireOrderDetails order = getItem(position);

        dateTextView.setText(order.getmDate());
        timeTextView.setText(order.getmTime());


        TextView orderStatusTextView = (TextView) convertView.findViewById(R.id.orderStatusTextView);
        TextView expectedDeliveryTextView = (TextView) convertView.findViewById(R.id.expectedDeliveryTextView);
        TextView deliveryTextView = (TextView) convertView.findViewById(R.id.deliveryTextView);




        if (order.getmConfirmationStatus() == 1){
            orderStatusTextView.setText("Confirm");
            deliveryTextView.setVisibility(View.VISIBLE);
            expectedDeliveryTextView.setVisibility(View.VISIBLE);
            expectedDeliveryTextView.setText(order.getmExpectedDeliveryDay());

        }
        else if(order.getmConfirmationStatus() == -1){
            orderStatusTextView.setText("Rejected");
            expectedDeliveryTextView.setVisibility(View.GONE);
            deliveryTextView.setVisibility(View.GONE);
        }
        else {
            orderStatusTextView.setText("In Process");
            expectedDeliveryTextView.setVisibility(View.GONE);
            deliveryTextView.setVisibility(View.GONE);
        }

        return convertView;
    }
}

