package com.example.faiz.carwash.Activities.Activities.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
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

import com.example.faiz.carwash.Activities.Activities.Activities.MainActivity;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/19/2017.
 */

public class Payment_Successful extends android.support.v4.app.Fragment {

    public ImageButton floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public View clist_Back_view, payment_cardone_view, payment_cardtwo_view;
    public static TextView ActionBartitle;
    public Button update_payment;
    public ImageView back_arrow;
    public TextView payment_user_email, payment_parking_base, payment_flat, payment_block, payment_apartment, payment_mobile_no, payment_user_name, payment_invoice_date, payment_park_slot_no;
    public Add_Customer_Object add_customer_object;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.payment_success, null);
        initializeView(view);

        if (getArguments() != null) {
            if (getArguments().getParcelable("object") != null) {
                add_customer_object = getArguments().getParcelable("object");
                initializeData(add_customer_object);
            }
        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.getInstance().onBackPressed();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customAnimations();
            }
        });


        return view;
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
                payment_cardone_view.setVisibility(View.VISIBLE);
                payment_cardtwo_view.setVisibility(View.VISIBLE);
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
                payment_cardone_view.setVisibility(View.GONE);
                payment_cardtwo_view.setVisibility(View.GONE);
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

    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Payment Successful");
        clist_Back_view = (View) view.findViewById(R.id.payment_back_view);
        payment_cardone_view = (View) view.findViewById(R.id.payment_cardone_view);
        payment_cardtwo_view = (View) view.findViewById(R.id.payment_cardtwo_view);
        floatingActionButton = (ImageButton) view.findViewById(R.id.payment_list_fab);
        floatingMenuItem1 = (ImageView) view.findViewById(R.id.payment_share);
        floatingMenuItem2 = (ImageView) view.findViewById(R.id.payment_download);
        payment_user_email = (TextView) view.findViewById(R.id.payment_user_email);
        payment_parking_base = (TextView) view.findViewById(R.id.payment_parking_base);

        payment_flat = (TextView) view.findViewById(R.id.payment_flat);
        payment_block = (TextView) view.findViewById(R.id.payment_block);
        payment_apartment = (TextView) view.findViewById(R.id.payment_apartment);
        payment_mobile_no = (TextView) view.findViewById(R.id.payment_mobile_no);
        payment_user_name = (TextView) view.findViewById(R.id.payment_user_name);
        payment_invoice_date = (TextView) view.findViewById(R.id.payment_invoice_date);
        payment_park_slot_no = (TextView) view.findViewById(R.id.payment_park_slot_no);

    }

    private void initializeData(Add_Customer_Object add_customer_object) {

        payment_apartment.setText(add_customer_object.cust_apartment);
        payment_block.setText(add_customer_object.getCust_block());
        payment_invoice_date.setText("");
        payment_mobile_no.setText(add_customer_object.getCust_mobile());
        payment_user_email.setText(add_customer_object.getCust_email());
        payment_parking_base.setText(add_customer_object.getCust_parking());
        payment_user_name.setText(add_customer_object.getCust_name());
        payment_flat.setText(add_customer_object.getCust_flat());
        payment_park_slot_no.setText(add_customer_object.getCust_parking_slot());

    }
}
