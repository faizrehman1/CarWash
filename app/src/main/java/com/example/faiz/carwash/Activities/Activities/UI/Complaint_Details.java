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

import com.example.faiz.carwash.Activities.Activities.Activities.ComplaintActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/21/2017.
 */

public class Complaint_Details extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button submit_complaint;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.complaint_details,null);



        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Complaint Details");
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        submit_complaint = (Button)view.findViewById(R.id.submit_complaint);


        submit_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.complaint_container, new Complaints_Fragment()).commit();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplaintActivity.getInstance().onBackPressed();

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ComplaintActivity.doubleFlag = true;
        ComplaintActivity.flag = true;
    }
}
