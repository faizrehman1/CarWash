package com.example.faiz.carwash.Activities.Activities.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faiz.carwash.Activities.Activities.Adapters.NetworkChangeReceiver;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.UserModel;
import com.example.faiz.carwash.Activities.Activities.UI.Password_Recovery;
import com.example.faiz.carwash.Activities.Activities.UI.SignUp_Fragment;
import com.example.faiz.carwash.Activities.Activities.UI.Terms_and_Condition;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.Activities.Activities.Utils.SharedPref;
import com.example.faiz.carwash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    public Button btnLogin;
    public CheckBox termcheck;
    public EditText editTextNumber;
    public TextInputEditText user_pass;
    public TextView forget_password;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    public TextView signup_text;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public FrameLayout termcontainer;
    public static boolean flag =  true;
    public static boolean doubleFlag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Window window = LoginActivity.this.getWindow();
            window.setStatusBarColor(ContextCompat.getColor(LoginActivity.this, R.color.colorWhite));

//            mayRequestContacts();
//
        }
        editTextNumber = (EditText) findViewById(R.id.mobile_text);
        user_pass = (TextInputEditText) findViewById(R.id.user_pass);
        forget_password = (TextView) findViewById(R.id.forget_password);
        btnLogin = (Button) findViewById(R.id.login_btn);
        termcheck = (CheckBox) findViewById(R.id.termcheck);
        //  FirebaseApp.initializeApp(LoginActivity.this);
        termcontainer = (FrameLayout)findViewById(R.id.termcontainer);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        signup_text = (TextView) findViewById(R.id.signup_text);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            mayRequestContacts();
//
        }

        if(!SharedPref.getCurrentUser(LoginActivity.this).getCust_apartment().equals("")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("type","user");
            intent.putExtra("cust",SharedPref.getCurrentUser(LoginActivity.this));
          //  SharedPref.setCurrentUser(LoginActivity.this,add_customer_object);
            overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        }

        // Internet Connectivity

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
                                                .make(termcontainer, "Internet Connected", Snackbar.LENGTH_LONG);

                                        snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorWhite));
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
                                            .make(termcontainer, "No internet connection!", Snackbar.LENGTH_INDEFINITE);
                                    ;
                                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(LoginActivity.this, R.color.colorWhite));
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
        //End Connectivity


        forget_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.termcontainer, new Password_Recovery()).commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final boolean[] flag = {true};
                if(editTextNumber.getText().toString().length() <10 || editTextNumber.getText().toString().length() > 10){
                    editTextNumber.setError("Please enter valid Mobile Number");
                    return;
                }

                final String emails = editTextNumber.getText().toString()+"@carwash.com";
                final String passo = user_pass.getText().toString();
                final Add_Customer_Object[] add_customer_object = new Add_Customer_Object[1];
                if (editTextNumber.getText().toString().length() == 0 && !android.util.Patterns.EMAIL_ADDRESS.matcher(editTextNumber.getText().toString()).matches()) {
                    editTextNumber.setError("This is Required Field");
                 //   progressDialog.dismiss();
                } else if (passo.length() == 0 && passo.length() <= 6) {
                    user_pass.setError("This is Required Field");
                  //  progressDialog.dismiss();
                } else if(!termcheck.isChecked()) {
                    Snackbar.make(view,"Accept Terms and Conditions",Snackbar.LENGTH_LONG).show();
                }else{

                    FirebaseHandler.getInstance().getAdd_customer()
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot != null) {
                                        if (dataSnapshot.getValue() != null) {
                                            AppLogs.d("TAG", dataSnapshot.getValue().toString());
                                            for(DataSnapshot data:dataSnapshot.getChildren()){
                                                Add_Customer_Object customer_object = data.getValue(Add_Customer_Object.class);
                                                if(customer_object.getCust_mobile().equals(editTextNumber.getText().toString()) && passo.matches("carwash")){
                                                    flag[0] = false;
                                                    add_customer_object[0] = customer_object;
                                                }
                                            }
                                            if(flag[0]){
                                                ownerLogin(emails,passo,flag[0]);
                                            }else{
                                                custLogin(emails,passo,flag[0],add_customer_object[0]);
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });




                }



            }
        });

        termcheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.termcontainer, new Terms_and_Condition()).commit();
                } else {

                }
            }
        });

        signup_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.termcontainer, new SignUp_Fragment()).commit();
            }
        });


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) {
                    currentUser.getUid();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    if(currentUser.getEmail().equals("0900789601@carwash.com")){
                        intent.putExtra("type","admin");
//                    }else{
//                        intent.putExtra("type","user");
//                    }
                    overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                    startActivity(intent);

                    overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
                    finish();
                } else

                {
                    AppLogs.loge("Auth Listener: User Not Signed In");
                }


            }

        };



    }

    private void custLogin(String emails, String passo, boolean b, Add_Customer_Object add_customer_object) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("type","user");
        intent.putExtra("cust",add_customer_object);
        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
        SharedPref.setCurrentUser(LoginActivity.this,add_customer_object);
        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);
        finish();

    }

    private void ownerLogin(String emails, String passo, boolean b) {
        final ProgressDialog progressDialog = ProgressDialog.show(LoginActivity.this, "Sign In", "Connecting...", true, false);

        try {

            mAuth.signInWithEmailAndPassword(emails, passo).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        AppLogs.logd("signInWithEmail:onComplete:" + task.isSuccessful());
                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        ///      openMainScreen();
                        progressDialog.dismiss();

                        FirebaseHandler.getInstance().getUsersRef().child(task.getResult().getUser().getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot != null) {
                                    if (dataSnapshot.getValue() != null) {
                                        UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                        UserModel userModel1 = new UserModel(userModel.getFname(),userModel.getLname(),userModel.getEmail(),userModel.getPasss(),userModel.getAddress(),userModel.getUser_uid(),userModel.getImageUrl());
                                               userModel.myObj = userModel1;

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    } else if (!task.isSuccessful()) {
                        progressDialog.dismiss();
                        AppLogs.logw("signInWithEmail" + task.getException());
                        Toast.makeText(LoginActivity.this, "" + task.getException(),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });


        } catch (Exception ex) {
            ex.printStackTrace();
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}