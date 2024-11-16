package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView rollingImage = findViewById(R.id.rollingImage);

        Animation scaleDownAndMoveLeft = AnimationUtils.loadAnimation(this, R.anim.scale_down_and_move_left);
        final Animation rollingAndMovingRight = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving_right);

        scaleDownAndMoveLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                rollingImage.startAnimation(rollingAndMovingRight);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        rollingAndMovingRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });

        rollingImage.startAnimation(scaleDownAndMoveLeft);
    }
}