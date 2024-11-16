package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EventsActivity extends AppCompatActivity {

    CleverTapAPI cleverTapAPI;
    private LinearLayout eventNameLayout1, eventNamePropertiesLayout2;
    private EditText eventNameEditText1, eventNameEditText2;
    private EditText propertyNameEditText1, propertyNameEditText2, propertyNameEditText3,
            propertyNameEditText4, propertyNameEditText5;
    private EditText propertyValueEditText1, propertyValueEditText2, propertyValueEditText3,
            propertyValueEditText4, propertyValueEditText5;
    private Button confirmEventButton1, confirmEventButton2;
    private Button eventButton, eventWithPropertiesButton;
    private Button chargedBtn;

    private static final int MAX_PROPERTIES = 5; // Maximum number of property pairs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

        cleverTapAPI.pushEvent("Events");

        ImageView rollingImage = findViewById(R.id.rollingImage);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);

        // Initialize views
        eventNameLayout1 = findViewById(R.id.eventNameLayout1);
        eventNamePropertiesLayout2 = findViewById(R.id.eventNamePropertiesLayout2);

        eventNameEditText1 = findViewById(R.id.eventNameEditText1);
        eventNameEditText2 = findViewById(R.id.eventNameEditText2);

        propertyNameEditText1 = findViewById(R.id.propertyNameEditText1);
        propertyNameEditText2 = findViewById(R.id.propertyNameEditText2);
        propertyNameEditText3 = findViewById(R.id.propertyNameEditText3);
        propertyNameEditText4 = findViewById(R.id.propertyNameEditText4);
        propertyNameEditText5 = findViewById(R.id.propertyNameEditText5);

        propertyValueEditText1 = findViewById(R.id.propertyValueEditText1);
        propertyValueEditText2 = findViewById(R.id.propertyValueEditText2);
        propertyValueEditText3 = findViewById(R.id.propertyValueEditText3);
        propertyValueEditText4 = findViewById(R.id.propertyValueEditText4);
        propertyValueEditText5 = findViewById(R.id.propertyValueEditText5);

        confirmEventButton1 = findViewById(R.id.confirmEventButton1);
        confirmEventButton2 = findViewById(R.id.confirmEventButton2);

        eventButton = findViewById(R.id.eventButton);
        eventWithPropertiesButton = findViewById(R.id.eventWithPropertiesButton);
        chargedBtn = findViewById(R.id.chargedButton);

        // Set click listeners for buttons

        chargedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> chargeDetails = new HashMap<String, Object>();
                chargeDetails.put("Amount", 300);
                chargeDetails.put("Payment Mode", "Credit card");
                chargeDetails.put("Charged ID", 24052013);

                HashMap<String, Object> item1 = new HashMap<String, Object>();
                item1.put("Product category", "books");
                item1.put("Book name", "The Millionaire next door");
                item1.put("Quantity", 1);

                HashMap<String, Object> item2 = new HashMap<String, Object>();
                item2.put("Product category", "books");
                item2.put("Book name", "Achieving inner zen");
                item2.put("Quantity", 1);

                HashMap<String, Object> item3 = new HashMap<String, Object>();
                item3.put("Product category", "books");
                item3.put("Book name", "Chuck it, let's do it");
                item3.put("Quantity", 5);

                ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
                items.add(item1);
                items.add(item2);
                items.add(item3);

                cleverTapAPI.pushChargedEvent(chargeDetails, items);

                Toast.makeText(EventsActivity.this, "Your charged event was successfully pushed to CleverTap, User ID: " + cleverTapAPI.getCleverTapID(), Toast.LENGTH_LONG).show();
            }
        });

        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Event without Properties layout
                eventNameLayout1.setVisibility(View.VISIBLE);
                eventNamePropertiesLayout2.setVisibility(View.GONE);
            }
        });

        eventWithPropertiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show Event with Properties layout
                eventNameLayout1.setVisibility(View.GONE);
                eventNamePropertiesLayout2.setVisibility(View.VISIBLE);
                confirmEventButton2.setEnabled(true);
            }
        });

        // Click listener for Confirm Event Button 1
        confirmEventButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle confirm event without properties
                String eventName = eventNameEditText1.getText().toString().trim();
                // Perform necessary actions with eventName
                cleverTapAPI.pushEvent(eventName);
                Toast.makeText(EventsActivity.this, "Event " + eventName + " succesfully pushed to CleverTap Dashboard for user " + cleverTapAPI.getCleverTapID().toString(), Toast.LENGTH_LONG).show();
            }
        });

        // Click listener for Confirm Event Button 2
        confirmEventButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle confirm event with properties
                String eventName = eventNameEditText2.getText().toString();


                String propertyName1 = propertyNameEditText1.getText().toString();
                String propertyValue1 = propertyValueEditText1.getText().toString();
                String TAG = "";
                Log.d(TAG, "Property 1: " + propertyName1 + ", " + propertyValue1);

                String propertyName2 = propertyNameEditText2.getText().toString();
                String propertyValue2 = propertyValueEditText2.getText().toString();
                Log.d(TAG, "Property 2: " + propertyName2 + ", " + propertyValue2);

                String propertyName3 = propertyNameEditText3.getText().toString();
                String propertyValue3 = propertyValueEditText3.getText().toString();
                Log.d(TAG, "Property 3: " + propertyName3 + ", " + propertyValue3);

                String propertyName4 = propertyNameEditText4.getText().toString();
                String propertyValue4 = propertyValueEditText4.getText().toString();
                Log.d(TAG, "Property 4: " + propertyName4 + ", " + propertyValue4);

                String propertyName5 = propertyNameEditText5.getText().toString();
                String propertyValue5 = propertyValueEditText5.getText().toString();
                Log.d(TAG, "Property 5: " + propertyName5 + ", " + propertyValue5);

                // Perform necessary actions with eventName, propertyName1, propertyValue1, etc.
                HashMap<String, Object> kv = new HashMap<>();
                if (propertyName1 != "" & propertyValue1 != "") {
                    kv.put(propertyName1,propertyValue1);
                }
                if (propertyName2 != "" & propertyValue2 != "") {
                    kv.put(propertyName2,propertyValue2);
                }
                if (propertyName3 != "" & propertyValue3 != "") {
                    kv.put(propertyName3,propertyValue3);
                }
                if (propertyName4 != "" & propertyValue4 != "") {
                    kv.put(propertyName4,propertyValue4);
                }
                if (propertyName5 != "" & propertyValue5 != "") {
                    kv.put(propertyName5,propertyValue5);
                }

                Log.d(TAG, kv.toString());

                Toast.makeText(EventsActivity.this, "Event " + eventName + " succesfully pushed to CleverTap Dashboard for user " + cleverTapAPI.getCleverTapID().toString(), Toast.LENGTH_LONG).show();
                cleverTapAPI.pushEvent(eventName,kv);
            }
        });
    }

    // Method to update the state of Confirm Event Button 1
    private void updateConfirmEventButtonState1() {
        String eventName = eventNameEditText1.getText().toString().trim();
        boolean enableButton = !eventName.isEmpty();
        confirmEventButton1.setEnabled(enableButton);
    }

}