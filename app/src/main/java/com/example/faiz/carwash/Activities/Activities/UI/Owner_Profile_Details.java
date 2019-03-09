package com.example.faiz.carwash.Activities.Activities.UI;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.carwash.Activities.Activities.Activities.ProfileActivity;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Owner_Services;
import com.example.faiz.carwash.Activities.Activities.Model.Supervisor;
import com.example.faiz.carwash.Activities.Activities.Model.UserModel;
import com.example.faiz.carwash.Activities.Activities.Model.Vehicle_TypeObject;
import com.example.faiz.carwash.Activities.Activities.Utils.AppController;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 9/15/2017.
 */

public class Owner_Profile_Details extends android.support.v4.app.Fragment {


    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public ImageView floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public View clist_Back_view;
    public LinearLayout info_box;
    public LinearLayout card_view_profile;
    public TextView user_name,user_phone,user_address;
    public CircleImageView profile_image;
    Drawable drawable;
    public FlexboxLayout flexBoxLayout_appartment,flexBoxLayout_supervisor,flexBoxLayout_vehicle_type,flexBoxLayout_service;
    public String vehicle_type[] ={"Small Car","Luxury Car","Bike"};
    public String servie_flex[] = {"Interior Detailing","Exterior Detailing","Washing","Mopping"};
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view  = inflater.inflate(R.layout.owner_profile_details,null);
        initializeView(view);
         progressDialog = ProgressDialog.show(getActivity(), "", "Loading Data..", true, false);
        FirebaseHandler.getInstance().getAdd_apartment().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flexBoxLayout_appartment.removeAllViews();
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){

                        int i=0;
                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            AppLogs.d("TAG_SNAP",snapshot.getValue().toString());
                            Apartment apartment = snapshot.getValue(Apartment.class);
                            flexBoxLayout_appartment.addView(edittext(i,"",apartment.getApartmentName()));
                            i++;
                        }
                        progressDialog.hide();

                    //    flexBoxLayout_appartment.addView(Optionedittext(i,"","ADD APARTMENT"));

                    }
                }else{
                    progressDialog.hide();
               //     flexBoxLayout_appartment.addView(Optionedittext(1,"","ADD APARTMENT"));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseHandler.getInstance().getAdd_supervisor().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                flexBoxLayout_supervisor.removeAllViews();
                if(dataSnapshot!=null) {
                    if (dataSnapshot.getValue() != null) {
                        int i = 0;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("TAG_SNAP", snapshot.getValue().toString());
                            Supervisor apartment = snapshot.getValue(Supervisor.class);
                            flexBoxLayout_supervisor.addView(edittext(i, "", apartment.getSuperVisor_name()));
                            i++;
                        }
                    }

                }
                progressDialog.hide();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getActivity().getSupportFragmentManager().popBackStack();
               ProfileActivity.getInstance().onBackPressed();
//              //  CustomerActivity.getInstance().onBackPressed();
//                                getActivity().getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                      //  .addToBackStack(null)
//                        .replace(R.id.profile_container, new Owner_Home()).commit();

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

            }
        });
        floatingMenuItem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                animations();
                customAnimations();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, new Owner_Profile_Edit()).commit();
            }
        });

        FirebaseHandler.getInstance().getUsersRef().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            if(dataSnapshot.getValue()!=null){
                                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                                user_phone.setText(userModel.getEmail());
                                user_address.setText(userModel.getAddress());
                                user_name.setText(userModel.getFname()+""+userModel.getLname());
                                if(userModel.getImageUrl().equals("")){
                                    Glide.with(getActivity()).load(R.drawable.user).asBitmap().into(profile_image);

                                }else{
                                    Glide.with(getActivity()).load(userModel.getImageUrl()).asBitmap().placeholder(R.drawable.user).into(profile_image);

                                }

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        return view;
    }

    private void initializeView(View view) {
        final int[] i = {0};
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        user_name = (TextView)view.findViewById(R.id.user_name);
        user_phone = (TextView)view.findViewById(R.id.user_phone);
        profile_image = (CircleImageView)view.findViewById(R.id.profile_image);
        user_address = (TextView)view.findViewById(R.id.user_address);
        flexBoxLayout_appartment = (FlexboxLayout)view.findViewById(R.id.flexBoxLayout_appartment);
        flexBoxLayout_supervisor = (FlexboxLayout)view.findViewById(R.id.flexBoxLayout_supervisor);
        flexBoxLayout_vehicle_type = (FlexboxLayout)view.findViewById(R.id.flexBoxLayout_vehicle_type);
        flexBoxLayout_service = (FlexboxLayout)view.findViewById(R.id.flexBoxLayout_service);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Profile");
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        info_box = (LinearLayout)view.findViewById(R.id.info_box);
        floatingActionButton = (ImageView) view.findViewById(R.id.profile_edit_sort);
        floatingMenuItem1 = (ImageView)view.findViewById(R.id.floatingMenuItem1);
        floatingMenuItem2 = (ImageView)view.findViewById(R.id.floatingMenuItem2);
        clist_Back_view = (View)view.findViewById(R.id.clist_Back_view);
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        card_view_profile = (LinearLayout) view.findViewById(R.id.card_view_profile);

      //  for (int ai = 0; ai < vehicle_type.length; ai++) {
        //    flexBoxLayout_vehicle_type.addView(edittext(ai,"",vehicle_type[ai]));
      //  }

        FirebaseHandler.getInstance().getAdd_vehicle_type()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            flexBoxLayout_vehicle_type.removeAllViews();
                            if(dataSnapshot.getValue()!=null){
                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                    Vehicle_TypeObject owner_services = data.getValue(Vehicle_TypeObject.class);
                                    flexBoxLayout_vehicle_type.addView(edittext(i[0], null, owner_services.getVehicle_type()));
                                    i[0]++;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        FirebaseHandler.getInstance().getAdd_owner_serivces()
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null){
                            flexBoxLayout_service.removeAllViews();
                            if(dataSnapshot.getValue()!=null){
                                for(DataSnapshot data:dataSnapshot.getChildren()){
                                    Owner_Services owner_services = data.getValue(Owner_Services.class);
                                    flexBoxLayout_service.addView(edittext(i[0], null, owner_services.getService_title()));
                                i[0]++;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

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
                card_view_profile.setBackgroundColor(Color.parseColor("#7fc9c8c8"));
                floatingActionButton.setBackgroundResource(R.mipmap.closed);
              //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //    info_box.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.viewcolor));
              //  }

                drawable =  ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.tint_color, getActivity().getTheme());
                info_box.setBackground(drawable);

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
                card_view_profile.setBackgroundColor(Color.parseColor("#FFFFFF"));

                floatingActionButton.setBackgroundResource(R.mipmap.sort_btn);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                  //  info_box.setBackgroundTintList(getActivity().getResources().getColorStateList(R.color.White));
//                 //   info_box.setBackgroundResource(R.drawable.custom_card);
//
//
//                }
                drawable =  ResourcesCompat.getDrawable(getActivity().getResources(), R.drawable.layout_border, getActivity().getTheme());
                info_box.setBackground(drawable);
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

    private TextView edittext(int id, String hint, String text) {
        TextView textView = new TextView(AppController.getContext());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_blue);

        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        //   textView.setMaxLines(1);
    //    textView.setPadding(25, 0, 25, 0);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setTextColor(Color.WHITE);
     //   textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
     //   textView.setCompoundDrawablePadding(5);
        textView.setAllCaps(true);
        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);

//        int marginInDp = (int) TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources()
//                        .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);

        // TextArrayList.add(editText);

        return textView;
    }

    private TextView Optionedittext(int id, String hint, String text) {
        TextView textView = new TextView(getActivity());
        //    EditText editText = new EditText(getActivity());
        textView.setId(id);
        //    textView.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        //   textView.setHeight(45);
        textView.setBackgroundResource(R.drawable.curver_text_gray);

        //   textView.setHint(hint);
        //  editText.setFocusable(focus);
        textView.setText(text);
        textView.setAllCaps(true);
        //   textView.setMaxLines(1);
        textView.setPadding(25, 0, 25, 0);
        textView.setTextColor(Color.WHITE);
        textView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.add_capsule_icon, 0, 0, 0);
        textView.setCompoundDrawablePadding(10);
     //   textView.setAllCaps(true);

        //  editText.setHintTextColor(Color.LTGRAY);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        int marginInDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 8, getActivity().getResources()
                        .getDisplayMetrics());// 8 is margin in dp
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 10, 10);
        //bottom/left/right/top
        textView.setLayoutParams(params);

        // textInputLayout.addView(editText);

        // TextArrayList.add(editText);

//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .replace(R.id.profile_container, new Add_Appartment()).commit();
//            }
//        });

        return textView;
    }
   @Override
    public void onResume() {
        super.onResume();
        ProfileActivity.doubleFlag = true;
        ProfileActivity.flag = true;
    }

}
