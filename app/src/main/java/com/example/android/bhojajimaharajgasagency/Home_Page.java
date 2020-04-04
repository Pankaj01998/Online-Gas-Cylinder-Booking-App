package com.example.android.bhojajimaharajgasagency;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Home_Page extends AppCompatActivity {

    private FirebaseDatabase mHomeFirebaseDatabase;
    private DatabaseReference mHomeUsersDatabaseReference;

    private ChildEventListener mHomeChildEventListener;

    private TextView muserName;
    private TextView mOrder;
    private Button mPreviousOrdersButton;
    private Button mTrackOrderButton;


    private String mPhone;

    private String mName;
    private int mVillage;
    private int mGender;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("HOme MAin @@@@@@@@@@@@@@@@@@@@@@", "  1   @@@@@@@@@@@@@@@@@");
        setContentView(R.layout.home_page);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));


        mPhone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

        Log.e("HOme MAin @@@@@@@@@@@@@@@@@@@@@@   2 ", mPhone);

        mHomeFirebaseDatabase = FirebaseDatabase.getInstance();
        mHomeUsersDatabaseReference = mHomeFirebaseDatabase.getReference().child("Users_Info").child(mPhone);

        attachDatabaseReadListener();

        attachOrderOnclickListener();

        readPreviousOrders();

        trackPreviousOrderOnClickListener();

    }

    private void trackPreviousOrderOnClickListener() {
        mTrackOrderButton = (Button) findViewById(R.id.button_track_order);
        mTrackOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Page.this, TrackOrders.class);
                i.putExtra("Phone_Number", mPhone);
                i.putExtra("Name", mName);
                i.putExtra("Village", mVillage);
                i.putExtra("Gender", mGender);
                startActivity(i);
            }
        });
    }

    private void readPreviousOrders() {
        mPreviousOrdersButton = (Button) findViewById(R.id.button_previous_order);

        mPreviousOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Page.this, Previous_Orders.class);
                i.putExtra("Phone_Number", mPhone);
                startActivity(i);
            }
        });

    }

    private void attachOrderOnclickListener() {
        mOrder = (TextView) findViewById(R.id.order);
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home_Page.this, Order.class);
                i.putExtra("Phone_Number", mPhone);
                i.putExtra("Name", mName);
                i.putExtra("Village", mVillage);
                i.putExtra("Gender", mGender);
                startActivity(i);
            }
        });
    }


    private void attachDatabaseReadListener(){
        mHomeChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User_Info info = dataSnapshot.getValue(User_Info.class);
                Log.e("HOme MAin @   3 ", info.getName());

                mName = info.getName();
                mGender = info.getGender();
                mVillage = info.getVillage();

                muserName = (TextView) findViewById(R.id.user_name);
                muserName.setText(info.getName());
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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

        mHomeUsersDatabaseReference.addChildEventListener(mHomeChildEventListener);

    }

}
