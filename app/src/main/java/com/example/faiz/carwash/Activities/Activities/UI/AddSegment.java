package com.example.faiz.carwash.Activities.Activities.UI;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_ServiceAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_VehicleTypeAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Owner_Services;
import com.example.faiz.carwash.Activities.Activities.Model.Segment;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by AST on 9/15/2017.
 */

public class AddSegment extends android.support.v4.app.Fragment {

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Spinner segment_service, segment_number, segment_vehicle_type;
    public EditText segment_name;
    public EditText segment_cost;
    //Daily, Weekly, Monthly, Twice Monthly &  Once
    public String numbersDay[] = {"Daily", "Weekly", "Monthly", "Twice Monthly", "Once"};
    public ArrayAdapter<String> numberDaysAdapter;
    public String segment_service_array[] = {"Interior Detailing", "Exterior Detailing", "Washing", "Mopping"};
    public Supervisor_ServiceAdapter segment_service_adapter;
    public String segment_vehicle_type_array[] = {"Small Car", "Mid/Premium Car", "Suv", "Bike"};
    public Supervisor_VehicleTypeAdapter segment_vehicle_type_adapter;
    public Button save_segment;
    public ArrayList<Segment> segmentArrayList;
    public Apartment segmentObject;
    //    public DatabaseReference reference;
    public String key;
    public Segment segment;
    public String segmentkey = "";
    public ArrayList<Owner_Services> vehicle_typeObjectArrayList;
    public DatabaseReference reference;
    public ArrayList<Owner_Services> owner_servicesArrayList;
    public boolean SegmentFlag = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_segment_view, null);
        initializeView(view);


        if (getArguments() != null) {
            if (getArguments().getParcelable("object") != null) {
                segmentObject = getArguments().getParcelable("object");
                key = segmentObject.getApartmentUid();
            }
            //      add_apart_name.setText(segmentObject.getApartmentName());
            if (getArguments().getParcelable("segment") != null) {
                // if (segmentObject.getSegmentArrayList().size() > 0) {
                //  segment.add(segmentObject.getSegmentArrayList().get(0));
                //          container_seg.setVisibility(View.VISIBLE);
                segment = getArguments().getParcelable("segment");
                segment_cost.setText(segment.getSegment_cost());
                //   segment_vehicle_type.setText(segment.getVehicle_type());
                //  segment_service.setText(segment.getService_type());
                segment_name.setText(segment.getSegmentName());
                //  segment_vehicle_type.setSelection(segment_vehicle_type_adapter.getPosition(segment.getVehicle_type()));
                //  segment_service.setSelection(segment_service_adapter.getPosition(segment.getService_type()));
                //  segment_number.setSelection(numberDaysAdapter.getPosition(segment.getServiceNumber()));
                segmentkey = segment.getSegment_UID();
                //   segment_number.set(segment.getServiceNumber());

                //   }
            }
        }

        FirebaseHandler.getInstance().getAdd_owner_serivces().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot != null) {
                            if (dataSnapshot.getValue() != null) {
                                for (DataSnapshot data : dataSnapshot.getChildren()) {
                                    Owner_Services vehicle_typeObject = data.getValue(Owner_Services.class);
                                    owner_servicesArrayList.add(vehicle_typeObject);
                                    vehicle_typeObjectArrayList.add(vehicle_typeObject);
                                    segment_service_adapter.notifyDataSetChanged();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

//        FirebaseHandler.getInstance().getAdd_vehicle_type().child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        if(dataSnapshot!=null){
//                            if(dataSnapshot.getValue()!=null){
//                                for(DataSnapshot data:dataSnapshot.getChildren()){
//                                    Vehicle_TypeObject vehicle_typeObject = data.getValue(Vehicle_TypeObject.class);
//                                    vehicle_typeObjectArrayList.add(vehicle_typeObject);
//                                    segment_service_adapter.notifyDataSetChanged();
//                                }
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });


        segment_number.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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


        segment_vehicle_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        segment_service.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Owner_Services owner_services = (Owner_Services) view.findViewById(R.id.text1).getTag();
                setVehicleType(owner_services);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });


        save_segment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                if (segment_name.getText().toString().length() == 0 || check_validation(segment_name.getText().toString())) {
                    Snackbar.make(view, "Enter Valid Segment Name", Snackbar.LENGTH_SHORT).show();
                } else {
                    FirebaseHandler.getInstance().getAppartment_segments().child(key).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot != null) {
                                if (dataSnapshot.getValue() != null) {
                                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                                        Segment segment = data.getValue(Segment.class);
                                        if (segment.getSegmentName().toLowerCase().equals(segment_name.getText().toString().toLowerCase())) {
                                            Snackbar.make(view, "Segment name already Exist", Snackbar.LENGTH_SHORT).show();
                                            SegmentFlag = false;
                                            break;
                                        } else {
                                            SegmentFlag = true;
                                        }
                                    }
                                    add_Segmment(SegmentFlag);
                                }else{
                                    SegmentFlag = true;
                                    add_Segmment(SegmentFlag);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });




                }
            }
        });

        return view;
    }

    private void add_Segmment(boolean segment) {
        final Segment segmentt;
        DatabaseReference databaseReference;
        if(segment) {
            Owner_Services owner_services = (Owner_Services) segment_service.getSelectedItem();
            Owner_Services vehicleType = (Owner_Services)segment_vehicle_type.getSelectedItem();

            if (segmentkey.equals("")) {




                databaseReference = FirebaseHandler.getInstance().getAppartment_segments()
                        .child(key).push();
                segmentt = new Segment(segment_name.getText().toString(),
                        segment_number.getSelectedItem().toString(),
                        owner_services.getService_title(),
                        owner_services.getVehicle_type(),
                        segment_cost.getText().toString(), databaseReference.getKey(), key);
            } else {
                segmentt = new Segment(segment_name.getText().toString(),
                        segment_number.getSelectedItem().toString(), owner_services.getService_title(),
                        owner_services.getVehicle_type(),
                        segment_cost.getText().toString(), segmentkey, key);

                databaseReference = FirebaseHandler.getInstance().getAppartment_segments()
                        .child(key).child(segmentkey);

            }


            //   segmentObject.setSegmentArrayList(segmentArrayList);

            databaseReference.setValue(segmentt, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Add_Appartment add_appartment = new Add_Appartment();
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("object", segmentObject);
                    bundle.putParcelable("segment", segmentt);
                    add_appartment.setArguments(bundle);
                          getActivity().getSupportFragmentManager().popBackStack();
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                            .addToBackStack(null)
                            .replace(R.id.profile_container, add_appartment).commit();
                }
            });

        }else{

        }
    }

    private void setVehicleType(Owner_Services owner_services) {

        vehicle_typeObjectArrayList.clear();
        vehicle_typeObjectArrayList.addAll(owner_servicesArrayList);
        for (int i = 0; i < owner_servicesArrayList.size(); i++) {
            if (vehicle_typeObjectArrayList.get(i).getService_uid().equals(owner_services.getService_uid())) {
                segment_vehicle_type.setSelection(i);
                segment_vehicle_type_adapter.notifyDataSetChanged();
            } else {
                vehicle_typeObjectArrayList.set(i, null);
                segment_vehicle_type_adapter.notifyDataSetChanged();
            }
        }

        setCost(owner_services);
    }

    private void setCost(Owner_Services owner_services) {
        segment_cost.setText(owner_services.getService_cost());
    }

    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Segment");
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        segment_name = (EditText) view.findViewById(R.id.segment_name);
        segment_number = (Spinner) view.findViewById(R.id.segment_number);
        segment_service = (Spinner) view.findViewById(R.id.segment_service);
        segment_vehicle_type = (Spinner) view.findViewById(R.id.segment_vehicle_type);
        segment_cost = (EditText) view.findViewById(R.id.segment_cost);
        save_segment = (Button) view.findViewById(R.id.save_segment);
        owner_servicesArrayList = new ArrayList<>();
        segmentArrayList = new ArrayList<>();
        vehicle_typeObjectArrayList = new ArrayList<>();
        numberDaysAdapter = new ArrayAdapter<String>(getActivity(), R.layout.segment_custom_spinner_text, numbersDay);
        segment_number.setAdapter(numberDaysAdapter);
        reference = FirebaseDatabase.getInstance().getReference();
        segment_service_adapter = new Supervisor_ServiceAdapter(getActivity(), owner_servicesArrayList);
        segment_service.setAdapter(segment_service_adapter);

        segment_vehicle_type_adapter = new Supervisor_VehicleTypeAdapter(getActivity(), vehicle_typeObjectArrayList);
        segment_vehicle_type.setAdapter(segment_vehicle_type_adapter);

        segment_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    hideKeyboard(view);
                }
            }
        });

    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean check_validation(String inputStr) {

        boolean flag;

        Pattern pattern = Pattern.compile(new String("^[a-zA-Z]+( [a-zA-z]+)*$"));
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            //if pattern matches
            flag = false;
        } else {
            flag = true;
            //if pattern does not matches
        }
    return flag;
    }
}
