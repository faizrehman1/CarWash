package com.example.faiz.carwash.Activities.Activities.UI;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.CustomerActivity;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.Activities.Activities.Model.Subscription;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by AST on 9/16/2017.
 */

public class Add_Subscription extends android.support.v4.app.Fragment {

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Spinner vehicle_type_spinner;
    public EditText vehicle_make, vehicle_reg_no, subs_end_date, subs_start_date;
    public RadioButton hold_subscription, resume_subscription, default_subscription;
    public String vehicleType[] = {"Small Car", "Car", "Heavy Car"};
    Calendar myCalendarStart, myCalendarEnd;
    public Button add_subscription_btn;
    Subscription subscription;
    Add_Customer_Object add_customer_object;
    public String key;
    DatePickerDialog datePickerDialogStart;
    DatePickerDialog datePickerDialogEnd;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_subscription, null);
        initializeView(view);
        myCalendarStart = Calendar.getInstance();
        myCalendarEnd = Calendar.getInstance();

        if (getArguments() != null) {
            if (getArguments().getParcelable("object") != null) {
                add_customer_object = getArguments().getParcelable("object");
                key = add_customer_object.getCust_Uid();
            }

            if (getArguments().getParcelable("subs") != null) {
                subscription = getArguments().getParcelable("subs");
                vehicle_make.setText(subscription.getVehicle_make());
                vehicle_reg_no.setText(subscription.getVehicle_Reg_no());
                key = subscription.getCust_sub_uid();
                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                subs_start_date.setText(sdf.format(subscription.getSubsription_start_date()));
                subs_end_date.setText(sdf.format(subscription.getSubsription_end_date()));
                hold_subscription.setChecked(subscription.isHold_subscription());
                resume_subscription.setChecked(subscription.isResume_subscription());
                default_subscription.setChecked(subscription.isDefault_subscription());
                myCalendarStart.setTimeInMillis(subscription.getSubsription_start_date());
                myCalendarEnd.setTimeInMillis(subscription.getSubsription_end_date());
            }
        }


        ArrayAdapter<String> adapterVehicleName = new ArrayAdapter<String>(
                getActivity(), android.R.layout.simple_spinner_item, vehicleType);
        vehicle_type_spinner.setAdapter(adapterVehicleName);


        final DatePickerDialog.OnDateSetListener dateStart = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarStart.set(Calendar.YEAR, year);
                myCalendarStart.set(Calendar.MONTH, monthOfYear);
                myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelStart();
            }

        };

        final DatePickerDialog.OnDateSetListener dateEnd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendarEnd.set(Calendar.YEAR, year);
                myCalendarEnd.set(Calendar.MONTH, monthOfYear);
                myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabelend();
            }

        };


        subs_start_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//your co
                    datePickerDialogStart = new DatePickerDialog(getActivity(), dateStart, myCalendarStart
                            .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                            myCalendarStart.get(Calendar.DAY_OF_MONTH));
                    if(datePickerDialogEnd !=null) {

                        int day = datePickerDialogEnd.getDatePicker().getDayOfMonth();
                        int month = datePickerDialogEnd.getDatePicker().getMonth();
                        int year = datePickerDialogEnd.getDatePicker().getYear();

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        datePickerDialogStart.getDatePicker().setMaxDate(calendar.getTimeInMillis() - 10000);

                    }

                    datePickerDialogStart.show();

                    return true;

                }
                return false;

            }
        });

        subs_end_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    datePickerDialogEnd = new DatePickerDialog(getActivity(), dateEnd, myCalendarEnd
                            .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                            myCalendarEnd.get(Calendar.DAY_OF_MONTH));
                   if(datePickerDialogStart !=null) {
                       int day = datePickerDialogStart.getDatePicker().getDayOfMonth();
                       int month = datePickerDialogStart.getDatePicker().getMonth();
                       int year = datePickerDialogStart.getDatePicker().getYear();

                       Calendar calendar = Calendar.getInstance();
                       calendar.set(year, month, day);
                       datePickerDialogEnd.getDatePicker().setMinDate(calendar.getTimeInMillis() + 10000);
                   }
                    datePickerDialogEnd.show();


                    return true;
                }
                return false;
            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomerActivity.getInstance().onBackPressed();
            }
        });

        add_subscription_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicle_make.getText().toString().equals("")) {
                    Snackbar.make(view, "Vehicle Make is Required Field!", Snackbar.LENGTH_SHORT).show();

                } else if (vehicle_reg_no.getText().toString().equals("")) {
                    Snackbar.make(view, "Vehicle Reg No Required!", Snackbar.LENGTH_SHORT).show();

                } else if ((!hold_subscription.isChecked() && !resume_subscription.isChecked() && !default_subscription.isChecked())) {
                    Snackbar.make(view, "Subscription is Required Field", Snackbar.LENGTH_SHORT).show();

                } else if (subs_start_date.getText().toString().equals("")) {
                    Snackbar.make(view, "Start Date is Required", Snackbar.LENGTH_SHORT).show();

                } else if (subs_end_date.getText().toString().equals("")) {
                    Snackbar.make(view, "End Date is Required", Snackbar.LENGTH_SHORT).show();

                } else {
                    DatabaseReference reference;
                    String subkey;
                    if (subscription == null) {
                        reference = FirebaseHandler.getInstance().getCustomer_subscription().child(key).push();
                        subkey = reference.getKey();
                    } else {
                        subkey = subscription.getSubs_uid();
                    }
                    subscription = new Subscription(vehicle_type_spinner.getSelectedItem().toString()
                            , vehicle_make.getText().toString(), vehicle_reg_no.getText().toString()
                            , myCalendarStart.getTimeInMillis(), hold_subscription.isChecked(), resume_subscription.isChecked()
                            , default_subscription.isChecked(), myCalendarEnd.getTimeInMillis(), subkey, key);
                    //    add_customer_object.setSubscription(subscription);

                    FirebaseHandler.getInstance().getCustomer_subscription()
                            .child(key).child(subkey).setValue(subscription, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Bundle bundle = new Bundle();
                            Add_Customer add_customer = new Add_Customer();
                            bundle.putParcelable("object", add_customer_object);
                            add_customer.setArguments(bundle);
                            getActivity().getSupportFragmentManager().popBackStack();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                                    .addToBackStack(null)
                                    .replace(R.id.customer_container, add_customer).commit();

                        }
                    });


                }


            }
        });
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Subscription");
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        vehicle_type_spinner = (Spinner) view.findViewById(R.id.vehicle_type_spinner);
        vehicle_make = (EditText) view.findViewById(R.id.vehicle_make);
        vehicle_reg_no = (EditText) view.findViewById(R.id.vehicle_reg_no);
        subs_start_date = (EditText) view.findViewById(R.id.subs_start_date);
        hold_subscription = (RadioButton) view.findViewById(R.id.hold_subscription);
        resume_subscription = (RadioButton) view.findViewById(R.id.resume_subscription);
        default_subscription = (RadioButton) view.findViewById(R.id.default_subscription);
        subs_end_date = (EditText) view.findViewById(R.id.subs_end_date);
        add_subscription_btn = (Button) view.findViewById(R.id.add_subscription_btn);
        subs_start_date.setShowSoftInputOnFocus(false);
        subs_end_date.setShowSoftInputOnFocus(false);
    }

    private void updateLabelStart() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        subs_start_date.setText(sdf.format(myCalendarStart.getTime()));
    }

    private void updateLabelend() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        subs_end_date.setText(sdf.format(myCalendarEnd.getTime()));
    }
    @Override
    public void onResume() {
        super.onResume();

    }
}
