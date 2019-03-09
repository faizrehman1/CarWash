package com.example.faiz.carwash.Activities.Activities.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.faiz.carwash.Activities.Activities.Activities.ComplaintActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.LoginActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.Message_NotificationActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.ServiceActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.TransactionActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.Navigations_ItemsAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.OnSwipeTouchListener;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Utils.SharedPref;
import com.example.faiz.carwash.R;

/**
 * Created by AST on 9/13/2017.
 */

public class Customer_home extends android.support.v4.app.Fragment {

    public DrawerLayout drawer_layout;
    public ImageView navIcon;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public RelativeLayout main_view;
    public String[] menuName = {"Change Password", "Terms & Conditions", "Send Messages", "Add Customer", "Pay Bill Online", "Log Out"};
    public int[] menuIcons = {R.mipmap.change_password, R.mipmap.menu_terms_and_conditions, R.mipmap.send_message, R.mipmap.add_customer, R.mipmap.pay_bill, R.mipmap.add_customer};

    public CardView transactionCard, termsandcond, ComplaintCard, ServicesCard, NotificationCard, ProfileCard;
    public Add_Customer_Object add_customer_object;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_home, null);
        initViews(view);


        if (getArguments() != null) {
            add_customer_object = getArguments().getParcelable("cust");

        }


        navIcon.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            @Override
            public void onTouch() {
                if (drawer_layout.isDrawerOpen(Gravity.LEFT)) {
                    drawer_layout.closeDrawer(mDrawerList);
                    // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    // getSupportActionBar().setCustomView(R.layout.menu_title);
                    // getSupportActionBar().show();
                } else {
                    drawer_layout.openDrawer(Gravity.LEFT);
                    //getSupportActionBar().hide();
                    // requestWindowFeature(Window.FEATURE_NO_TITLE);
                }
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer_layout, null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                main_view.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);


        transactionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), TransactionActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);


//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Transaction_Summary()).commit();
            }
        });


        termsandcond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), CustomerActivity.class);
//                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
//                getActivity().startActivity(intent);
//                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.container_main, new Terms_and_Condition()).commit();
            }
        });

        ComplaintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ComplaintActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);


//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Complaints_Fragment()).commit();
            }
        });

        ServicesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ServiceActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);


            }
        });

        NotificationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Message_NotificationActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right, R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left, R.anim.slide_out_left);

            }
        });

        ProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (add_customer_object != null) {
                    Customer_Profile customer_profile = new Customer_Profile();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("cust", add_customer_object);
                    customer_profile.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, customer_profile).commit();
                }
            }
        });


        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Change_Password()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 2) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Terms_and_Condition()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 4) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Add_Customer()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 3) {
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Send_Message()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                } else if (i == 6) {
                    Add_Customer_Object add_customer_object = new Add_Customer_Object("","","","","","","","","","");
                    SharedPref.setCurrentUser(getActivity(),add_customer_object);
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    drawer_layout.closeDrawer(mDrawerList);
                }
            }
        });

        return view;


    }

    private void initViews(View view) {
        transactionCard = (CardView) view.findViewById(R.id.transaction_customer);
        termsandcond = (CardView) view.findViewById(R.id.terms_and_cond_customer);
        ComplaintCard = (CardView) view.findViewById(R.id.complaint_customer);
        ServicesCard = (CardView) view.findViewById(R.id.service_customer);
        NotificationCard = (CardView) view.findViewById(R.id.message_customer);
        ProfileCard = (CardView) view.findViewById(R.id.profile_customer);
        drawer_layout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        navIcon = (ImageView) view.findViewById(R.id.nav_icon);
        mDrawerList = (ListView) view.findViewById(R.id.left_drawer);
        main_view = (RelativeLayout) view.findViewById(R.id.main_view);
        View viewinflate = getActivity().getLayoutInflater().inflate(R.layout.nav_header_main, null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(getActivity(), menuName, menuIcons);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
