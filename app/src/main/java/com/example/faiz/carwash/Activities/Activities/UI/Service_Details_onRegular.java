package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.ServiceActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/22/2017.
 */

public class Service_Details_onRegular extends android.support.v4.app.Fragment {
    public Button invoice_btn;
    public ImageButton edit_detail_regular;

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button done_regular_service;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.service_detail_onregular,null);
        initializeView(view);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceActivity.getInstance().onBackPressed();
            }
        });


        edit_detail_regular = (ImageButton)view.findViewById(R.id.edit_detail_regular);


        done_regular_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        //    .addToBackStack(null)
                        .replace(R.id.service_container, new Service_Regular_Fragment()).commit();
            }
        });


        edit_detail_regular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                  //      .addToBackStack(null)
                        .replace(R.id.service_container, new Add_Services_Regular()).commit();
            }
        });

        return view;
    }

    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Service Details");

        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        done_regular_service = (Button)view.findViewById(R.id.done_regular_service);

    }
    @Override
    public void onResume() {
        super.onResume();
        ServiceActivity.doubleFlag = true;
        ServiceActivity.flag = true;
    }
}
