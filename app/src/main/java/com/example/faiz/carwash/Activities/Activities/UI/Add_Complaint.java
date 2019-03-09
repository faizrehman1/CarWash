package com.example.faiz.carwash.Activities.Activities.UI;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.example.faiz.carwash.Activities.Activities.Activities.ComplaintActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.BoyListAdapter;
import com.example.faiz.carwash.Activities.Activities.Adapters.SupervisorListAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Supervisor;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AST on 9/21/2017.
 */

public class Add_Complaint extends android.support.v4.app.Fragment {

    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button add_complaint;
    public DatabaseReference firebase;
    public ArrayList<Supervisor> supervisorArrayList;
    public SupervisorListAdapter supervisorListAdapter;
    public Spinner complaint_supervisor,complaint_boy;
    public EditText complaint_text;
    public LinearLayout attach_complaint;
    public ArrayList<Supervisor> boyList;
    public BoyListAdapter boyListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.add_complaint,null);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView) toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Complaint");
        back_arrow = (ImageView)toolbar.findViewById(R.id.back_image);
        firebase = FirebaseDatabase.getInstance().getReference();
        add_complaint = (Button)view.findViewById(R.id.add_complaint);
        complaint_supervisor = (Spinner)view.findViewById(R.id.complaint_supervisor);
        complaint_boy = (Spinner)view.findViewById(R.id.complaint_boy);
        complaint_text = (EditText)view.findViewById(R.id.complaint_text);
        attach_complaint = (LinearLayout)view.findViewById(R.id.attach_complaint);
        supervisorArrayList = new ArrayList<>();
        boyList = new ArrayList<>();
        boyListAdapter = new BoyListAdapter(getActivity(),boyList);
        complaint_boy.setAdapter(boyListAdapter);
        supervisorListAdapter =  new SupervisorListAdapter(getActivity(),supervisorArrayList);
        complaint_supervisor.setAdapter(supervisorListAdapter);

        add_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.complaint_container, new Complaint_Details()).commit();
            }
        });

        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplaintActivity.getInstance().onBackPressed();

            }
        });

        firebase.child("add_supervisor").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
            if(dataSnapshot!=null){
                if(dataSnapshot.getValue()!=null){
                    for(DataSnapshot data:dataSnapshot.getChildren()){
                        Supervisor supervisor = data.getValue(Supervisor.class);
                        if(supervisor.getSuperVisor_type()==0){
                            boyList.add(supervisor);
                            boyListAdapter.notifyDataSetChanged();
                        }else if(supervisor.getSuperVisor_type()==2){
                            supervisorArrayList.add(supervisor);
                            supervisorListAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        complaint_supervisor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (adapterView.getChildAt(1) != null) {
                    ((TextView) adapterView.getChildAt(1)).setTextSize(12);
                    ((TextView) adapterView.getChildAt(1)).setTypeface(null, Typeface.BOLD);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        attach_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ComplaintActivity.doubleFlag = true;
        ComplaintActivity.flag = true;
    }
}
