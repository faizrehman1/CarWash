package com.example.faiz.carwash.Activities.Activities.UI;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/16/2017.
 */

public class Change_Password extends android.support.v4.app.Fragment {


    public ImageView back_change_pass;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.change_password,null);
        back_change_pass = (ImageView)view.findViewById(R.id.back_change_pass);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.TRANSPARENT);

//            mayRequestContacts();
//
        }



        back_change_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main) != null) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                            .remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main)).commit();
                }

                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                Window window = getActivity().getWindow();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.colorWhite));
                }

            }
        });


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
