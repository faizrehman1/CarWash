package com.example.faiz.carwash.Activities.Activities.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.faiz.carwash.Activities.Activities.Model.UserModel;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_Activity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    public FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(Splash_Activity.this);


        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                mAuthListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        final FirebaseUser user = firebaseAuth.getCurrentUser();
                        if (user != null) {
                            // User is signed in
                            FirebaseHandler.getInstance().getUsersRef().child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot!=null){
                                        if(dataSnapshot.getValue()!=null){
                                            UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                            UserModel.getInstance(

                                                    userModel.getFname(),
                                                    userModel.getLname(),
                                                    userModel.getEmail(),
                                                    userModel.getPasss(),
                                                    userModel.getAddress(),
                                                    userModel.getUser_uid(),
                                                    userModel.getImageUrl()
                                            );

                                            openMainScreen();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });





                        } else {
                            // User is signed out
                            Intent intent = new Intent(Splash_Activity.this,LoginActivity.class);
                            overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                //add listener
                mAuth.addAuthStateListener(mAuthListener);

            }
        }, 3000);
    }

    private void openMainScreen() {
        Intent intent = new Intent(Splash_Activity.this,MainActivity.class);
        intent.putExtra("type","admin");
        overridePendingTransition(R.anim.slide_down,R.anim.slide_up);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
