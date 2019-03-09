package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.TransactionActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/16/2017.
 */

public class Transaction_Fragment extends android.support.v4.app.Fragment {


    public static TextView ActionBartitle;
    public ImageView back_arrow;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transaction_fragment,null);




        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            Window window = getActivity().getWindow();
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));

//            mayRequestContacts();
//
        }

        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);


        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Transaction");

        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionActivity.getInstance().onBackPressed();

            }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        TransactionActivity.doubleFlag = true;
        TransactionActivity.flag  = true;
    }
}
