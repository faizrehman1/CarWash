package com.example.faiz.carwash.Activities.Activities.UI;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Block;
import com.example.faiz.carwash.Activities.Activities.Model.On_DemandServices;
import com.example.faiz.carwash.Activities.Activities.Model.Parkings;
import com.example.faiz.carwash.Activities.Activities.Model.Segment;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.android.flexbox.FlexboxLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AST on 9/15/2017.
 */

public class Add_Appartment extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow, add_segment, add_blocks, add_parking;
    public Spinner apartment_cost_spinner, apartment_vehicle_type, apartment_service;
    public EditText add_apart_name;
    public Button add_apartment_but;
    //  public ArrayList<Segment> segment;
    public TextView app_segcost, app_seg_v_type, app_seg_service, segment_name;
    public LinearLayout on_demand_container;

    public Apartment segmentObject;
    public String numbersDay[] = {"₹100", "₹250", "₹500", "₹750", "₹1000"};
    public ArrayAdapter<String> numberDaysAdapter;
    public String segment_service_array[] = {"Interior Detailing", "Exterior Detailing", "Washing", "Mopping"};
    public ArrayAdapter<String> segment_service_adapter;
    public String segment_vehicle_type_array[] = {"Small Car", "Mid/Premium Car", "Suv", "Bike"};
    public ArrayAdapter<String> segment_vehicle_type_adapter;
    public LinearLayout container_seg;
    public DatabaseReference reference;
    String key;
    public Segment segment;
    public ArrayList<String> addBlock_list;
    public ArrayList<String> addParking_list;
    public FlexboxLayout add_block_apart, add_parking_apart;
    public ImageView add_app_ondemand;
    public LinearLayout custom_container_seg;
   public boolean ApartmentFlag = false;
    public boolean BlockFlag  = false;
    public boolean ParkingFlag = false;
    public boolean segmentFlag = false;
    public boolean OnDemandFlag = false;
    public boolean onDemandCheck = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_appartment, null);
        initializeView(view);
        reference = FirebaseHandler.getInstance().getAdd_customer().push();
        key = reference.getKey();
        if (getArguments() != null) {
            segmentObject = getArguments().getParcelable("object");
            add_apart_name.setText(segmentObject.getApartmentName());
            key = segmentObject.getApartmentUid();
            //    if (segmentObject.getSegmentArrayList() != null) {
            //       if (segmentObject.getSegmentArrayList().size() > 0) {
            //         segment.add(segmentObject.getSegmentArrayList().get(0));
//            if (getArguments().getParcelable("segment") != null) {
//                segment = getArguments().getParcelable("segment");
//                container_seg.setVisibility(View.VISIBLE);
//                app_segcost.setText(segment.getSegment_cost());
//                app_seg_v_type.setText(segment.getVehicle_type());
//                app_seg_service.setText(segment.getService_type());
//                segment_name.setText(segment.getSegmentName());
//            }
            //   }
        } else {
            segmentObject = new Apartment(add_apart_name.getText().toString(),
                    "",
                    "", key);
        }

        FirebaseHandler.getInstance().getAdd_blocks()
                .child(segmentObject.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                add_block_apart.removeAllViews();
                                //    segment_container.removeAllViews();
                                int i = 0;
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Block segment = data.getValue(Block.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        add_block_apart.addView(edittext(i, "", segment.getBlock_title()));
                                        BlockFlag = true;
                                        i++;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        FirebaseHandler.getInstance().getAdd_parking()
                .child(segmentObject.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                add_parking_apart.removeAllViews();
                                //    segment_container.removeAllViews();
                                int i = 0;
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Parkings segment = data.getValue(Parkings.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        add_parking_apart.addView(edittext(i, "", segment.getParking_title()));
                                        ParkingFlag = true;
                                        i++;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        FirebaseHandler.getInstance().getAppartment_segments()
                .child(segmentObject.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                custom_container_seg.removeAllViews();
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Segment segment = data.getValue(Segment.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        addLayout(segment);
                                        segmentFlag = true;
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


        FirebaseHandler.getInstance().getApartment_on_demand_service()
                .child(segmentObject.getApartmentUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if(getActivity()!=null) {
                        if (dataSnapshot.getValue() != null) {
                            on_demand_container.removeAllViews();
                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                On_DemandServices on_demandServices = data.getValue(On_DemandServices.class);
                                addServiceLayout(on_demandServices);
                                OnDemandFlag = true;
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //    getActivity().getSupportFragmentManager().beginTransaction()
                //            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                //            .replace(R.id.profile_container, new Owner_Profile_Edit()).commit();
                //    ProfileActivity.getInstance().onBackPressed();
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        add_segment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSegment appartment_detail = new AddSegment();
                Bundle bundle = new Bundle();
                segmentObject = new Apartment(add_apart_name.getText().toString(),
                        "",
                        "", key);
                bundle.putParcelable("object", segmentObject);
                appartment_detail.setArguments(bundle);
                //   getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                        // .addToBackStack(null)
                        .replace(R.id.profile_container, appartment_detail).commit();
            }
        });

        add_apartment_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {


                if (add_apart_name.getText().toString().length()==0 || check_validation(add_apart_name.getText().toString())) {
                    Snackbar.make(view, "Enter valid Apartment Name", Snackbar.LENGTH_SHORT).show();
                } else {
                    //  String key;
                    //    if(segmentObject!=null){
                    //        key = segmentObject.getApartmentUid();
                    //    }else{
                    //      DatabaseReference reference = FirebaseHandler.getInstance().getAdd_apartment().push();
                    //    key = reference.getKey();
                    //    }
                    if(!ParkingFlag){
                        Snackbar.make(view,"Please Add Parking First!",Snackbar.LENGTH_SHORT).show();

                    }else if(!BlockFlag){
                        Snackbar.make(view,"Please Add Block First!",Snackbar.LENGTH_SHORT).show();

                    }else if (!segmentFlag) {
                        Snackbar.make(view,"Please Add Segment First!",Snackbar.LENGTH_SHORT).show();

                    }
                    else if(!OnDemandFlag){
                        Snackbar.make(view,"Please Add OnDemand Service First!",Snackbar.LENGTH_SHORT).show();

                    } else {

                        FirebaseHandler.getInstance().getAdd_apartment().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot!=null){
                                    if(dataSnapshot.getValue()!=null){
                                        for(DataSnapshot data:dataSnapshot.getChildren()){
                                            Apartment apartment = data.getValue(Apartment.class);
                                            if(apartment.getApartmentName().toLowerCase().equals(add_apart_name.getText().toString().toLowerCase())){
                                                Snackbar.make(view,"Apartment name already Exist",Snackbar.LENGTH_SHORT).show();
                                                ApartmentFlag = false;
                                                break;
                                            }else{
                                                ApartmentFlag = true;
                                            }
                                        }
                                        add_Apartment(ApartmentFlag);
                                    }else{
                                        ApartmentFlag = true;
                                        add_Apartment(ApartmentFlag);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                }
            }
        });

        apartment_cost_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        apartment_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        apartment_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(0) != null) {
                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return view;
    }

    private void add_Apartment(boolean apartmentFlag) {

        if(apartmentFlag) {

            segmentObject = new Apartment(add_apart_name.getText().toString(),
                    "",
                    "", key);
            FirebaseHandler.getInstance().getAdd_apartment().child(key).setValue(segmentObject, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                    Appartment_Detail appartment_detail = new Appartment_Detail();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object", segmentObject);
                    appartment_detail.setArguments(bundle);

                    getActivity().getFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                            .addToBackStack(null)
                            .replace(R.id.profile_container, appartment_detail).commit();
                }
            });
        }else{
       //     Toast.makeText(getActivity(),"Fill Required Fields",Toast.LENGTH_SHORT).show();
        }
    }

    public boolean check_validation(String inputStr){

        boolean flag;

        Pattern pattern = Pattern.compile(new String ("^[a-zA-Z]+( [a-zA-z]+)*$"));
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
        {
            //if pattern matches
            flag = false;
        }
        else
        {
            flag = true;
            //if pattern does not matches
        }

    return flag;
    }

    private void addServiceLayout(On_DemandServices on_demandServices) {

        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.add_ondemand_service, null, false);
        on_demand_container.addView(layout);
        TextView app_detail_cost = (TextView) layout.findViewById(R.id.app_detail_cost);
        // TextView segment_name = (TextView) layout.findViewById(R.id.segment_name);
        TextView app_detail_vehicle = (TextView) layout.findViewById(R.id.app_detail_vehicle);
        TextView app_detail_service = (TextView) layout.findViewById(R.id.app_detail_service);

        //  segment_name.setText(segment.getSegmentName());
        app_detail_cost.setText(on_demandServices.getService_cost());
        app_detail_service.setText(on_demandServices.getService_name());
        app_detail_vehicle.setText(on_demandServices.getService_vehicleType());

    }

    private void initializeView(View view) {
        final int[] i = {0};
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        add_segment = (ImageView) view.findViewById(R.id.add_segment);
        add_block_apart = (FlexboxLayout) view.findViewById(R.id.add_block_apart);
        add_parking_apart = (FlexboxLayout) view.findViewById(R.id.add_parking_apart);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Apartment");
        //   segment = new ArrayList<>();
        apartment_cost_spinner = (Spinner) view.findViewById(R.id.apartment_cost_spinner);
        apartment_vehicle_type = (Spinner) view.findViewById(R.id.apartment_vehicle_type);
        add_app_ondemand = (ImageView) view.findViewById(R.id.add_app_ondemand);
        add_blocks = (ImageView) view.findViewById(R.id.add_blocks);
        add_parking = (ImageView) view.findViewById(R.id.add_parking);
        apartment_service = (Spinner) view.findViewById(R.id.apartment_service);
        add_apart_name = (EditText) view.findViewById(R.id.add_apart_name);
        add_apartment_but = (Button) view.findViewById(R.id.add_apartment_but);
        app_segcost = (TextView) view.findViewById(R.id.app_segcost);
        app_seg_v_type = (TextView) view.findViewById(R.id.app_seg_v_type);
        app_seg_service = (TextView) view.findViewById(R.id.app_seg_service);
        on_demand_container = (LinearLayout) view.findViewById(R.id.on_demand_container);
        segment_name = (TextView) view.findViewById(R.id.segment_name);
        custom_container_seg = (LinearLayout) view.findViewById(R.id.custom_container_seg);
        //  app_add_block_tower = (TextView) view.findViewById(R.id.app_add_block_tower);
        // app_add_parking = (TextView) view.findViewById(R.id.app_add_parking);
        container_seg = (LinearLayout) view.findViewById(R.id.container_seg);
        numberDaysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, numbersDay);
        apartment_cost_spinner.setAdapter(numberDaysAdapter);

        segment_service_adapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, segment_service_array);
        apartment_service.setAdapter(segment_service_adapter);

        segment_vehicle_type_adapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, segment_vehicle_type_array);
        apartment_vehicle_type.setAdapter(segment_vehicle_type_adapter);
        addBlock_list = new ArrayList<>();
        addParking_list = new ArrayList<>();


        add_blocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog1 = alertDialog.create();
                final View view1 = getActivity().getLayoutInflater().inflate(R.layout.add_block_apartment, null);
                Button add_dialog_ms = (Button) view1.findViewById(R.id.add_dialog_ms);
                final Button cancle_dialog_ms = (Button) view1.findViewById(R.id.cancle_dialog_ms);
                final EditText editText = (EditText) view1.findViewById(R.id.block_name);

                add_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference ref = FirebaseHandler.getInstance().getAdd_blocks().child(key).push();
                        String tempKey = ref.getKey();
                        Block block = new Block(editText.getText().toString(), tempKey);
                        if (!editText.getText().toString().equals("") && !check_validation(editText.getText().toString())) {
                            FirebaseHandler.getInstance().getAdd_blocks()
                                    .child(key)
                                    .child(tempKey)
                                    .setValue(block, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            addBlock_list.add(editText.getText().toString());
                                            alertDialog1.dismiss();
                                            //       add_block_apart.addView(edittext(i[0], "", editText.getText().toString()));
                                            i[0]++;
                                        }
                                    });
                        } else {
                            editText.setError("Enter valid Block");
                        }
                    }
                });

                cancle_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });


                alertDialog1.setView(view1);
                alertDialog1.show();

            }
        });

        add_parking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog1 = alertDialog.create();
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.add_parking_apartment, null);
                Button add_dialog_ms = (Button) view1.findViewById(R.id.add_dialog_ms);
                Button cancle_dialog_ms = (Button) view1.findViewById(R.id.cancle_dialog_ms);
                final EditText editText = (EditText) view1.findViewById(R.id.park_name);

                add_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference ref = FirebaseHandler.getInstance().getAdd_blocks().child(key).push();
                        String tempKey = ref.getKey();
                        Parkings parkings = new Parkings(editText.getText().toString(), tempKey);
                        if (!editText.getText().toString().equals("") && !check_validation(editText.getText().toString())) {
                            FirebaseHandler.getInstance().getAdd_parking()
                                    .child(key)
                                    .child(tempKey)
                                    .setValue(parkings, new DatabaseReference.CompletionListener() {
                                        @Override
                                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                            addBlock_list.add(editText.getText().toString());
                                            alertDialog1.dismiss();
                                            //     add_parking_apart.addView(edittext(i[0], "", editText.getText().toString()));
                                            i[0]++;
                                        }
                                    });
                        } else {
                            editText.setError("Enter valid parking");
                        }
                    }
                });

                cancle_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog1.dismiss();
                    }
                });


                alertDialog1.setView(view1);
                alertDialog1.show();

            }
        });


        add_app_ondemand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                FirebaseHandler.getInstance().getApartment_on_demand_service().child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    On_DemandServices segment = data.getValue(On_DemandServices.class);
                                    if (segment.getService_name().toLowerCase().equals(apartment_service.getSelectedItem().toString().toLowerCase())) {
                                        Snackbar.make(view, "On Demand Service already Exist", Snackbar.LENGTH_SHORT).show();
                                        onDemandCheck = false;
                                        break;
                                    } else {
                                        onDemandCheck = true;
                                    }
                                }
                                add_OnDemand(onDemandCheck);
                            }else{
                                onDemandCheck = true;
                                add_OnDemand(onDemandCheck);

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }

        });
    }

    private void add_OnDemand(boolean onDemandCheck) {
        if(onDemandCheck) {
            //  final String tempCost = apartment_cost_spinner.getSelectedItem().toString();
            DatabaseReference ref = FirebaseHandler.getInstance().getApartment_on_demand_service()
                    .child(key).push();
            String tempKey = ref.getKey();
            On_DemandServices block = new On_DemandServices(
                    apartment_service.getSelectedItem().toString()
                    , apartment_cost_spinner.getSelectedItem().toString(),
                    apartment_vehicle_type.getSelectedItem().toString(), tempKey, key
            );

            FirebaseHandler.getInstance().getApartment_on_demand_service()
                    .child(key)
                    .child(tempKey)
                    .setValue(block, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                        }


                    });
        }
    }

    private TextView edittext(int id, String hint, String text) {
        TextView textView = new TextView(getActivity());
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

        return textView;
    }

    public void addLayout(final Segment segment) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.segment_container, null, false);
        custom_container_seg.addView(layout);
        TextView app_detail_cost = (TextView) layout.findViewById(R.id.app_detail_cost);
        TextView segment_name = (TextView) layout.findViewById(R.id.segment_name);
        TextView app_detail_vehicle = (TextView) layout.findViewById(R.id.app_detail_vehicle);
        TextView app_detail_service = (TextView) layout.findViewById(R.id.app_detail_service);
        layout.setTag(segment);
        segment_name.setText(segment.getSegmentName());
        app_detail_cost.setText(segment.getSegment_cost());
        app_detail_service.setText(segment.getService_type());
        app_detail_vehicle.setText(segment.getVehicle_type());

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddSegment appartment_detail = new AddSegment();
                Bundle bundle = new Bundle();
                segmentObject = new Apartment(add_apart_name.getText().toString(),
                        "",
                        "", key);
                bundle.putParcelable("object", segmentObject);
                bundle.putParcelable("segment", (Parcelable) layout.getTag());
                appartment_detail.setArguments(bundle);
                //   getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                        // .addToBackStack(null)
                        .replace(R.id.profile_container, appartment_detail).commit();
            }

        });


    }
    @Override
    public void onResume() {
        super.onResume();

    }
}



