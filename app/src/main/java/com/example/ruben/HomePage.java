package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.sdk.CTInboxListener;
import com.clevertap.android.sdk.CTInboxStyleConfig;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.interfaces.NotificationHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class HomePage extends AppCompatActivity{
    CleverTapAPI clevertapDefaultInstance;
    private Button loginButton;
    private Button updateProfileButton;
    private Button confirmLoginButton;
    private Button updateButton;
    private EditText usernameEditText;
    private EditText passwordEditText;

    private EditText name, email, phone, gender, dob;
    private LinearLayout loginFields, loginImage;
    private LinearLayout updateProfileFields, updateProfileImage;
    private String identity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        clevertapDefaultInstance.pushEvent("User Profile");

        ImageView rollingImage = findViewById(R.id.rollingCT);
        Animation rollingAnimation = AnimationUtils.loadAnimation(this, R.anim.rolling_and_moving);
        rollingImage.startAnimation(rollingAnimation);

        loginButton = findViewById(R.id.loginButton);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        confirmLoginButton = findViewById(R.id.confirmLoginButton);
        updateButton = findViewById(R.id.updateButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginFields = findViewById(R.id.loginFields);
        loginImage = findViewById(R.id.linearLayoutLoginImage);
        updateProfileFields = findViewById(R.id.updateProfileFields);
        updateProfileImage = findViewById(R.id.linearLayoutUpdateImage);
        identity = usernameEditText.getText().toString();

        name = findViewById(R.id.nameEditText);
        email = findViewById(R.id.emailEditText);
        phone = findViewById(R.id.phoneEditText);
        gender = findViewById(R.id.genderEditText);
        dob = findViewById(R.id.dobEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFields.setVisibility(View.VISIBLE);
                loginImage.setVisibility(View.VISIBLE);
            }
        });

        confirmLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()) {

                    HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                    profileUpdate.put("Username", usernameEditText.getText().toString());    // String
                    profileUpdate.put("Identity", usernameEditText.getText().toString());      // String or number
                    clevertapDefaultInstance.onUserLogin(profileUpdate);

                    Toast.makeText(HomePage.this, "You have successfully logged into CleverTap dashboard", Toast.LENGTH_SHORT).show();

                    updateProfileButton.setEnabled(true);
                    updateProfileButton.setAlpha(1.0f);
                }
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFields.setVisibility(View.GONE);
                updateProfileFields.setVisibility(View.VISIBLE);
                updateProfileImage.setVisibility(View.VISIBLE);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String DOB = dob.getText().toString();

                HashMap<String, Object> profileUpdate = new HashMap<String, Object>();
                profileUpdate.put("Name", name.getText().toString());                  // String
                profileUpdate.put("Identity", identity);                    // String or number
                profileUpdate.put("Email", email.getText().toString());               // Email address of the user
                profileUpdate.put("Phone", "+91" + phone.getText().toString());                 // Phone (with the country code, starting with +)
                if(gender.getText().toString().toLowerCase() == "male")
                {profileUpdate.put("Gender", "M");} // Can be either M or F
                else{profileUpdate.put("Gender", "F");}
                profileUpdate.put("DOB", new Date(DOB));

                clevertapDefaultInstance.pushProfile(profileUpdate);

                Toast.makeText(HomePage.this, "You have successfully updated your profile on CleverTap dashboard", Toast.LENGTH_SHORT).show();

                // Save the email and phone number to SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("UserProfile", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("Email", email.getText().toString());
                editor.putString("Phone", "+91" + phone.getText().toString());
                editor.apply();
            }
        });


    }
}