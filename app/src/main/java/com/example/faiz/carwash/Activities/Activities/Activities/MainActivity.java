package com.example.faiz.carwash.Activities.Activities.Activities;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Adapters.NetworkChangeReceiver;
import com.example.faiz.carwash.Activities.Activities.UI.Customer_home;
import com.example.faiz.carwash.Activities.Activities.UI.Owner_Home;
import com.example.faiz.carwash.R;

public class MainActivity extends AppCompatActivity {

    public static MainActivity mainActivity;
    public FrameLayout main_frame;
    public static boolean flag =  true;
    public static boolean doubleFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = this;
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Window window = MainActivity.this.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

//            mayRequestContacts();
//
        }
        main_frame = (FrameLayout)findViewById(R.id.container_main);


        Owner_Home owner_home = new Owner_Home();

        String user_type = getIntent().getStringExtra("type");


            if (user_type.equals("user")) {

                Customer_home customer_home = new Customer_home();
                Bundle bundle = new Bundle();
                bundle.putParcelable("cust", getIntent().getParcelableExtra("cust"));
                customer_home.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.container_main, customer_home);
                transaction.commit();


            } else if (user_type.equals("admin")) {
                FragmentTransaction transaction = getSupportFragmentManager()
                        .beginTransaction();
                //   .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
                transaction.replace(R.id.container_main, owner_home);
                transaction.commit();

            }


        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        NetworkChangeReceiver receiver = new NetworkChangeReceiver(){
            @Override
            public boolean isNetworkAvailable(Context context) {
                ConnectivityManager connectivity = (ConnectivityManager)
                        context.getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivity != null) {
                    NetworkInfo[] info = connectivity.getAllNetworkInfo();
                    if (info != null) {
                        for (int i = 0; i < info.length; i++) {
                            if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                                if(!isConnected) {
                                    if (!flag) {
                                        Log.v("Reciever", "Now you are connected to Internet!");
                                        // networkStatus.setText("Now you are connected to Internet!");
//                                Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
                                        Snackbar snackbar = Snackbar
                                                .make(main_frame, "Internet Connected", Snackbar.LENGTH_LONG);

                                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
                                        ///   snackbar.setDuration(8000);
// Changing action button text color
                                        View sbView = snackbar.getView();
                                        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                        textView.setTextColor(Color.parseColor("#ffffff"));
                                        snackbar.show();
                                        isConnected = true;
                                        //do your processing here ---
                                        //if you need to post any data to the server or get status
                                        //update from the server


                                    }else{
                                        flag = false;
                                        //  return true;
                                    }
                                    return true;
                                }
                            }else {
                                if (!doubleFlag) {
                                    Snackbar snackbar = Snackbar
                                            .make(main_frame, "No internet connection!", Snackbar.LENGTH_INDEFINITE);
                                    ;
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));
                                    snackbar.setDuration(800000);
// Changing action button text color
                                    View sbView = snackbar.getView();
                                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                    textView.setTextColor(Color.parseColor("#ffffff"));
                                    snackbar.show();
                                }else{
                                    doubleFlag = false;
                                }
                            }
                        }
                    }
                }
                Log.v("Reciever", "You are not connected to Internet!");
                //networkStatus.setText("You are not connected to Internet!");
//            Toast.makeText(context, "DISCONNECT", Toast.LENGTH_SHORT).show();


                isConnected = false;
                return false;
            }

        };
        registerReceiver(receiver, filter);



    }

    public static MainActivity getInstance(){
        return mainActivity;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Window window = MainActivity.this.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorWhite));

//            mayRequestContacts();
//
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
