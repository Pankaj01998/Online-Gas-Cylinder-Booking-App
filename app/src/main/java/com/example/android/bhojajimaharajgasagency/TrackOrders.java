package com.example.android.bhojajimaharajgasagency;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrackOrders extends AppCompatActivity {

    private FirebaseDatabase mTrackFirebaseDatabase;
    private DatabaseReference mTrackOrdersDatabaseReference;

    private ValueEventListener mTrackValueEventListener;
    private ChildEventListener mChildListner;

    private String mUserPhone;

    private TrackOrdersAdapter mTrackOrdersAdapter;
    private ListView mTrackOrdersListView;

    private String mName;
    private int mVillage;
    private int mGender;

    private NotificationCompat.Builder mBuilder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trackorder);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));


        mTrackOrdersListView = (ListView) findViewById(R.id.list_track_orders);


        List<EntireOrderDetails> trackOrders = new ArrayList<>();
        mTrackOrdersAdapter = new TrackOrdersAdapter(this, R.layout.list_item_track, trackOrders);
        mTrackOrdersListView.setAdapter(mTrackOrdersAdapter);

        mTrackFirebaseDatabase = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        mUserPhone = bundle.getString("Phone_Number");
        mName = bundle.getString("Name");
        mVillage = bundle.getInt("Village");
        mGender = bundle.getInt("Gender");


        mTrackOrdersDatabaseReference = mTrackFirebaseDatabase.getReference().child("Order_Details").child(mUserPhone);

        attachDatabaseReadListener();


    }



    private void attachDatabaseReadListener() {
        mTrackValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mTrackOrdersAdapter.clear();
                Log.e("Track Orders  1 ", "Entered");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("Track Orders  2 ", "Entered");
                    Order_Details order_details = snapshot.getValue(Order_Details.class);
                    int deliveryStatus = order_details.getmDeliveryStatus();
                    if(deliveryStatus == 0){
                        Log.e("Track Orders  3 ", "Entered");
                        mTrackOrdersAdapter.add(new EntireOrderDetails(mName, mVillage, mGender, mUserPhone, order_details.getmConsumerNo(), order_details.getmDate(), order_details.getmTime(), order_details.getmConfirmationStatus(), order_details.getmDeliveryStatus(), order_details.getmExpectedDeliveryDay()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        mChildListner = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Order_Details order = dataSnapshot.getValue(Order_Details.class);
                String msg = "";

                if (order.getmConfirmationStatus() == 1){
                    msg = "Hurray your order has been placed.";
                }
                else if (order.getmConfirmationStatus() == -1){
                    msg = "Sorry your order has been rejected.";
                }


                if (order.getmDeliveryStatus() == 1){
                    msg = "Congrats your order has been delivered.";
                }

                NotificationManager notificationManager = (NotificationManager) TrackOrders.this.getSystemService(Context.NOTIFICATION_SERVICE);

                int notificationId = 1;
                String channelId = "channel-01";
                String channelName = "Channel Name";
                int importance = NotificationManager.IMPORTANCE_HIGH;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(
                            channelId, channelName, importance);
                    notificationManager.createNotificationChannel(mChannel);
                }

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(TrackOrders.this, channelId)
                        .setSmallIcon(R.drawable.g1)
                        .setContentTitle("Confirmation Notification")
                        .setContentText(msg);


                Intent notificationIntent = new Intent(TrackOrders.this, MainActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(TrackOrders.this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(contentIntent);

                notificationManager.notify(notificationId, mBuilder.build());

                Log.e("Track Orders 33 1 ", "Entered");

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };
        mTrackOrdersDatabaseReference.addValueEventListener(mTrackValueEventListener);
        mTrackOrdersDatabaseReference.addChildEventListener(mChildListner);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mTrackValueEventListener != null){
            mTrackOrdersDatabaseReference.addValueEventListener(mTrackValueEventListener);
        }
        if (mChildListner != null){
            mTrackOrdersDatabaseReference.addChildEventListener(mChildListner);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTrackValueEventListener != null){
            mTrackOrdersDatabaseReference.removeEventListener(mTrackValueEventListener);
        }
        if (mChildListner != null){
            mTrackOrdersDatabaseReference.removeEventListener(mChildListner);
        }
    }
}