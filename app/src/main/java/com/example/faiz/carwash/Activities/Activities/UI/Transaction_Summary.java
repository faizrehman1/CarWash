package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.TransactionActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/16/2017.
 */

public class Transaction_Summary extends android.support.v4.app.Fragment {


    public Button show_Detail;
    public static TextView ActionBartitle;
    public ImageView back_arrow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.transaction_summary,null);

        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
      //  getSupportActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        show_Detail = (Button)view.findViewById(R.id.show_detail);
        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Transaction Summary");

        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransactionActivity.getInstance().onBackPressed();

            }
        });


        show_Detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.transaction_container, new Transaction_Fragment()).commit();
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
