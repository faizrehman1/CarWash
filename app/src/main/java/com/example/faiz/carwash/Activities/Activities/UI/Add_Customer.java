package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.CustomerActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.SuperVisor_ApartmentAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_BlockAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_ParkingAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Block;
import com.example.faiz.carwash.Activities.Activities.Model.Parkings;
import com.example.faiz.carwash.Activities.Activities.Model.Subscription;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AST on 9/16/2017.
 */

public class Add_Customer extends android.support.v4.app.Fragment {

    public LinearLayout add_subscription;

    public static TextView ActionBartitle;
    public Button add_customer_btn;
    public ImageView back_arrow;
    public EditText customer_name, customer_flat, customer_parking_slot_not, customer_mobile, customer_email;
    public Spinner spiner_apartment, spinner_block, customer_parking_spinner;

    public String apartmentName[] = {"Swan Lakes", "Eden Gardens", "Aparment 3", "Aparment 4", "Aparment 5", "Aparment 6", "Aparment 7"
            , "Aparment 8", "Aparment 9", "Aparment 10"};
    public String blockName[] = {"Tower 1", "Tower 2", "Tower 3", "Tower 4", "Tower 5", "Tower 6", "Tower 7", "Tower 8", "Tower 9", "Tower 10"};
    public String parkingName[] = {"Basement 1", "Basement 2", "Basement 3", "Basement 4", "Basement 5", "Basement 6", "Basement 7", "Basement 8"
            , "Basement 9", "Basement 10"};
    SuperVisor_ApartmentAdapter adapterApartmentName;
    Supervisor_BlockAdapter adapterBlockName;
    Supervisor_ParkingAdapter adapterParkingName;
    public Subscription subscription;
    public Add_Customer_Object add_customer_object;
    public TextView vehicle_make, vehicle_regNo;
    DatabaseReference reference;
    public String key;
    public LinearLayout subscription_container;
    public ArrayList<Apartment> apartmentArrayList;
    public ArrayList<Block> blockArrayList;
    public ArrayList<Parkings> parkingsArrayList;

    // Add_Customer_Object add_customer_object;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_customer, null);

        initializeView(view);
        initializeAdapters();
        reference = FirebaseHandler.getInstance().getAdd_customer().push();
        key = reference.getKey();
        if (getArguments() != null) {
            if (getArguments().getParcelable("object") != null) {
                add_customer_object = getArguments().getParcelable("object");
                //  subscription = add_customer_object.getSubscription();
                customer_name.setText(add_customer_object.getCust_name());
                customer_flat.setText(add_customer_object.getCust_flat());
                customer_email.setText(add_customer_object.getCust_email());
                customer_parking_slot_not.setText(add_customer_object.getCust_parking_slot());
                customer_mobile.setText(add_customer_object.getCust_mobile());
            //    customer_parking_spinner.setSelection(adapterParkingName.getPosition(add_customer_object.getCust_parking()));
                //   spinner_block.setSelection(adapterBlockName.getPosition(add_customer_object.getCust_block()));
                //     spiner_apartment.setSelection(adapterApartmentName.getPosition(add_customer_object.getCust_apartment()));
                //    vehicle_make.setText(add_customer_object.getSubscription().getVehicle_make());
                //   vehicle_regNo.setText(add_customer_object.getSubscription().getVehicle_Reg_no());
                key = add_customer_object.getCust_Uid().toString();
            }


        }

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.getInstance().onBackPressed();

            }
        });

        FirebaseHandler.getInstance().getCustomer_subscription().child(key).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        AppLogs.d("TAG_SNAP", dataSnapshot.getValue().toString());
                        int i = 0;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            subscription = data.getValue(Subscription.class);
                            subscription_container.addView(addLayout(i, subscription));
                            i++;
                        }

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        add_subscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Subscription add_subscription = new Add_Subscription();

                if(spiner_apartment.getSelectedItem()!=null) {

                    //  if (add_customer_object!=null) {
                    add_customer_object = new Add_Customer_Object(
                            customer_name.getText().toString(), customer_mobile.getText().toString(),
                            spiner_apartment.getSelectedItem().toString(), customer_parking_slot_not.getText().toString(),
                            spinner_block.getSelectedItem().toString(), customer_email.getText().toString(), customer_flat.getText().toString()
                            , customer_parking_spinner.getSelectedItem().toString(), key, "");

                            Bundle bundle = new Bundle();
                    bundle.putParcelable("object", add_customer_object);
                    add_subscription.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                            .addToBackStack(null)
                            .replace(R.id.customer_container, add_subscription).commit();

                }else{
                    Snackbar.make(view,"Add Apartment in Profile !",Snackbar.LENGTH_SHORT).show();
//                    add_customer_object = new Add_Customer_Object(
//                            customer_name.getText().toString(), customer_mobile.getText().toString(),
//                            "", customer_parking_slot_not.getText().toString(),
//                            "", customer_email.getText().toString(), customer_flat.getText().toString()
//                            , "", key, "");
                }



            }
        });

        add_customer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  if (subscription != null) {
                if (customer_name.getText().toString().equals("")) {
                    Snackbar.make(view, "Add valid customer name", Snackbar.LENGTH_LONG).show();
                } else if (customer_mobile.getText().toString().length() == 0 || customer_mobile.getText().toString().length() < 10 || customer_mobile.getText().toString().length() > 10) {
                    Snackbar.make(view, "Please enter valid Mobile Number", Snackbar.LENGTH_LONG).show();
                } else if (customer_parking_slot_not.getText().toString().equals("")) {
                    Snackbar.make(view, "Add valid slot no", Snackbar.LENGTH_LONG).show();
                } else if (customer_email.getText().toString().equals("") && !android.util.Patterns.EMAIL_ADDRESS.matcher(customer_email.getText().toString()).matches()) {
                    Snackbar.make(view, "Add valid customer email", Snackbar.LENGTH_LONG).show();
                } else if (customer_flat.getText().toString().equals("")) {
                    Snackbar.make(view, "Add valid customer flat ", Snackbar.LENGTH_LONG).show();
                } else {

                    if (spiner_apartment.getSelectedItem() !=null) {

                        Apartment apartment = (Apartment) spiner_apartment.getSelectedItem();
                        Block block = (Block) spinner_block.getSelectedItem();
                        Parkings parkings = (Parkings) customer_parking_spinner.getSelectedItem();

                        add_customer_object = new Add_Customer_Object(
                                customer_name.getText().toString(), customer_mobile.getText().toString(),
                                apartment.getApartmentName(), customer_parking_slot_not.getText().toString(),
                                block.getBlock_title(), customer_email.getText().toString(), customer_flat.getText().toString()
                                , parkings.getParking_title(), key, ""
                        );


                        FirebaseHandler.getInstance().getAdd_customer()
                                .child(key)
                                .setValue(add_customer_object, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Customer_Detail customer_detail = new Customer_Detail();
                                        Bundle bundle = new Bundle();
                                        bundle.putParcelable("object", add_customer_object);
                                        customer_detail.setArguments(bundle);
                                        getActivity().getSupportFragmentManager().popBackStack();
                                        getActivity().getSupportFragmentManager().beginTransaction()
                                                .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                                                //   .addToBackStack(null)
                                                .replace(R.id.customer_container, customer_detail).commit();
                                    }
                                });


                    }else{
                        Snackbar.make(view,"Add Apartment in Profile !",Snackbar.LENGTH_SHORT).show();

                    }
                }
                // }
                //  else {
                //     Snackbar.make(view, "Add Subscription First", Snackbar.LENGTH_LONG).show();
                //  }
            }
        });


        return view;
    }

    private View addLayout(int i, Subscription data) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.subscription_container, null, false);

        layout.setTag(data);

        vehicle_make = (TextView) layout.findViewById(R.id.vehicle_make);
        vehicle_regNo = (TextView) layout.findViewById(R.id.vehicle_reg_no);


        vehicle_make.setText(data.getVehicle_make());
        vehicle_regNo.setText(data.getVehicle_Reg_no());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Subscription add_subscription = new Add_Subscription();
                //  if (add_customer_object!=null) {
                add_customer_object = new Add_Customer_Object(
                        customer_name.getText().toString(), customer_mobile.getText().toString(),
                        spiner_apartment.getSelectedItem().toString(), customer_parking_slot_not.getText().toString(),
                        spinner_block.getSelectedItem().toString(), customer_email.getText().toString(), customer_flat.getText().toString()
                        , customer_parking_spinner.getSelectedItem().toString(), key, ""
                );
                Bundle bundle = new Bundle();
                bundle.putParcelable("object", add_customer_object);
                bundle.putParcelable("subs", (Parcelable) layout.getTag());
                add_subscription.setArguments(bundle);
                //   }
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.customer_container, add_subscription).commit();
            }
        });


        //  LinearLayout linear = (LinearLayout)findViewById(R.id.myLayout);
        // linear.addView(layout);

        return layout;
    }


    public void initializeAdapters() {
        adapterApartmentName = new SuperVisor_ApartmentAdapter(getActivity(), apartmentArrayList);
        spiner_apartment.setAdapter(adapterApartmentName);
        adapterBlockName = new Supervisor_BlockAdapter(getActivity(), blockArrayList);
        spinner_block.setAdapter(adapterBlockName);

        adapterParkingName = new Supervisor_ParkingAdapter(getActivity(),parkingsArrayList);
        customer_parking_spinner.setAdapter(adapterParkingName);

        FirebaseHandler.getInstance().getAdd_apartment().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Apartment apartment = data.getValue(Apartment.class);
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


       // customer_parking_spinner.setAdapter(adapterParkingName);
    }

    private void initializeView(View view) {
        add_subscription = (LinearLayout) view.findViewById(R.id.add_subscription);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        subscription_container = (LinearLayout) view.findViewById(R.id.subscription_container);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Customers");
        add_customer_btn = (Button) view.findViewById(R.id.add_customer_btn);
        customer_name = (EditText) view.findViewById(R.id.customer_name);
        customer_flat = (EditText) view.findViewById(R.id.customer_flat);
        customer_parking_slot_not = (EditText) view.findViewById(R.id.customer_parking_slot_not);
        customer_mobile = (EditText) view.findViewById(R.id.customer_mobile);
        customer_email = (EditText) view.findViewById(R.id.customer_email);
        spiner_apartment = (Spinner) view.findViewById(R.id.spiner_apartment);
        spinner_block = (Spinner) view.findViewById(R.id.spinner_block);
        customer_parking_spinner = (Spinner) view.findViewById(R.id.customer_parking_spinner);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        apartmentArrayList = new ArrayList<>();
        blockArrayList = new ArrayList<>();
        parkingsArrayList = new ArrayList<>();

        spiner_apartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Apartment apartment = (Apartment) view.findViewById(R.id.text1).getTag();
                getblocks(apartment);
                getParking(apartment);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void getParking(Apartment apartment) {
        FirebaseHandler.getInstance().getAdd_parking().child(apartment.getApartmentUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingsArrayList.clear();
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Parkings block = snapshot.getValue(Parkings.class);
                            parkingsArrayList.add(new Parkings(block.getParking_title(), block.getParking_uid()));
                            adapterParkingName.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
                            blockArrayList.add(new Block(block.block_title, block.getBlock_uid()));
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
        CustomerActivity.flag  = true;
    }
}
