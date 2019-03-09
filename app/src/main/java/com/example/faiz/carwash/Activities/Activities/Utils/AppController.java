package com.example.faiz.carwash.Activities.Activities.Utils;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.google.firebase.FirebaseApp;

/**
 * Created by AST on 9/28/2017.
 */

public class AppController extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseApp.initializeApp(this);

        context = this.getApplicationContext();

    }

    public static Context getContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
