package com.example.personfinder.GeneralClasses;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

public class PersonFinderApplication extends Application {

    public static PersonFinderApplication instance;

    public static Context getCtx() {
        return instance.getApplicationContext();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onCreate(base));
        MultiDex.install(this);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //TODO added below line 4 DEC
        newConfig.setLocale(newConfig.locale);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        new WebView(this).destroy();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        Log.e("OnTerminate", "OnTerminate");
    }
}