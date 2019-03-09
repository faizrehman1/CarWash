package com.example.faiz.carwash.Activities.Activities.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.ServiceActivity;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/21/2017.
 */

public class Service_Regular_Fragment extends android.support.v4.app.Fragment {


    public ImageButton floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public View clist_Back_view;
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button btnOndemand;
    public Dialog filter_Regular_Service;
    public Button apply_dialog_r_service,cancle_dialog_r_service;
    public EditText datefrom_r_service,dateto_r_service;
    public Spinner supervisor_r_service,service_status_r_service,service_type_r_service,appart_r_service,bloc_r_service,boy_r_service;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.service_onregular, null);
        initializeView(view);


        btnOndemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   customAnimations();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                      //  .addToBackStack(null)
                        .replace(R.id.service_container, new Service_OnDemand_Fragment()).commit();
            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceActivity.getInstance().onBackPressed();

            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();


            }
        });

        floatingMenuItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                animations();
                customAnimations();
                filter_Regular_Service.show();

            }
        });
        floatingMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                animations();
                customAnimations();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.service_container, new Add_Services_Regular()).commit();
            }
        });

        return view;
    }

    private void initializeView(View view) {
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.filter_dialog_r_service, null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Services");
        btnOndemand = (Button)view.findViewById(R.id.on_demand);
        clist_Back_view = (View) view.findViewById(R.id.clist_back_view);
        floatingActionButton = (ImageButton) view.findViewById(R.id.service_list_sort);
        floatingMenuItem1 = (ImageView) view.findViewById(R.id.floatingMenuItem1);
        floatingMenuItem2 = (ImageView) view.findViewById(R.id.floatingMenuItem2);
        filter_Regular_Service = new Dialog(getActivity());
        filter_Regular_Service.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        filter_Regular_Service.setContentView(completeView);

        datefrom_r_service = (EditText)completeView.findViewById(R.id.datefrom_r_service);
        dateto_r_service = (EditText)completeView.findViewById(R.id.dateto_r_service);
        appart_r_service = (Spinner)completeView.findViewById(R.id.appart_r_service);
        bloc_r_service = (Spinner)completeView.findViewById(R.id.bloc_r_service);
        supervisor_r_service = (Spinner)completeView.findViewById(R.id.supervisor_r_service);
        boy_r_service = (Spinner)completeView.findViewById(R.id.boy_r_service);
        service_status_r_service = (Spinner)completeView.findViewById(R.id.service_status_r_service);
        service_type_r_service = (Spinner)completeView.findViewById(R.id.service_type_r_service);
        cancle_dialog_r_service = (Button)completeView.findViewById(R.id.cancle_dialog_r_service);
        apply_dialog_r_service = (Button)completeView.findViewById(R.id.apply_dialog_r_service);


        cancle_dialog_r_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filter_Regular_Service.dismiss();
            }
        });

    }

    public void customAnimations() {
        if (floatingMenuItem1.getVisibility() == View.VISIBLE) {
            implementOnCloseAnimations();
        } else {
            implentOnOpenAnimations();
        }

    }

    private void implentOnOpenAnimations() {
        ObjectAnimator slideInAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 200, 0);
        slideInAnimation.setDuration(200);
        ObjectAnimator fadeInAnimatin = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 0, 1);
        fadeInAnimatin.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 1f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 1f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 200, 0);
        slideInAnimation2.setDuration(200);
        ObjectAnimator fadeInAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 0, 1);
        fadeInAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 1f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 1f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideInAnimation).with(fadeInAnimatin).with(scaleAnimationX).with(scaleAnimationY).with(slideInAnimation2).with(fadeInAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                floatingMenuItem1.setVisibility(View.VISIBLE);
                floatingMenuItem2.setVisibility(View.VISIBLE);
                clist_Back_view.setVisibility(View.VISIBLE);
                floatingActionButton.setBackgroundResource(R.mipmap.closed);

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    private void implementOnCloseAnimations() {
        ObjectAnimator slideOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "translationY", 0, 200);
        slideOutAnimation.setDuration(200);
        ObjectAnimator fadeOutAnimation = ObjectAnimator.ofFloat(floatingMenuItem1, "alpha", 1, 0);
        fadeOutAnimation.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleX", 0.5f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(floatingMenuItem1, "scaleY", 0.5f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "translationY", 0, 200);
        slideOutAnimation2.setDuration(200);
        ObjectAnimator fadeOutAnimation2 = ObjectAnimator.ofFloat(floatingMenuItem2, "alpha", 1, 0);
        fadeOutAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleX", 0.5f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(floatingMenuItem2, "scaleY", 0.5f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideOutAnimation).with(fadeOutAnimation).with(scaleAnimationX).with(scaleAnimationY).with(slideOutAnimation2).with(fadeOutAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                floatingMenuItem1.setVisibility(View.GONE);
                floatingMenuItem2.setVisibility(View.GONE);
                clist_Back_view.setVisibility(View.GONE);
                floatingActionButton.setBackgroundResource(R.mipmap.sort_btn);


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animatorSet.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        ServiceActivity.doubleFlag = true;
        ServiceActivity.flag = true;
    }
}