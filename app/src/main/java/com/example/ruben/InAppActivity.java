package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.clevertap.android.sdk.CleverTapAPI;

public class InAppActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app);

        ImageView rollingImage = findViewById(R.id.rollingImage);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);

        CleverTapAPI cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.DEBUG);

        cleverTapAPI.pushEvent("In App");

        findViewById(R.id.btnCover).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Cover In App");
            }
        });

        findViewById(R.id.btnHalfInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Half Interstitial In App");
            }
        });

        findViewById(R.id.btnInterstitial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Interstitial In App");
            }
        });

        findViewById(R.id.btnHeader).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Header In App");
            }
        });

        findViewById(R.id.btnFooter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Footer In App");
            }
        });

        findViewById(R.id.btnAlert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Alert In App");
            }
        });

        findViewById(R.id.btnNPS).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("NPS In App");
            }
        });

        findViewById(R.id.btnLeadGeneration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cleverTapAPI.pushEvent("Lead Generation In App");
            }
        });
    }
}