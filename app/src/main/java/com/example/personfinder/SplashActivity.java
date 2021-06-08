package com.example.personfinder;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personfinder.GeneralClasses.PreferencesHandler;

public class SplashActivity extends AppCompatActivity {

    private PreferencesHandler preferencesHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_content_splash);
        preferencesHandler = new PreferencesHandler(SplashActivity.this);

        navigateToSignInActivity();
    }

    private void navigateToSignInActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
//                if (preferencesHandler.getLoginstatus() == true) {
//                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
//                    finish();
//                }else{
//                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
//                    finish();
//                }

            }
        }, 1500);
    }
}
