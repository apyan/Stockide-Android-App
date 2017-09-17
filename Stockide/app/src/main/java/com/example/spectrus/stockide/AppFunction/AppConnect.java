package com.example.spectrus.stockide.AppFunction;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class is used for connections
 * with the Internet.
 */

public class AppConnect {

    // Connect Variables
    Context context;

    // Constructor
    public AppConnect (Context eContext) {
        context = eContext;
    }

    // Check Internet Connectivity (Version 1)
    public boolean connectivityExist(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    // Checking for Internet Connectivity (Version 2)
    public boolean connectionAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return ((activeNetworkInfo != null) && (activeNetworkInfo.isConnected()));
    }
}
