package com.example.faiz.carwash.Activities.Activities.UI;

import android.app.AlertDialog;
import android.os.Bundle;
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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Adapters.SuperVisor_ApartmentAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.Supervisor_BlockAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Model.Block;
import com.example.faiz.carwash.Activities.Activities.Model.Supervisor;
import com.example.faiz.carwash.Activities.Activities.Utils.AppLogs;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
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

public class Add_Supervisor_Boy extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public EditText supervisor_name, supervisor_mobile, supervisor_email, supervisor_address;
    public RadioButton super_boy, super_cheif, super_visor;
    public Spinner supervisor_app, supervisor_block;
    public String apartmentName[];
    ///    public String blockName[] = {"Tower 1", "Tower 2", "Tower 3", "Tower 4", "Tower 5", "Tower 6", "Tower 7", "Tower 8", "Tower 9", "Tower 10"};
    Supervisor_BlockAdapter adapterBlockName;
    SuperVisor_ApartmentAdapter adapterApartmentName;
    public Button add_supervisor_btn;
    public Supervisor segment;
    public ArrayList<Block> arrayList_block;
    public LinearLayout add_block;
    public ArrayList<Apartment> apartments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_supervisor, null);
        initializeView(view);
        //   Pattern pattern = Pattern.compile("^[0-9]");
        //    Matcher matcher = pattern.matcher(userID);

        if (getArguments() != null) {
            segment = getArguments().getParcelable("object");
            supervisor_name.setText(segment.getSuperVisor_name());
            supervisor_email.setText(segment.getSuperVisor_email());
            supervisor_mobile.setText(segment.getSuperVisor_mobile());
            supervisor_address.setText(segment.getSuperVisor_address());

            if (segment.getSuperVisor_type() == 0) {
                super_boy.setChecked(true);
            } else if (segment.getSuperVisor_type() == 1) {
                super_cheif.setChecked(true);
            } else if (segment.getSuperVisor_type() == 2) {
                super_visor.setChecked(true);
            }


        }




        FirebaseHandler.getInstance().getAdd_apartment().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        int i = 0;
                    //    apartmentName = new String[(int) dataSnapshot.getChildrenCount()];
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            AppLogs.d("TAG_SNAP", snapshot.getValue().toString());
                            Apartment apartment = snapshot.getValue(Apartment.class);
                      //      apartmentName[i] = apartment.getApartmentName();
                            apartments.add(apartment);
                            adapterApartmentName.notifyDataSetChanged();
                            i++;
                        }


                       // if (segment != null) {
                        //    supervisor_app.setSelection(adapterApartmentName.getPosition(segment.getSuperVisor_apartment()));
                         //   supervisor_block.setSelection(adapterBlockName.getPosition(segment.getSuperVisor_block()));
                      //  }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        supervisor_app.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getChildAt(0) != null) {
//                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
//                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
//
//                }
             //   String item = ((TextView)view.findViewById(R.id.offer_type_txt)).getText().toString();
              //  selectedOffer.setText(item);

                Apartment apartment = (Apartment) view.findViewById(R.id.text1).getTag();
               // supervisor_app.setSelection(adapterApartmentName.getPosition(apartment));
                getblocks(apartment);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        supervisor_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (adapterView.getChildAt(0) != null) {
//                    ((TextView) adapterView.getChildAt(0)).setTextSize(10);
//                    ((TextView) adapterView.getChildAt(0)).setTypeface(null, Typeface.BOLD);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_supervisor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if (supervisor_name.getText().toString().length() == 0 || !supervisor_name.getText().toString().matches("^[a-zA-Z]+( [a-zA-z]+)*$")) {
                    Snackbar.make(view, "Fill Name field", Snackbar.LENGTH_SHORT).show();
                    supervisor_name.setError("Enter Valid Name");
                } else if (supervisor_mobile.getText().toString().length() == 0 || supervisor_mobile.getText().toString().length() <10 || supervisor_mobile.getText().toString().length() >10) {
                     Snackbar.make(view, "Fill Mobile field", Snackbar.LENGTH_SHORT).show();
                     supervisor_mobile.setError("Enter Valid Mobile Number");
                 }else if (supervisor_email.getText().toString().length() == 0 || !android.util.Patterns.EMAIL_ADDRESS.matcher(supervisor_email.getText().toString()).matches()) {
                    Snackbar.make(view, "Fill Name field", Snackbar.LENGTH_SHORT).show();
                    supervisor_email.setError("Enter Valid Email Address");
                } else if (supervisor_address.getText().toString().length() == 0 || !supervisor_address.getText().toString().matches("^[a-zA-Z]+( [a-zA-z]+)*$")) {
                    Snackbar.make(view, "Fill Address field", Snackbar.LENGTH_SHORT).show();
                    supervisor_address.setError("Enter Address");
                } else if (!super_boy.isChecked() && !super_cheif.isChecked() && !super_visor.isChecked()) {
                    Snackbar.make(view, "Select One Role", Snackbar.LENGTH_SHORT).show();
                } else {
                    DatabaseReference reference = FirebaseHandler.getInstance().getAdd_supervisor().push();
                    String super_visorName = supervisor_name.getText().toString();
                    String super_visorEmail = supervisor_email.getText().toString();
                    String super_visorMobile = supervisor_mobile.getText().toString();
                    String super_visorAddress = supervisor_address.getText().toString();
                    int super_visor_type = 0;

                    if (super_boy.isChecked()) {
                        super_visor_type = 0;
                    } else if (super_cheif.isChecked()) {
                        super_visor_type = 1;
                    } else if (super_visor.isChecked()) {
                        super_visor_type = 2;
                    }

                    String key;

                    if (segment != null) {
                        if (segment.getSuperVisor_id().equals("")) {
                            key = reference.getKey().toString();
                        } else {
                            key = segment.getSuperVisor_id().toString();
                        }
                    } else {
                        key = reference.getKey().toString();
                    }

                    Apartment apartment = (Apartment) supervisor_app.getSelectedItem();
                    Block block = (Block) supervisor_block.getSelectedItem();

                    final Supervisor supervisor = new Supervisor(super_visorName, super_visorEmail, super_visorMobile
                            , super_visorAddress, apartment.getApartmentName()
                            , block.getBlock_title()
                            , super_visor_type, "", key);
                    FirebaseHandler.getInstance().getAdd_supervisor().child(key).setValue(supervisor, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Supervisor_Profile supervisor_profile = new Supervisor_Profile();
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("object", supervisor);
                            supervisor_profile.setArguments(bundle);
                            getActivity().getFragmentManager().popBackStack();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .setCustomAnimations(R.anim.slide_right, R.anim.slide_out_right, R.anim.slide_left, R.anim.slide_out_left)
                                    .replace(R.id.profile_container, supervisor_profile).commit();
                        }
                    });
                }
            }
        });


        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStack();
            }
        });

        return view;
    }

    private void getblocks(Apartment apartment) {
        FirebaseHandler.getInstance().getAdd_blocks().child(apartment.getApartmentUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList_block.clear();
                if (dataSnapshot != null) {
                    if (dataSnapshot.getValue() != null) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Block block = snapshot.getValue(Block.class);
                            arrayList_block.add(new Block(block.block_title,block.getBlock_uid()));
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

    private void initializeView(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Add Supervisor/Boy");
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        add_supervisor_btn = (Button) view.findViewById(R.id.add_supervisor_btn);
        supervisor_name = (EditText) view.findViewById(R.id.supervisor_name);
        supervisor_mobile = (EditText) view.findViewById(R.id.supervisor_mobile);
        supervisor_email = (EditText) view.findViewById(R.id.supervisor_email);
        supervisor_address = (EditText) view.findViewById(R.id.supervisor_address);
        supervisor_app = (Spinner) view.findViewById(R.id.supervisor_app);
        supervisor_block = (Spinner) view.findViewById(R.id.supervisor_block);
        super_boy = (RadioButton) view.findViewById(R.id.super_boy);
        super_cheif = (RadioButton) view.findViewById(R.id.super_cheif);
        super_visor = (RadioButton) view.findViewById(R.id.super_visor);
        add_block = (LinearLayout) view.findViewById(R.id.add_block);
        arrayList_block = new ArrayList<>();
        adapterBlockName = new Supervisor_BlockAdapter(getActivity(),arrayList_block);
        supervisor_block.setAdapter(adapterBlockName);
        apartments = new ArrayList<>();
        adapterApartmentName = new SuperVisor_ApartmentAdapter(getActivity(), apartments);
        supervisor_app.setAdapter(adapterApartmentName);




        add_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                final AlertDialog alertDialog1 = alertDialog.create();
                View view1 = getActivity().getLayoutInflater().inflate(R.layout.add_block_supervisor, null);
                Button add_dialog_ms = (Button) view1.findViewById(R.id.add_dialog_ms);
                Button cancle_dialog_ms = (Button)view1.findViewById(R.id.cancle_dialog_ms);
                final EditText editText = (EditText) view1.findViewById(R.id.block_name);

                add_dialog_ms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                  //      FirebaseHandler.getInstance().getAdd_blocks()
                     //           .push()
                     //           .setValue(editText.getText().toString(), new DatabaseReference.CompletionListener() {
                      //              @Override
                      //              public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        alertDialog1.dismiss();
                     //               }
                       //         });
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
    @Override
    public void onResume() {
        super.onResume();

    }
}
