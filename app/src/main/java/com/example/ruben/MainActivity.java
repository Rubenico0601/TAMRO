package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.ImageView;
import android.widget.Toast;

import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.PushPermissionResponseListener;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.inapp.CTLocalInApp;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements CTInboxListener {

    CleverTapAPI cleverTapAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        cleverTapAPI.enableDeviceNetworkInfoReporting(true); //Location Tracking

        //Set the Notification Inbox Listener
        cleverTapAPI.setCTNotificationInboxListener((CTInboxListener) this);
        //Initialize the inbox and wait for callbacks on overridden methods
        cleverTapAPI.initializeInbox();

        cleverTapAPI.pushEvent("HomePage");

        ImageView rollingImage = findViewById(R.id.rollingImage);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);

        if (!cleverTapAPI.isPushPermissionGranted()) {
            // Define Chelsea FC colors
            final int CHELSEA_BLUE = Color.parseColor("#034694"); // Chelsea blue
            final int WHITE = Color.parseColor("#FFFFFF"); // White
            final int BLACK = Color.parseColor("#000000"); // Black

            JSONObject jsonObject = CTLocalInApp.builder()
                    .setInAppType(CTLocalInApp.InAppType.ALERT)
                    .setTitleText("Get Notified")
                    .setMessageText("Please enable notifications on your device to use Push Notifications.")
                    .followDeviceOrientation(true)
                    .setPositiveBtnText("Allow")
                    .setNegativeBtnText("Cancel")
                    .setBackgroundColor("#FFFFFF") // Set background color to white
                    .setBtnBorderColor("#034694") // Set button border color to Chelsea blue
                    .setTitleTextColor("#034694") // Set title text color to Chelsea blue
                    .setMessageTextColor("#000000") // Set message text color to black
                    .setBtnTextColor("#FFFFFF") // Set button text color to white
                    .setImageUrl("https://i.ibb.co/ct1Mwnk/bell.png")
                    .setBtnBackgroundColor("#034694") // Set button background color to Chelsea blue
                    .build();
            cleverTapAPI.promptPushPrimer(jsonObject);

        }else {
            Toast.makeText(getApplicationContext(),"Notification Permission Already Given",Toast.LENGTH_SHORT).show();
        }

        //User Profiles
        findViewById(R.id.userProfilesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_UserProfile");
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        //Events
        findViewById(R.id.eventsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_Events");
                Intent intent = new Intent(MainActivity.this, EventsActivity.class);
                startActivity(intent);

            }
        });

        //Push
        findViewById(R.id.pushButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_Push");
                Intent intent = new Intent(MainActivity.this, PushActivity.class);
                startActivity(intent);
            }
        });

        //In App
        findViewById(R.id.inAppButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_InApp");
                Intent intent = new Intent(MainActivity.this, InAppActivity.class);
                startActivity(intent);
            }
        });

        //Native Display
        findViewById(R.id.nativeDisplayButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_Native");
                Intent intent = new Intent(MainActivity.this, NativeDisplayActivity.class);
                startActivity(intent);
            }
        });

        //App Inbox

        //Settings
        findViewById(R.id.settingsButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Home_Settings");
                Intent intent = new Intent(MainActivity.this, UpdateProfile.class);
                startActivity(intent);
            }
        });

    }
    public void inboxDidInitialize() {

        findViewById(R.id.appInboxButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Define the tab titles
                ArrayList<String> tabs = new ArrayList<>();
                tabs.add("Promotions");
                tabs.add("Offers");

                // Initialize the inbox style configuration
                CTInboxStyleConfig styleConfig = new CTInboxStyleConfig();
                styleConfig.setFirstTabTitle("All Inboxes");
                styleConfig.setTabs(tabs); // Set the tabs for the inbox

                // Set color configurations inspired by Chelsea FC
                styleConfig.setTabBackgroundColor("#034694"); // Chelsea blue for the background of tabs
                styleConfig.setSelectedTabIndicatorColor("#FFFFFF"); // White indicator for the selected tab
                styleConfig.setSelectedTabColor("#FFFFFF"); // White color for the selected tab text
                styleConfig.setUnselectedTabColor("#C4D8E2"); // Light blue-grey for unselected tab text
                styleConfig.setBackButtonColor("#034694"); // Chelsea blue for the back button
                styleConfig.setNavBarTitleColor("#FFFFFF"); // White color for the navigation bar title
                styleConfig.setNavBarTitle("Chelsea FC Inbox"); // Set navigation bar title
                styleConfig.setNavBarColor("#034694"); // Chelsea blue for the navigation bar background
                styleConfig.setInboxBackgroundColor("#E6EFF7"); // Very light blue for the inbox background

                // Display the app inbox with the configured style
                if (cleverTapAPI != null) {
                    cleverTapAPI.showAppInbox(styleConfig);
                }

            }
        });

    }
    @Override
    public void inboxMessagesDidUpdate() {
        // Handle inbox message updates if needed
    }

}