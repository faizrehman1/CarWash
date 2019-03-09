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

import com.example.faiz.carwash.Activities.Activities.Activities.ServiceActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/21/2017.
 */

public class Add_Services_Regular extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button service_regular_details;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_service_regular,null);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Service");

        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceActivity.getInstance().onBackPressed();
            }
        });

        service_regular_details = (Button)view.findViewById(R.id.service_regular_details);

        service_regular_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                 //       .addToBackStack(null)
                        .replace(R.id.service_container, new Service_Details_onRegular()).commit();
            }
        });



        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ServiceActivity.doubleFlag = true;
        ServiceActivity.flag = true;
    }
}
