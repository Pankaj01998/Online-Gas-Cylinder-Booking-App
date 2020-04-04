package com.example.android.bhojajimaharajgasagency;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

//    private Handler mHandler = new Handler();

    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();

        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();

        Log.e("######################", "@@@@@@@@@@@@@@@@@@@@@@@ pen b");

        authStateListener();

    }

    private void authStateListener() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.e("real MAin @@@@@@@@@@@@@@@@@@@@@@", "  1   @@@@@@@@@@@@@@@@@");
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null)
                {

                    Log.e("real MAin @@@@@@@@@@@@@@@@@@@@@@", "  2   @@@@@@@@@@@@@@@@@");
                    Button login_button = (Button) findViewById(R.id.login_button);

                    login_button.setVisibility(View.VISIBLE);

                    login_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MainActivity.this, Info_Page.class);
                            startActivity(i);
                        }
                    });

                }
                else{
                    Log.e("real MAin @@@@@@@@@@@@@@@@@@@@@@", "  3   @@@@@@@@@@@@@@@@@");
                    Toast.makeText(MainActivity.this, "Signed In", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, Home_Page.class);
                    finish();
                    startActivity(i);
                }
            }
        };
    }


    @Override
    protected void onResume() {
        Log.e("real MAin @@@@@@@@@@@@@@@@@@@@@@", "  4   @@@@@@@@@@@@@@@@@");
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        Log.e("real MAin @@@@@@@@@@@@@@@@@@@@@@", "  5   @@@@@@@@@@@@@@@@@");
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }
}
