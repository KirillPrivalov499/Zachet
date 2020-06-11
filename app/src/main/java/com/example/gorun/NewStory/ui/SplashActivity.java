package com.example.gorun.NewStory.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.gorun.ChatTutorial.MainActivity;
import com.example.gorun.NewStory.NotificationService;
import com.example.gorun.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_DURATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if (FirebaseAuth.getInstance().getCurrentUser() != null){
                    Intent mainIntent = new Intent(SplashActivity.this, NavigationActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    SplashActivity.this.startActivity(mainIntent);
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_DURATION);
    }


}
