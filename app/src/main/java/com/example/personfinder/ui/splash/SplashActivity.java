package com.example.personfinder.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.PreferencesHandler;
import com.example.personfinder.R;
import com.example.personfinder.ui.main_activity.MainActivity;
import com.example.personfinder.ui.sign_in.SignInActivity;

public class SplashActivity extends AppCompatActivity {

    private PreferencesHandler preferencesHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        preferencesHandler = new PreferencesHandler(SplashActivity.this);

        navigateToSignInActivity();
    }

    private void navigateToSignInActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
                if (preferencesHandler.getLoginstatus() == true) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                }
                finish();

            }
        }, 1500);
    }
}
