package com.example.ruben;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.displayunits.DisplayUnitListener;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnit;
import com.clevertap.android.sdk.displayunits.model.CleverTapDisplayUnitContent;

import java.util.ArrayList;

public class NativeDisplayActivity extends AppCompatActivity implements DisplayUnitListener {
    CleverTapAPI cleverTapAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_display2);

        cleverTapAPI = CleverTapAPI.getDefaultInstance(getApplicationContext());
        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        //CleverTapAPI.getDefaultInstance(this).getAllDisplayUnits();
        cleverTapAPI.setDisplayUnitListener(this);

        cleverTapAPI.pushEvent("Native Display");

    }

    @Override
    public void onDisplayUnitsLoaded(ArrayList<CleverTapDisplayUnit> units) {
        for (int i = 0; i <units.size() ; i++) {
            CleverTapDisplayUnit unit = units.get(i);
            prepareDisplayView(unit);
        }
    }

    private void prepareDisplayView(CleverTapDisplayUnit unit) {
        LinearLayout parentLayout = findViewById(R.id.linear);
        int imgCount = 0;
        parentLayout.removeAllViews(); // Clear previous views if any

        for (CleverTapDisplayUnitContent contentItem : unit.getContents()) {
            if(imgCount >=3) break;

            String media = contentItem.getMedia();
            String mediaURL = contentItem.getActionUrl();
            System.out.println("Media: " + media);

            ImageView imageView = new ImageView(this);
            Glide.with(this).load(media).into(imageView);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
            );

            int margin = (int) (2 * getResources().getDisplayMetrics().density); // Convert 8dp to pixels
            layoutParams.setMargins(margin, margin, margin, margin);

            imageView.setOnClickListener(view -> {
                // Open the URL in a browser
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mediaURL));
                startActivity(browserIntent);
            });

            imageView.setLayoutParams(layoutParams);
            parentLayout.addView(imageView);

            imgCount++;
        }
    }
}