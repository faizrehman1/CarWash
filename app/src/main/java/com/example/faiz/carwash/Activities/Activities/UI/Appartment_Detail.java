package com.example.faiz.carwash.Activities.Activities.UI;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 9/15/2017.
 */

public class Appartment_Detail extends android.support.v4.app.Fragment {

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Apartment apartment;
    public CircleImageView edit_app_details;
    public String numbersDay[] = {"₹100", "₹250", "₹500", "₹750", "₹1000"};
    public ArrayAdapter<String> numberDaysAdapter;
    public String segment_service_array[] = {"Interior Detailing", "Exterior Detailing", "Washing", "Mopping"};
    public ArrayAdapter<String> segment_service_adapter;
    public String segment_vehicle_type_array[] = {"Small Car", "Mid/Premium Car", "Suv", "Bike"};
    public ArrayAdapter<String> segment_vehicle_type_adapter;
    public Spinner appdetail_cost, appdetail_vehicle, appdetail_service;
    public TextView app_detail_cost, app_detail_vehicle, app_detail_service, segment_name, apartment_detail_name;
    public Button done_apartment;
    public LinearLayout segment_container,on_demand_container_detail;
    public FlexboxLayout app_detail_block,app_detail_parking;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.appartment_details, null);

        initializeView(view);

        if (getArguments() != null) {
            apartment = getArguments().getParcelable("object");
            apartment_detail_name.setText(apartment.getApartmentName());
       //     app_detail_parking.setText(apartment.getApartmentParking());
       //     app_detail_block.setText(apartment.getApartmentBlock());
            //   if(apartmeynt.getSegmentArrayList()!=null) {
            //     if (apartment.getSegmentArrayList().size() > 0) {
            //         app_detail_cost.setText(apartment.getSegmentArrayList().get(0).getSegment_cost());
            //         app_detail_service.setText(apartment.getSegmentArrayList().get(0).getService_type());
            //       app_detail_vehicle.setText(apartment.getSegmentArrayList().get(0).getVehicle_type());
//
            //        segment_name.setText(apartment.getSegmentArrayList().get(0).getSegmentName());
            //      }
            //   }
        }

        done_apartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, new Owner_Profile_Edit()).commit();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   ProfileActivity.getInstance().onBackPressed();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                      //  .addToBackStack(null)
                        .replace(R.id.profile_container, new Owner_Profile_Edit()).commit();
            }
        });

        edit_app_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_Appartment appartment_detail = new Add_Appartment();
                Bundle bundle = new Bundle();
                if (apartment != null) {
                    bundle.putParcelable("object", (Parcelable) apartment);
                }
                appartment_detail.setArguments(bundle);
           //     getFragmentManager().popBackStack();
                getActivity().getSupportFragmentManager().popBackStack();
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .replace(R.id.profile_container, appartment_detail).commit();
            }
        });

        FirebaseHandler.getInstance().getAppartment_segments()
                .child(apartment.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                segment_container.removeAllViews();
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Segment segment = data.getValue(Segment.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        addLayout(segment);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        FirebaseHandler.getInstance().getAdd_blocks()
                .child(apartment.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                            //    segment_container.removeAllViews();
                                int i=0;
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Block segment = data.getValue(Block.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        app_detail_block.addView(edittext(i,"",segment.getBlock_title()));
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
                .child(apartment.getApartmentUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (getActivity() != null) {
                            if (dataSnapshot != null) {
                                //    segment_container.removeAllViews();
                                int i=0;
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Parkings segment = data.getValue(Parkings.class);
                                        AppLogs.d("SEGMENT DATA", segment.toString());
                                        app_detail_parking.addView(edittext(i,"",segment.getParking_title()));
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


        FirebaseHandler.getInstance().getApartment_on_demand_service()
                .child(apartment.getApartmentUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        on_demand_container_detail.removeAllViews();
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            On_DemandServices on_demandServices = data.getValue(On_DemandServices.class);
                            addServiceLayout(on_demandServices);
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

    private void addServiceLayout(On_DemandServices on_demandServices) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.add_ondemand_service, null, false);
        on_demand_container_detail.addView(layout);
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
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Apartment Details");
        app_detail_block = (FlexboxLayout)view.findViewById(R.id.app_detail_block);
        app_detail_parking = (FlexboxLayout) view.findViewById(R.id.app_detail_parking);
        edit_app_details = (CircleImageView) view.findViewById(R.id.edit_app_details);
        apartment_detail_name = (TextView) view.findViewById(R.id.apartment_detail_name);
    //    appdetail_cost = (Spinner) view.findViewById(R.id.appdetail_cost);
     //   appdetail_vehicle = (Spinner) view.findViewById(R.id.appdetail_vehicle);
    //    appdetail_service = (Spinner) view.findViewById(R.id.appdetail_service);
        done_apartment = (Button) view.findViewById(R.id.done_apartment);
        on_demand_container_detail = (LinearLayout)view.findViewById(R.id.on_demand_container_detail);
        numberDaysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, numbersDay);
     //   appdetail_cost.setAdapter(numberDaysAdapter);
        segment_container = (LinearLayout) view.findViewById(R.id.segment_container);
        segment_service_adapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, segment_service_array);
    //    appdetail_service.setAdapter(segment_service_adapter);

        segment_vehicle_type_adapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, segment_vehicle_type_array);
      //  appdetail_vehicle.setAdapter(segment_vehicle_type_adapter);


//        appdetail_cost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getChildAt(0) != null) {
//                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
//                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });



//        appdetail_vehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getChildAt(0) != null) {
//                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
//                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        appdetail_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getChildAt(0) != null) {
//                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
//                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void addLayout(Segment segment) {
        LayoutInflater inflater;
        inflater = LayoutInflater.from(getActivity());

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.segment_container, null, false);
        segment_container.addView(layout);
        app_detail_cost = (TextView) layout.findViewById(R.id.app_detail_cost);
        segment_name = (TextView) layout.findViewById(R.id.segment_name);
        app_detail_vehicle = (TextView) layout.findViewById(R.id.app_detail_vehicle);
        app_detail_service = (TextView) layout.findViewById(R.id.app_detail_service);

        segment_name.setText(segment.getSegmentName());
        app_detail_cost.setText(segment.getSegment_cost());
        app_detail_service.setText(segment.getService_type());
        app_detail_vehicle.setText(segment.getVehicle_type());

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
    @Override
    public void onResume() {
        super.onResume();

    }
}
