package com.example.android.bhojajimaharajgasagency;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Info_Page extends AppCompatActivity {

    public String mName;
    private EditText mEditText;

    private Spinner mGenderSpinner;
    private int mGender;


    private Spinner mVillageSpinner;
    private int mVillage;




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.info_page);


        mEditText = (EditText) findViewById(R.id.edit_pet_name);

        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);
        mVillageSpinner = (Spinner) findViewById(R.id.spinner_village);
        setUpSpinner();


        Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  1   @@@@@@@@@@@@@@@@@");

        Button send_button = (Button) findViewById(R.id.send_button);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  2   @@@@@@@@@@@@@@@@@");
                mName = mEditText.getText().toString().trim();
                Intent i = new Intent(Info_Page.this, Login.class);
                i.putExtra("userName", mName);
                i.putExtra("userVillage", mVillage);
                i.putExtra("userGender", mGender);
                finish();
                startActivity(i);

            }
        });

    }

    private void setUpSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout

        Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  3   @@@@@@@@@@@@@@@@@");
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_gender_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mGenderSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mGenderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  4   @@@@@@@@@@@@@@@@@");
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_male))) {
                        mGender = 1; // Male
                    } else if (selection.equals(getString(R.string.gender_female))) {
                        mGender = 2; // Female
                    } else {
                        mGender = 0; // Unknown
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGender = 0; // Unknown
            }
        });


        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter villageSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_village_options, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        villageSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mVillageSpinner.setAdapter(villageSpinnerAdapter);


        Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  5   @@@@@@@@@@@@@@@@@");

        // Set the integer mSelected to the constant values
        mVillageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Info MAin @@@@@@@@@@@@@@@@@@@@@@", "  6   @@@@@@@@@@@@@@@@@");
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.gender_Allipur))) {
                        mVillage = 1; // Allipur
                    } else if (selection.equals(getString(R.string.gender_Shirud))) {
                        mVillage = 2; // Shirud
                    } else {
                        mVillage = 0; // Pawani
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mVillage = 0; // Unknown
            }
        });

    }

}
