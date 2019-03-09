package com.example.faiz.carwash.Activities.Activities.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;
import com.example.faiz.carwash.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AST on 10/9/2017.
 */

public class Customer_list_Adapter extends BaseAdapter {

    public ArrayList<Add_Customer_Object> userModels;
    public Context mContext;
    public int mCurrentfilterLength;
    public ArrayList<Add_Customer_Object> backUplist;

    public Customer_list_Adapter(ArrayList<Add_Customer_Object> userModels, Context mContext) {
        this.userModels = userModels;
        this.mContext = mContext;
        this.backUplist = new ArrayList<>(userModels);
    }

    @Override
    public int getCount() {
        return userModels.size();
    }

    @Override
    public Object getItem(int i) {
        return userModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    //    View custom_view = layoutInflater.inflate(R.layout.cust_list_adapter,null);


        ViewHolder viewHolder ;
        if(view == null){
            view =  layoutInflater.inflate(R.layout.cust_list_adapter,null);
            viewHolder = new ViewHolder();
            viewHolder.cust_adapter_image = (CircleImageView)view.findViewById(R.id.cust_adapter_image);
            viewHolder.cust_adapter_user_name = (TextView)view.findViewById(R.id.cust_adapter_name);
            viewHolder.cust_adapter_user_app = (TextView)view.findViewById(R.id.cust_adapter_app_name);
            viewHolder.cust_adapter_rupee = (TextView)view.findViewById(R.id.cust_rupee_adapter);
            viewHolder.cust_adapter_block = (TextView)view.findViewById(R.id.cust_adapter_block);
            viewHolder.flat_no = (TextView)view.findViewById(R.id.flat_no);
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }





        Add_Customer_Object add_customer_object = userModels.get(i);


        Glide.with(mContext).load(add_customer_object.getCust_imagUrl()).placeholder(R.drawable.user).fitCenter().into(viewHolder.cust_adapter_image);
        viewHolder.cust_adapter_block.setText(add_customer_object.getCust_block());
        viewHolder.cust_adapter_rupee.setText("₹700");
        viewHolder.cust_adapter_user_app.setText(add_customer_object.getCust_apartment());
        viewHolder.cust_adapter_user_name.setText(add_customer_object.getCust_name());
        viewHolder.flat_no.setText("#"+add_customer_object.getCust_flat());



        return view;
    }

    public void add(Add_Customer_Object add_customer_object){
        backUplist.add(add_customer_object);
    }

    public void searchCustomer(String s) {
        int filterLength = s.length();
        s = s.toLowerCase();
        if (filterLength == 0 || filterLength < mCurrentfilterLength) {
            mCurrentfilterLength = filterLength;
            userModels.clear();
            userModels.addAll(backUplist);
            if (filterLength == 0) {
                notifyDataSetChanged();
                return;
            }
        }
        int i = 0;
        while (i < userModels.size()) {
            if (!userModels.get(i).getCust_name().toLowerCase().contains(s) || !userModels.get(i).getCust_name().toLowerCase().startsWith(String.valueOf(s.charAt(0))))
            //  || !mValues.get(i).getName().toLowerCase().startsWith(String.valueOf(s.charAt(0)))) {
            {
                userModels.remove(i);
            } else {
                i++;
            }

        }
        mCurrentfilterLength = filterLength;
        notifyDataSetChanged();
    }

    public void filterCustomer(String s, String toString) {
        int filterLength = s.length();

        userModels.clear();
        userModels.addAll(backUplist);

        s = s.toLowerCase();
        if (filterLength == 0 || filterLength < mCurrentfilterLength) {
            mCurrentfilterLength = filterLength;
            userModels.clear();
            userModels.addAll(backUplist);
            if (filterLength == 0) {
                notifyDataSetChanged();
                return;
            }
        }
        int i = 0;
        while (i < userModels.size()) {
            if (!userModels.get(i).getCust_apartment().toLowerCase().contains(s.toLowerCase()) || !userModels.get(i).getCust_apartment().toLowerCase().startsWith(String.valueOf(s.toLowerCase().charAt(0)))
              && !userModels.get(i).getCust_block().toLowerCase().contains(toString.toLowerCase()) || !userModels.get(i).getCust_block().toLowerCase().startsWith(String.valueOf(toString.toLowerCase().charAt(0))))
            {
                userModels.remove(i);
            } else {
                i++;
            }

        }
        mCurrentfilterLength = filterLength;
        notifyDataSetChanged();
    }


    private class ViewHolder{
        CircleImageView cust_adapter_image;
        TextView cust_adapter_user_name;
        TextView cust_adapter_user_app;
        TextView cust_adapter_rupee;
        TextView cust_adapter_block;
        TextView flat_no;
    }
}
