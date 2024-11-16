package com.example.ruben;

import android.app.Application;
import android.app.NotificationManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.clevertap.android.pushtemplates.PushTemplateNotificationHandler;
import com.clevertap.android.pushtemplates.TemplateRenderer;
import com.clevertap.android.sdk.ActivityLifecycleCallback;
import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.android.sdk.interfaces.NotificationHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyApplication extends Application {

    private static final String TAG = MyApplication.class.getName();
    private static MyApplication singleton;

    private CleverTapAPI clevertapDefaultInstance;

    public static MyApplication getInstance() {
        return singleton;
    }

    @Override
    public void onCreate() {
        ActivityLifecycleCallback.register(this);
        super.onCreate();

        singleton = this;
        // Required initialization logic here!
        clevertapDefaultInstance = CleverTapAPI.getDefaultInstance(getApplicationContext());

        CleverTapAPI.createNotificationChannel(getApplicationContext(),"TAM-RO","Rubenico","Test Channel", NotificationManager.IMPORTANCE_MAX,true);
        CleverTapAPI.setNotificationHandler((NotificationHandler)new PushTemplateNotificationHandler());

        CleverTapAPI.setDebugLevel(CleverTapAPI.LogLevel.VERBOSE);
        TemplateRenderer.setDebugLevel(3);

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.v("TAG", "token: " + token);
                        clevertapDefaultInstance.pushFcmRegistrationId(token, true);
                        // Log and toast
//                        String msg = getString(R.string.msg_token_fmt, token);
//                        Log.d(TAG, msg);
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public CleverTapAPI getClevertapDefaultInstance() {
        return clevertapDefaultInstance;
    }
}
