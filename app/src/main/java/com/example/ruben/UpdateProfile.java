package com.example.ruben;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.sdk.CleverTapAPI;

import java.io.IOException;
import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {
    Switch switchPush, switchEmail, switchSMS, switchWhatsApp;

    boolean isSmsChecked, isEmailChecked, isWhatsAppChecked, isPushEnabled;

    CleverTapAPI clevertapDefaultInstance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);
        clevertapDefaultInstance.enablePersonalization();

        ImageView rollingImage = findViewById(R.id.rollingImage);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);

        switchPush = findViewById(R.id.switch_push_notification);
        switchEmail = findViewById(R.id.switch_emails);
        switchSMS = findViewById(R.id.switch_sms);
        switchWhatsApp = findViewById(R.id.switch_whatsapp);

        switchEmail.setEnabled(false);
        switchSMS.setEnabled(false);
        switchWhatsApp.setEnabled(false);

        // Retrieve stored values from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
        String storedEmail = sharedPreferences.getString("Email", "");
        String storedPhone = sharedPreferences.getString("Phone", "");




        if(!storedEmail.isEmpty() & !storedEmail.equals(null)){
            switchEmail.setEnabled(true);
        }
        if(!storedPhone.isEmpty() & !storedPhone.equals(null)){
            switchSMS.setEnabled(true);
            switchWhatsApp.setEnabled(true);
        }

        Boolean enablePush = (Boolean) clevertapDefaultInstance.getProperty("MSG-push");
        Boolean enableSMS = (Boolean) clevertapDefaultInstance.getProperty("MSG-sms");
        Boolean enableEmail = (Boolean) clevertapDefaultInstance.getProperty("MSG-email");
        Boolean enableWA = (Boolean) clevertapDefaultInstance.getProperty("MSG-whatsapp");
        Log.v("CleverTap Enable", enableWA + " " + enableSMS + " " + enableEmail);

        if (enablePush) {
            switchPush.setChecked(true);
        } else {
            switchPush.setChecked(false);
        }

        if(enableSMS) {
            switchSMS.setChecked(true);
        }
        else{
            switchSMS.setChecked(false);
        }

        if(enableEmail){
            switchEmail.setChecked(true);
        }
        else{
            switchEmail.setChecked(false);
        }

        if(enableWA){
            switchWhatsApp.setChecked(true);
        }
        else{
            switchWhatsApp.setChecked(false);
        }

        switchPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchPush.isChecked()){
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-push", true);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                    Toast.makeText(UpdateProfile.this, "Push Notification is allowed", Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-push", false);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                }
            }
        });

        switchEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchEmail.isChecked()){
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-email", true);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                    Toast.makeText(UpdateProfile.this, "Email is allowed", Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-email", false);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                }
            }
        });

        switchSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchSMS.isChecked()){
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-sms", true);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                    Toast.makeText(UpdateProfile.this, "SMS is allowed", Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-sms", false);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                }
            }
        });

        switchWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switchWhatsApp.isChecked()){
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-whatsapp", true);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                    Toast.makeText(UpdateProfile.this, "Whatsapp Communication is allowed", Toast.LENGTH_LONG).show();
                }
                else{
                    HashMap<String, Object> profileUpdate = new HashMap<>();
                    profileUpdate.put("MSG-whatsapp", false);
                    clevertapDefaultInstance.pushProfile(profileUpdate);
                }
            }
        });

    }
}