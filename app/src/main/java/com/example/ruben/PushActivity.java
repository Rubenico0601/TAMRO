package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

public class PushActivity extends AppCompatActivity {

    CleverTapAPI cleverTapAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

        ImageView rollingImage = findViewById(R.id.rollingImage);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);


        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);

        cleverTapAPI.pushEvent("Push");

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"TAM-RO","TAM-RO","Your Channel Description", NotificationManager.IMPORTANCE_MAX,true);
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());

        findViewById(R.id.btnBasic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Basic Template");
            }
        });

        findViewById(R.id.btnAuto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Auto Carousel");
            }
        });

        findViewById(R.id.btnManual).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Manual Carousel");
            }
        });

        findViewById(R.id.btnRating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Rating Template");
            }
        });

        findViewById(R.id.btnFive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Five Icons");
            }
        });

        findViewById(R.id.btnTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Timer Template");
            }
        });

        findViewById(R.id.btnZero).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Zero Bezel");
            }
        });

        findViewById(R.id.btnInputBox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Input Box");
            }
        });
    }
}