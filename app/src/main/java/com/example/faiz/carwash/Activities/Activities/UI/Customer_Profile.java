package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.Subscription;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by AST on 9/15/2017.
 */

public class Customer_Profile extends android.support.v4.app.Fragment {

    public TextView cust_name,cust_mob,cust_app,cust_block,cust_flat,cust_parking,cust_email;
    public Add_Customer_Object add_customer_object;
    public LinearLayout add_subscription_container;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.customer_profile,null);
        initView(view);

        if(getArguments()!=null){
            add_customer_object = getArguments().getParcelable("cust");
            cust_app.setText(add_customer_object.getCust_apartment());
            cust_block.setText(add_customer_object.getCust_block());
            cust_email.setText(add_customer_object.getCust_email());
            cust_flat.setText(add_customer_object.getCust_flat());
            cust_parking.setText(add_customer_object.getCust_parking());
            cust_name.setText(add_customer_object.getCust_name());
            cust_mob.setText(add_customer_object.getCust_mobile());

            FirebaseHandler.getInstance().getCustomer_subscription().child(add_customer_object.cust_Uid)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot!=null){
                                if(dataSnapshot.getValue()!=null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Subscription subscription = data.getValue(Subscription.class);
                                        addLayout(subscription);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


        }


        return view;
    }

    public void addLayout(Subscription segment) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.subscription_container_view, null, false);
        add_subscription_container.addView(layout);
      TextView  vehicle_make = (TextView) layout.findViewById(R.id.vehicle_make);
      TextView  vehicle_reg_no = (TextView) layout.findViewById(R.id.vehicle_reg_no);
   //     app_detail_vehicle = (TextView) layout.findViewById(R.id.app_detail_vehicle);
      //  app_detail_service = (TextView) layout.findViewById(R.id.app_detail_service);

        vehicle_make.setText(segment.getVehicle_make());
        vehicle_reg_no.setText(segment.getVehicle_Reg_no());
//        app_detail_service.setText(segment.getService_type());
//        app_detail_vehicle.setText(segment.getVehicle_type());

    }

    private void initView(View view) {
        cust_name = (TextView)view.findViewById(R.id.cust_name);
        cust_mob = (TextView)view.findViewById(R.id.cust_mob);
        cust_app = (TextView)view.findViewById(R.id.cust_app);
        cust_block = (TextView)view.findViewById(R.id.cust_block);
        cust_flat = (TextView)view.findViewById(R.id.cust_flat);
        cust_parking = (TextView)view.findViewById(R.id.cust_parking);
        cust_email = (TextView)view.findViewById(R.id.cust_email);
        add_subscription_container = (LinearLayout)view.findViewById(R.id.add_subscription_container);
    }
}
