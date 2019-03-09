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
import com.example.faiz.carwash.Activities.Activities.Activities.CustomerActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.LoginActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.Message_NotificationActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.ProfileActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.ServiceActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.TransactionActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.Navigations_ItemsAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.OnSwipeTouchListener;
import com.example.faiz.carwash.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by AST on 9/13/2017.
 */

public class Owner_Home extends android.support.v4.app.Fragment {


    public DrawerLayout drawer_layout;
    public ImageView navIcon;
    public ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public RelativeLayout main_view;
    public String[] menuName= {"Change Password","Terms & Conditions","Send Messages","Add Customer","Pay Bill Online","Log Out"};
    public int[] menuIcons = {R.mipmap.change_password,R.mipmap.menu_terms_and_conditions,R.mipmap.send_message,R.mipmap.add_customer,R.mipmap.pay_bill,R.mipmap.add_customer};
    public CardView transactionCard,CustomerCard,ComplaintCard,ServicesCard,NotificationCard,ProfileCard;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.owner_home,null);

        drawer_layout = (DrawerLayout)view.findViewById(R.id.drawer_layout);
        navIcon = (ImageView)view.findViewById(R.id.nav_icon);
        mDrawerList = (ListView)view.findViewById(R.id.left_drawer);
        main_view = (RelativeLayout)view.findViewById(R.id.main_view);
        transactionCard = (CardView)view.findViewById(R.id.owner_transaction);
        CustomerCard = (CardView)view.findViewById(R.id.owner_customer);
        ComplaintCard = (CardView)view.findViewById(R.id.owner_complaint);
        ServicesCard = (CardView)view.findViewById(R.id.owner_service);
        NotificationCard = (CardView)view.findViewById(R.id.owner_notification);
        ProfileCard = (CardView)view.findViewById(R.id.owner_profile);

        View viewinflate = inflater.inflate(R.layout.nav_header_main,null);
        Navigations_ItemsAdapter navigations_itemsAdapter = new Navigations_ItemsAdapter(getActivity(),menuName,menuIcons);
        mDrawerList.setAdapter(navigations_itemsAdapter);
        mDrawerList.addHeaderView(viewinflate);

        transactionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        Intent intent = new Intent(getActivity(), TransactionActivity.class);
                        getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                        getActivity().startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);



//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Transaction_Summary()).commit();
            }
        });


        CustomerCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
             //   getFragmentManager().beginTransaction()
              //          .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
              //          .addToBackStack(null)
                 //       .add(R.id.container_main, new CustomerList_Fragment()).commit();
            }
        });

        ComplaintCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ComplaintActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);


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
                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);

//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Service_Regular_Fragment()).commit();
            }
        });

        NotificationCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Message_NotificationActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Message_Fragment()).commit();
            }
        });

        ProfileCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                getActivity().startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);
          //      ProfileActivity.flag  = true;
           //     ProfileActivity.doubleFlag = true;
//                getFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                        .addToBackStack(null)
//                        .add(R.id.container_main, new Owner_Profile_Edit()).commit();
            }
        });


        navIcon.setOnTouchListener(new OnSwipeTouchListener(getActivity())
        {
            @Override
            public void onTouch() {
                if(drawer_layout.isDrawerOpen(Gravity.LEFT))
                {
                    drawer_layout.closeDrawer(mDrawerList);
                    // getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                    // getSupportActionBar().setCustomView(R.layout.menu_title);
                    // getSupportActionBar().show();
                }
                else {
                    drawer_layout.openDrawer(Gravity.LEFT);
                    //getSupportActionBar().hide();
                    // requestWindowFeature(Window.FEATURE_NO_TITLE);
                }
            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawer_layout,null, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                main_view.setTranslationX(slideOffset * drawerView.getWidth());
                drawer_layout.bringChildToFront(drawerView);
                drawer_layout.requestLayout();
            }
        };
        drawer_layout.setDrawerListener(mDrawerToggle);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Change_Password()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }

                if(i==2){
                    getFragmentManager().beginTransaction()
                             .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Terms_and_Condition()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==4){
//
//                    Bundle bundle =  new Bundle();
//                    Add_Customer add_customer = new Add_Customer();
//                    bundle.putString("add","add_customer");
//                    add_customer.setArguments(bundle);
//
//                    getFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
//                            .addToBackStack(null)
//                            .add(R.id.container_main,add_customer).commit();

                    Intent intent = new Intent(getActivity(), CustomerActivity.class);
                    intent.putExtra("key","add");
                    getActivity().overridePendingTransition(R.anim.slide_right,R.anim.slide_out_right);
                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_left,R.anim.slide_out_left);

                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==3){
                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .add(R.id.container_main, new Send_Message()).commit();

                    drawer_layout.closeDrawer(mDrawerList);
                }
                if(i==6){
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    drawer_layout.closeDrawer(mDrawerList);
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
