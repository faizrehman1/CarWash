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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.CustomerActivity;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.Subscription;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by AST on 9/18/2017.
 */

public class Customer_Detail extends android.support.v4.app.Fragment {

    public ImageButton floatingActionButton;
    public ImageView edit_customer_details;
    public ImageView delete_customer_details;
    public View clist_Back_view,cdetail_back_view;
    public static TextView ActionBartitle;
    public Button update_payment;
    public ImageView back_arrow;
    public Add_Customer_Object add_customer_object;
    public TextView customer_subs_vehicle_rno,customer_subs_vehicle_make,parking_slot_detail,customer_app_detail,customer_block_detail,customer_email_detail,customer_flat_detail,customer_name_detail,customer_parking_detail,customer_phone_detail;
    public Subscription subscription;
    public LinearLayout subscription_container_detail;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_detail,null);
        initializeView(view);

        if(getArguments()!=null){
            add_customer_object = getArguments().getParcelable("object");
            customer_app_detail.setText(add_customer_object.getCust_apartment());
            customer_block_detail.setText(add_customer_object.getCust_block());
            customer_email_detail.setText(add_customer_object.getCust_email());
            customer_flat_detail.setText(add_customer_object.getCust_flat());
            customer_name_detail.setText(add_customer_object.getCust_name());
            customer_parking_detail.setText(add_customer_object.getCust_parking());
        //    customer_subs_vehicle_make.setText(add_customer_object.getSubscription().getVehicle_make());
         //   customer_subs_vehicle_rno.setText(add_customer_object.getCust_parking_slot());
            customer_phone_detail.setText(add_customer_object.getCust_mobile());
            parking_slot_detail.setText(add_customer_object.getCust_parking_slot());


        }

        FirebaseHandler.getInstance().getCustomer_subscription().child(add_customer_object.getCust_Uid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue()!=null){
                            AppLogs.d("TAG_SNAP",dataSnapshot.getValue().toString());
                            int i = 0;
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                subscription = data.getValue(Subscription.class);
                                subscription_container_detail.addView(addCustomer_subs_vehicle_make(i,subscription));
                             //   subscription_container_detail.addView(addcustomer_subs_vehicle_rno(i,subscription));
                                i++;
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    customAnimations();
            }
        });

        update_payment  = (Button)view.findViewById(R.id.update_payment);


        update_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   customAnimations();
                Payment_Successful payment_successful = new Payment_Successful();
                Bundle bundle = new Bundle();
                bundle.putParcelable("object",add_customer_object);
                payment_successful.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.customer_container,payment_successful).commit();
            }
        });

        edit_customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Add_Customer add_customer = new Add_Customer();

                if(add_customer_object!=null) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object", add_customer_object);
                    add_customer.setArguments(bundle);
                }
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                      // .addToBackStack(null)
                        .replace(R.id.customer_container,add_customer).commit();
                customAnimations();
            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.getInstance().onBackPressed();
            }
        });

        return view;
    }

    private View addCustomer_subs_vehicle_make(int i, Subscription subscription) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.cust_detail_subs_container, null, false);

        layout.setTag(subscription);

       customer_subs_vehicle_make  = (TextView)layout.findViewById(R.id.customer_subs_vehicle_make);
        customer_subs_vehicle_rno  = (TextView)layout.findViewById(R.id.customer_subs_vehicle_rno);


        customer_subs_vehicle_make.setText(subscription.getVehicle_make());
        customer_subs_vehicle_rno.setText(subscription.getVehicle_Reg_no());

        return layout;
    }

    private void initializeView(View view) {
        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        subscription_container_detail = (LinearLayout)view.findViewById(R.id.subscription_container_detail);
        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Customers Details");
        clist_Back_view = (View)view.findViewById(R.id.clist_back_view);
        cdetail_back_view= (View)view.findViewById(R.id.cdetail_back_view);
        floatingActionButton = (ImageButton)view.findViewById(R.id.customer_list_sort);
        edit_customer_details = (ImageView)view.findViewById(R.id.edit_customer_details);
        delete_customer_details = (ImageView)view.findViewById(R.id.delete_customer_details);
        parking_slot_detail  = (TextView)view.findViewById(R.id.parking_slot_detail);
        customer_app_detail  = (TextView)view.findViewById(R.id.customer_app_detail);
        customer_block_detail  = (TextView)view.findViewById(R.id.customer_block_detail);
        customer_email_detail  = (TextView)view.findViewById(R.id.customer_email_detail);
        customer_flat_detail  = (TextView)view.findViewById(R.id.customer_flat_detail);
        customer_name_detail  = (TextView)view.findViewById(R.id.customer_name_detail);
        customer_parking_detail  = (TextView)view.findViewById(R.id.customer_parking_detail);
        customer_phone_detail  = (TextView)view.findViewById(R.id.customer_phone_detail);




    }

    public void customAnimations() {
        if (edit_customer_details.getVisibility() == View.VISIBLE) {
            implementOnCloseAnimations();
        } else {
            implentOnOpenAnimations();
        }

    }

    private void implentOnOpenAnimations() {
        ObjectAnimator slideInAnimation = ObjectAnimator.ofFloat(edit_customer_details, "translationY", 200, 0);
        slideInAnimation.setDuration(200);
        ObjectAnimator fadeInAnimatin = ObjectAnimator.ofFloat(edit_customer_details, "alpha", 0, 1);
        fadeInAnimatin.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(edit_customer_details, "scaleX", 1f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(edit_customer_details, "scaleY", 1f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideInAnimation2 = ObjectAnimator.ofFloat(delete_customer_details, "translationY", 200, 0);
        slideInAnimation2.setDuration(200);
        ObjectAnimator fadeInAnimation2 = ObjectAnimator.ofFloat(delete_customer_details, "alpha", 0, 1);
        fadeInAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(delete_customer_details, "scaleX", 1f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(delete_customer_details, "scaleY", 1f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideInAnimation).with(fadeInAnimatin).with(scaleAnimationX).with(scaleAnimationY).with(slideInAnimation2).with(fadeInAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);


        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                edit_customer_details.setVisibility(View.VISIBLE);
                delete_customer_details.setVisibility(View.VISIBLE);
                cdetail_back_view.setVisibility(View.VISIBLE);
                clist_Back_view.setVisibility(View.VISIBLE);
                floatingActionButton.setBackgroundResource(R.mipmap.closed);
                update_payment.setText("Edit");
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
        ObjectAnimator slideOutAnimation = ObjectAnimator.ofFloat(edit_customer_details, "translationY", 0, 200);
        slideOutAnimation.setDuration(200);
        ObjectAnimator fadeOutAnimation = ObjectAnimator.ofFloat(edit_customer_details, "alpha", 1, 0);
        fadeOutAnimation.setDuration(200);
        ObjectAnimator scaleAnimationX = ObjectAnimator.ofFloat(edit_customer_details, "scaleX", 0.5f);
        scaleAnimationX.setDuration(200);
        ObjectAnimator scaleAnimationY = ObjectAnimator.ofFloat(edit_customer_details, "scaleY", 0.5f);
        scaleAnimationY.setDuration(200);

        ObjectAnimator slideOutAnimation2 = ObjectAnimator.ofFloat(delete_customer_details, "translationY", 0, 200);
        slideOutAnimation2.setDuration(200);
        ObjectAnimator fadeOutAnimation2 = ObjectAnimator.ofFloat(delete_customer_details, "alpha", 1, 0);
        fadeOutAnimation2.setDuration(200);
        ObjectAnimator scaleAnimation2X = ObjectAnimator.ofFloat(delete_customer_details, "scaleX", 0.5f);
        scaleAnimation2X.setDuration(200);
        ObjectAnimator scaleAnimation2Y = ObjectAnimator.ofFloat(delete_customer_details, "scaleY", 0.5f);
        scaleAnimation2Y.setDuration(200);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(slideOutAnimation).with(fadeOutAnimation).with(scaleAnimationX).with(scaleAnimationY).with(slideOutAnimation2).with(fadeOutAnimation2).with(scaleAnimation2X).with(scaleAnimation2Y);

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                edit_customer_details.setVisibility(View.GONE);
                delete_customer_details.setVisibility(View.GONE);
                cdetail_back_view.setVisibility(View.GONE);
                clist_Back_view.setVisibility(View.GONE);
                floatingActionButton.setBackgroundResource(R.mipmap.sort_btn);
                update_payment.setText("Update");
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


    }
}
