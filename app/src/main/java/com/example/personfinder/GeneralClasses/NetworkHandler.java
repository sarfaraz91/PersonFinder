package com.example.personfinder.GeneralClasses;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class NetworkHandler {
    private static final String TAG = "NetworkHandler";
    private static final int RETRY_TIME = 5000;
    private static NetworkHandler handler;

    static {
        getInstance();
    }

    public static NetworkHandler getInstance() {
        return handler != null ? handler : (handler = new NetworkHandler());
    }

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) PersonFinderApplication.getCtx().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }

}
