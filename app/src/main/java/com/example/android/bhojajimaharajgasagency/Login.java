package com.example.android.bhojajimaharajgasagency;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class Login extends AppCompatActivity {

    private FirebaseAuth mLoginFirebaseAuth;
    private FirebaseAuth.AuthStateListener  mLoginAuthStateListener;
    public static final int RC_SIGN_IN = 1;

    private String mLogUserName;
    private int mLogVillage;
    private int mLogGender;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUsersDatabaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        androidx.appcompat.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffff8800")));


        mLoginFirebaseAuth = FirebaseAuth.getInstance();


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUsersDatabaseReference = mFirebaseDatabase.getReference().child("Users_Info");

        Bundle bundle = getIntent().getExtras();
        mLogUserName = bundle.getString("userName");
        mLogVillage = bundle.getInt("userVillage");
        mLogGender = bundle.getInt("userGender");

        Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  5   @@@@@@@@@@@@@@@@@");


        mLoginAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser logUser = firebaseAuth.getCurrentUser();

                Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  6   @@@@@@@@@@@@@@@@@");

                if (logUser != null) {


                    Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  8   @@@@@@@@@@@@@@@@@");
                    Toast.makeText(Login.this, "Already Signed In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this, Home_Page.class);
                    startActivity(i);
                }
                else {

                    Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  7   @@@@@@@@@@@@@@@@@");

                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.PhoneBuilder().build()))
                                    .build(),
                            RC_SIGN_IN);
                }

            }
        };

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  1   @@@@@@@@@@@@@@@@@");


        if (requestCode == RC_SIGN_IN) {

            Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  2   @@@@@@@@@@@@@@@@@");


            if(resultCode == RESULT_CANCELED) {

                Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@",  "  3  @@@@@@@@@@@@@@@@@");

                Toast.makeText(this, "Signed In Canceled", Toast.LENGTH_SHORT).show();
                finish();
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);

            }
            else if (resultCode == RESULT_OK){

                Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "   4  @@@@@@@@@@@@@@@@@");


                String phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();

                User_Info userInfo = new User_Info(mLogUserName, mLogVillage, mLogGender);
                mFirebaseDatabase.getReference().child("Users_Info").child(phone).push().setValue(userInfo);
                finish();
                Toast.makeText(Login.this, "Uploaded", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Login.this, Home_Page.class);
                startActivity(i);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  9   @@@@@@@@@@@@@@@@@");
        mLoginFirebaseAuth.addAuthStateListener(mLoginAuthStateListener);
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  10   @@@@@@@@@@@@@@@@@");
        if(mLoginAuthStateListener != null){
            Log.e("MAin @@@@@@@@@@@@@@@@@@@@@@", "  11   @@@@@@@@@@@@@@@@@");
            mLoginFirebaseAuth.removeAuthStateListener(mLoginAuthStateListener);
        }
    }

}
