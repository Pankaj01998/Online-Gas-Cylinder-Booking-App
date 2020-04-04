package com.example.android.bhojajimaharajgasagency;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Previous_Orders extends AppCompatActivity {


    private FirebaseDatabase mPreviousFirebaseDatabase;
    private DatabaseReference mPreviousOrdersDatabaseReference;

    private ValueEventListener mPreviousValueEventListener;

    private String mUserPhone;

    private PreviousOrdersAdapter mPreviousOrdersAdapter;
    private ListView mPreviousOrdersListView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prevous_order);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));


        mPreviousOrdersListView = (ListView) findViewById(R.id.list_previous_orders);
        List<Order_Details> previousOrders = new ArrayList<>();
        mPreviousOrdersAdapter = new PreviousOrdersAdapter(this, R.layout.list_item, previousOrders);
        mPreviousOrdersListView.setAdapter(mPreviousOrdersAdapter);


        mPreviousFirebaseDatabase = FirebaseDatabase.getInstance();

        Bundle bundle = getIntent().getExtras();
        mUserPhone = bundle.getString("Phone_Number");

        mPreviousOrdersDatabaseReference = mPreviousFirebaseDatabase.getReference().child("Order_Details").child(mUserPhone);

        attachDatabaseReadListener();

    }

    private void attachDatabaseReadListener() {
        mPreviousValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mPreviousOrdersAdapter.clear();
                Log.e("Previous Orders  1 ", "Entered");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.e("Previous Orders  2 ", "Entered");
                    Order_Details order_details = snapshot.getValue(Order_Details.class);
                    int deliveryStatus = order_details.getmDeliveryStatus();
                    if(deliveryStatus == 1){
                        Log.e("Previous Orders  3 ", "Entered");
                        mPreviousOrdersAdapter.add(order_details);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mPreviousValueEventListener != null){
            mPreviousOrdersDatabaseReference.addValueEventListener(mPreviousValueEventListener);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mPreviousValueEventListener != null){
            mPreviousOrdersDatabaseReference.removeEventListener(mPreviousValueEventListener);
        }
    }

}
