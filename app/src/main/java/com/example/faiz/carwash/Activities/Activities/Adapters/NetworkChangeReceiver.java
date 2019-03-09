package com.example.faiz.carwash.Activities.Activities.Adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by AST on 11/21/2017.
 */

public abstract class NetworkChangeReceiver extends BroadcastReceiver {

    public boolean isConnected = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("Reciever", "Receieved notification about network status");
        isNetworkAvailable(context);
    }
    protected abstract boolean isNetworkAvailable(Context context);

}
