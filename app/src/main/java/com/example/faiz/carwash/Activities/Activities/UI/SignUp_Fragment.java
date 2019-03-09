package com.example.faiz.carwash.Activities.Activities.UI;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faiz.carwash.Activities.Activities.Model.UserModel;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by AST on 9/28/2017.
 */

public class SignUp_Fragment extends android.support.v4.app.Fragment {

    public TextView back_to_login;
    public Button sign_up;
    private FirebaseAuth mAuth;
    private FirebaseUser firebase_user;
    private DatabaseReference firebase;
    public EditText email,password,fname,lname,address_user;
    public String imageUrl="";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.signup_view,null);
        back_to_login = (TextView)view.findViewById(R.id.back_to_login);
        sign_up = (Button)view.findViewById(R.id.sign_up);
        mAuth = FirebaseAuth.getInstance();
        firebase = FirebaseDatabase.getInstance().getReference();
        email = (EditText) view.findViewById(R.id.editText_email);
        password = (EditText) view.findViewById(R.id.editText_password);
        fname = (EditText) view.findViewById(R.id.editText_fname);
        lname = (EditText) view.findViewById(R.id.editText_lname);
        address_user = (EditText)view.findViewById(R.id.address_user);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String pass = password.getText().toString();

                if (pass.length() <= 6) {
                    main(pass);
                }else if(( fname.getText().toString().equals("")
                        || lname.getText().toString().equals("")
                        || address_user.getText().toString().equals("")
                        || pass.equals(""))){
                    ///    || confrim_passwordd.equals("")) ){
                    Toast.makeText(getActivity(),"Fields Should not be left Empty",Toast.LENGTH_SHORT).show();

                }
            //    else if(email.getText().length()==0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches() ){
           //         email.setError("Enter Valid Email Address");
            //    }
                else if(fname.getText().length()== 0 || !fname.getText().toString().matches("[a-zA-Z ]+")){
                    fname.setError("Invalid Name");
                }
                else if(lname.getText().length() == 0 || !lname.getText().toString().matches("[a-zA-Z ]+")) {
                    lname.setError("Invalid Name");
                }
                //Checking the length of pasword while registering new USER;
                else if (pass.length() <= 6) {
                    main(pass);
                }else{

                    String emailTemp = email.getText().toString();
                    emailTemp = emailTemp+"@carwash.com";

                    try {
                        final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "Sign Up", "Connecting...", true, false);

                        mAuth.createUserWithEmailAndPassword(emailTemp,pass).addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if(task.isSuccessful()){
                                        String uid = mAuth.getCurrentUser().getUid();

                                        firebase.child("users").child(uid).setValue(new UserModel(fname.getText().toString(),lname.getText().toString(),email.getText().toString(),pass,address_user.getText().toString(),uid,imageUrl));

                                        progressDialog.dismiss();
                                        Toast.makeText(getActivity(), "Successfull", Toast.LENGTH_SHORT).show();
                                        AppLogs.logd("createUserWithEmail:onComplete: " + task.isSuccessful());
                                        if(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer) != null) {
                                            getActivity().getSupportFragmentManager()
                                                    .beginTransaction().
                                                    remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer)).commit();
                                        }
                                                } else
                                        if (!task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), " " + task.getException(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                AppLogs.d("FailureSignup",e.getMessage());

                            }
                        });

                    } catch (Exception ex) {

                        ex.printStackTrace();
                    }
                }

            }
        });

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer) != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                            .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.termcontainer)).commit();
                }
            }
        });

        return view;
    }
    private void main(String pass) {

        Toast.makeText(getActivity(), pass + "\nYou Password is no longer Stronger \nPlease signup Again with \natleast 7 Character of Pasword.\nThanks ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
