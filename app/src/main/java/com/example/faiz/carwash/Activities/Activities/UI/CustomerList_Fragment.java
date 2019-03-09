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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.CustomerActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.Customer_list_Adapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.SuperVisor_ApartmentAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_BlockAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Block;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AST on 9/16/2017.
 */

public class CustomerList_Fragment extends android.support.v4.app.Fragment {

    public ImageView floatingActionButton;
    public ImageView floatingMenuItem1;
    public ImageView floatingMenuItem2;
    public View clist_Back_view;
    public static TextView ActionBartitle;
    public ImageView back_arrow,remove_search;
    public ListView customer_list;
    public Customer_list_Adapter customer_list_adapter;
    public ArrayList<Add_Customer_Object> add_customer_objects;
    public EditText customer_list_search;
    public Dialog filterDialog;
    public Button custlist_filter_apply,custlist_filter_cancle;
    public Spinner custlist_filter_appart,custlist_filter_account,custlist_filter_block;
    public EditText custlist_filter_min_a,custlist_filter_min_b;
    public String blockName[] = {"Tower 1", "Tower 2", "Tower 3", "Tower 4", "Tower 5", "Tower 6", "Tower 7", "Tower 8", "Tower 9", "Tower 10"};
    SuperVisor_ApartmentAdapter adapterApartmentName;
    public String apartmentName[];
    Supervisor_BlockAdapter adapterBlockName;
    public ArrayList<Block> blockArrayList;
    public ArrayList<Apartment> apartmentArrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_list,null);
        add_customer_objects = new ArrayList<>();
        initialzeView(view);


        FirebaseHandler.getInstance().getAdd_customer().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null) {
                       add_customer_objects.clear();
                        for (DataSnapshot Snap : dataSnapshot.getChildren()) {
                            Add_Customer_Object add_customer_object = Snap.getValue(Add_Customer_Object.class);
                            add_customer_objects.add(add_customer_object);
                            customer_list_adapter.add(add_customer_object);
                            customer_list_adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        FirebaseHandler.getInstance().getAdd_apartment().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null) {
                        int i = 0;
                      //  apartmentName = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("TAG_SNAP", snapshot.getValue().toString());
                            Apartment apartment = snapshot.getValue(Apartment.class);
                         //   apartmentName[i] = apartment.getApartmentName();
                          //  i++;
                            apartmentArrayList.add(apartment);
                            adapterApartmentName.notifyDataSetChanged();
                        }


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        customer_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Bundle bundle = new Bundle();
                Customer_Detail customer_detail = new Customer_Detail();
                bundle.putParcelable("object", add_customer_objects.get(i));
                customer_detail.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.customer_container,customer_detail).commit();
            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.getInstance().onBackPressed();

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
                filterDialog.show();



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
                        .replace(R.id.customer_container, new Add_Customer()).commit();
            }
        });

        //search Function
        customer_list_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!customer_list_search.getText().toString().equals("")){
                    remove_search.setVisibility(View.VISIBLE);
                }else{
                    remove_search.setVisibility(View.INVISIBLE);
                }
                customer_list_adapter.searchCustomer(customer_list_search.getText().toString());

            }
        });



        remove_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customer_list_search.setText("");
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


    private void initialzeView(View view) {
        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Customers");
        customer_list_search = (EditText)view.findViewById(R.id.customer_list_search);
        clist_Back_view = (View)view.findViewById(R.id.clist_back_view);
        floatingActionButton = (ImageView) view.findViewById(R.id.customer_list_sort);
        floatingMenuItem1 = (ImageView)view.findViewById(R.id.floatingMenuItem1);
        floatingMenuItem2 = (ImageView)view.findViewById(R.id.floatingMenuItem2);
        customer_list = (ListView)view.findViewById(R.id.customer_list);
        remove_search = (ImageView)view.findViewById(R.id.remove_search);
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        blockArrayList = new ArrayList<>();
        apartmentArrayList = new ArrayList<>();
        customer_list_adapter = new Customer_list_Adapter(add_customer_objects,getActivity());
        customer_list.setAdapter(customer_list_adapter);



        //for Dialog
        View completeView = getActivity().getLayoutInflater().inflate(R.layout.custlist_filter, null);
        filterDialog = new Dialog(getActivity());
        filterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        filterDialog.setContentView(completeView);


        custlist_filter_apply= (Button)completeView.findViewById(R.id.custlist_filter_apply);
        custlist_filter_cancle = (Button)completeView.findViewById(R.id.custlist_filter_cancle);
        custlist_filter_account = (Spinner)completeView.findViewById(R.id.custlist_filter_account);
        custlist_filter_min_a = (EditText)completeView.findViewById(R.id.custlist_filter_min_a);
        custlist_filter_min_b = (EditText)completeView.findViewById(R.id.custlist_filter_min_b);
        custlist_filter_block = (Spinner)completeView.findViewById(R.id.custlist_filter_block);
        custlist_filter_appart = (Spinner)completeView.findViewById(R.id.custlist_filter_appart);

        adapterBlockName = new Supervisor_BlockAdapter(getActivity(),blockArrayList);
        custlist_filter_block.setAdapter(adapterBlockName);
        adapterApartmentName = new SuperVisor_ApartmentAdapter(getActivity(),apartmentArrayList);
        custlist_filter_appart.setAdapter(adapterApartmentName);

        custlist_filter_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //    if (adapterView.getChildAt(0) != null) {
            ///        ((TextView) adapterView.getChildAt(0)).setTextSize(10);
            //        ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
             //   }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        custlist_filter_appart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            //    if (adapterView.getChildAt(0) != null) {
             //       ((TextView) adapterView.getChildAt(0)).setTextSize(10);
             //       ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);

                    Apartment apartment = (Apartment) view.findViewById(R.id.text1).getTag();
                    getblocks(apartment);

            //    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        custlist_filter_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Apartment apartment = (Apartment) custlist_filter_appart.getSelectedItem();
              Block block = (Block)custlist_filter_block.getSelectedItem();

                customer_list_adapter.filterCustomer(apartment.getApartmentName(),block.getBlock_title());
                filterDialog.dismiss();
            }
        });

        custlist_filter_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.dismiss();
            }
        });



                //// End Dialog
    }


    private void getblocks(Apartment apartment) {
        FirebaseHandler.getInstance().getAdd_blocks().child(apartment.getApartmentUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                blockArrayList.clear();
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Block block = snapshot.getValue(Block.class);
                            blockArrayList.add(new Block(block.block_title,block.getBlock_uid()));
                            adapterBlockName.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        CustomerActivity.doubleFlag = true;
        CustomerActivity.flag = true;
    }
}
