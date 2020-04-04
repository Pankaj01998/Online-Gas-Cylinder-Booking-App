package com.example.android.bhojajimaharajgasagency;

import android.widget.EditText;

public class Order_Details {

    private String mConsumerNo;
    private String mDate;
    private String mTime;
    private int mConfirmationStatus;
    private int mDeliveryStatus;
    private String mExpectedDeliveryDay;

    public Order_Details() {
    }

    public Order_Details(String s1, String s2, String s3, int i, int j, String s4){

        this.mConsumerNo = s1;
        this.mDate = s2;
        this.mTime = s3;
        this.mConfirmationStatus = i;
        this.mDeliveryStatus = j;
        this.mExpectedDeliveryDay = s4;
    }

    public String getmConsumerNo() {
        return mConsumerNo;
    }

    public String getmDate(){
        return mDate;
    }

    public String getmTime(){
        return mTime;
    }

    public int getmConfirmationStatus() {
        return mConfirmationStatus;
    }

    public int getmDeliveryStatus() {
        return mDeliveryStatus;
    }

    public String getmExpectedDeliveryDay() {
        return mExpectedDeliveryDay;
    }
}
