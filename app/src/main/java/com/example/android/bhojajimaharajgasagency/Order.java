package com.example.android.bhojajimaharajgasagency;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.TimeZone;

public class Order extends AppCompatActivity {

    private EditText mConsumerNumber;
    private FirebaseDatabase mOrderFirebaseDatabase;
    private DatabaseReference mOrderUsersDatabaseReference;

    private String  mOrderPhone;

    private Button mPlaceOrderButton;

    private String mName;
    private int mVillage;
    private int mGender;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));

        Bundle bundle = getIntent().getExtras();
        mOrderPhone = bundle.getString("Phone_Number");
        mName = bundle.getString("Name");
        mVillage = bundle.getInt("Village");
        mGender = bundle.getInt("Gender");


        mConsumerNumber = (EditText) findViewById(R.id.edit_consumer_no);

        mPlaceOrderButton = (Button) findViewById(R.id.place_order);

        mOrderFirebaseDatabase = FirebaseDatabase.getInstance();
        mOrderUsersDatabaseReference = mOrderFirebaseDatabase.getReference().child("Order_Details").child(mOrderPhone);
        mPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                    String formattedDate = df.format(c);

                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+1:00"));
                Date currentLocalTime = cal.getTime();
                @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("HH:mm:ss a");
                date.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));

                String localTime = date.format(currentLocalTime);

                mOrderUsersDatabaseReference.push().setValue(new Order_Details(mConsumerNumber.getText().toString().trim(), formattedDate, localTime, 0, 0, ""));
                mOrderFirebaseDatabase.getReference().child("New_Orders").push().setValue(new EntireOrderDetails(mName, mVillage, mGender, mOrderPhone, mConsumerNumber.getText().toString().trim(), formattedDate, localTime, 0, 0, "") );
                mOrderFirebaseDatabase.getReference().child("All_Orders").push().setValue(new EntireOrderDetails(mName, mVillage, mGender, mOrderPhone, mConsumerNumber.getText().toString().trim(), formattedDate, localTime, 0, 0, "") );
                Toast.makeText(Order.this, "Order Placed", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Order.this, OrderConfirmed.class);
                finish();
                startActivity(i);
            }
        });




    }
}
