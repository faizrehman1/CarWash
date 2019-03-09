package com.example.faiz.carwash.Activities.Activities.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Adapters.Navigations_ItemsAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.NetworkChangeReceiver;
import com.example.faiz.carwash.Activities.Activities.UI.Change_Password;
import com.example.faiz.carwash.Activities.Activities.UI.Owner_Home;
import com.example.faiz.carwash.Activities.Activities.UI.Send_Message;
import com.example.faiz.carwash.Activities.Activities.UI.Terms_and_Condition;
import com.example.faiz.carwash.R;
import com.google.firebase.auth.FirebaseAuth;

public class OwnerActivity extends AppCompatActivity {


    public static OwnerActivity ownerActivity;
    public DrawerLayout drawer_layout;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public String[] menuName= {"Change Password","Terms & Conditions","Send Messages","Add Customer","Pay Bill Online","Logout"};
    public int[] menuIcons = {R.mipmap.change_password,R.mipmap.menu_terms_and_conditions,R.mipmap.send_message,R.mipmap.add_customer,R.mipmap.pay_bill,R.mipmap.add_customer};
    public FrameLayout owner_container;


    public static OwnerActivity getInstance(){
        return ownerActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner);
        owner_container = (FrameLayout)findViewById(R.id.owner_container);
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        View viewinflate = OwnerActivity.this.getLayoutInflater().inflate(R.layout.nav_header_main,null);
        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);

        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(OwnerActivity.this,menuName,menuIcons);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        mDrawerToggle = new ActionBarDrawerToggle(OwnerActivity.this, drawer_layout,null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                owner_container.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        //     .setCustomAnimations(R.anim.fade_in, R.anim.fade_out);
        // transaction.addToBackStack(null);
        transaction.add(R.id.owner_container, new Owner_Home());
        transaction.commit();

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
                                if(!isConnected){
                                    Log.v("Reciever", "Now you are connected to Internet!");
                                    // networkStatus.setText("Now you are connected to Internet!");
//                                Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
                                    Snackbar snackbar = Snackbar
                                            .make(owner_container, "Internet Connected", Snackbar.LENGTH_LONG);

                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(OwnerActivity.this, R.color.colorWhite));
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

                                    return true;
                                }
                            }else{
                                Snackbar snackbar = Snackbar
                                        .make(owner_container, "No internet connection!", Snackbar.LENGTH_INDEFINITE);
                                ;
                                snackbar.getView().setBackgroundColor(ContextCompat.getColor(OwnerActivity.this, R.color.colorWhite));
                                snackbar.setDuration(800000);
// Changing action button text color
                                View sbView = snackbar.getView();
                                TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                                textView.setTextColor(Color.parseColor("#ffffff"));
                                snackbar.show();
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

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.owner_container, new Change_Password()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }

                if(i==2){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.owner_container, new Terms_and_Condition()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==4){
//                    getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                            .addToBackStack(null)
//                            .add(R.id.owner_container, new Add_Customer()).commit();

                    Intent intent = new Intent(OwnerActivity.this, CustomerActivity.class);
                    intent.putExtra("key","add");
                    overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==3){
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.owner_container, new Send_Message()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==6){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(OwnerActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });

    }
}
