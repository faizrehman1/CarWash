package com.example.faiz.carwash.Activities.Activities.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Activities.MainActivity;
import com.example.faiz.carwash.Activities.Activities.Activities.Message_NotificationActivity;
import com.example.faiz.carwash.Activities.Activities.Adapters.SuperVisor_ApartmentAdapter;
import com.example.faiz.carwash.Activities.Activities.Model.Apartment;
import com.example.faiz.carwash.Activities.Activities.Utils.FirebaseHandler;
import com.example.faiz.carwash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by AST on 9/22/2017.
 */

public class Send_Message extends android.support.v4.app.Fragment {
    public static TextView ActionBartitle;
    public ImageView back_arrow;
    public Button send_message;
    public Spinner apart_msg_spinner;
    public DatabaseReference firebase;
    public SuperVisor_ApartmentAdapter apartmentAdapter;
    public ArrayList<Apartment> apartments;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.send_message,null);

        Toolbar toolbar= (Toolbar)view.findViewById(R.id.toolbar_outside);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayOptions(0, android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);

        ActionBartitle = (TextView)toolbar.findViewById(R.id.main_appbar_textView);
        ActionBartitle.setText("Send Message");
        apartments = new ArrayList<>();
        apart_msg_spinner = (Spinner)view.findViewById(R.id.apart_msg_spinner);
        apartmentAdapter = new SuperVisor_ApartmentAdapter(getActivity(),apartments);
        apart_msg_spinner.setAdapter(apartmentAdapter);
        firebase = FirebaseDatabase.getInstance().getReference();
        back_arrow = (ImageView) toolbar.findViewById(R.id.back_image);
        send_message = (Button)view.findViewById(R.id.send_message);

        send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main) != null) {
//                    getActivity().getSupportFragmentManager()
//                            .beginTransaction().
//                            remove(getActivity().getSupportFragmentManager().findFragmentById(R.id.container_main)).commit();
//                }

                getFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_out_left, R.anim.slide_right, R.anim.slide_out_right)
                        .addToBackStack(null)
                        .add(R.id.message_container, new Message_Fragment()).commit();


            }
        });

        FirebaseHandler.getInstance().getAdd_apartment().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot!=null){
                    if(dataSnapshot.getValue()!=null){
                        for(DataSnapshot data:dataSnapshot.getChildren()){
                            Apartment apartment = data.getValue(Apartment.class);
                            apartments.add(apartment);
                            apartmentAdapter.notifyDataSetChanged();
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
                MainActivity.getInstance().onBackPressed();
            }
        });

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        Message_NotificationActivity.flag = true;
        Message_NotificationActivity.doubleFlag = true;
    }

}
