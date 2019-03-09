package com.example.faiz.carwash.Activities.Activities.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.faiz.carwash.Activities.Activities.Model.Parkings;
import com.example.faiz.carwash.R;

import java.util.ArrayList;

/**
 * Created by AST on 11/14/2017.
 */

public class Supervisor_ParkingAdapter extends ArrayAdapter<Parkings> {

    public ArrayList<Parkings> blockArrayList;
    public Context context;

    public Supervisor_ParkingAdapter(@NonNull Context context, ArrayList<Parkings> blockArrayList) {
        super(context, 0);
        this.context = context;
        this.blockArrayList  = blockArrayList;
    }

    @Nullable
    @Override
    public Parkings getItem(int position) {
        return blockArrayList.get(position);
    }

    @Override
    public int getCount() {
        return blockArrayList.size();
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
                                @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.supervisor_block,null);
        TextView text1 = (TextView)view.findViewById(R.id.text1);
        text1.setTag(blockArrayList.get(position));
        text1.setText(blockArrayList.get(position).getParking_title());

        return view;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        return createItemView(position, convertView, parent);
    }
}
