package com.example.faiz.carwash.Activities.Activities.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AST on 10/10/2017.
 */

public class Add_Customer_Object implements Parcelable {
    public String cust_name;
    public String cust_mobile;
    public String cust_apartment;
    public String cust_parking_slot;
    public  String cust_block;
    public String cust_email;
    public String cust_flat;
    public String cust_parking;
    public String cust_Uid;
    public String cust_imagUrl;

    public Add_Customer_Object() {
    }

    public Add_Customer_Object(String cust_name, String cust_mobile, String cust_apartment, String cust_parking_slot, String cust_block, String cust_email, String cust_flat, String cust_parking,String cust_Uid, String cust_imagUrl) {
        this.cust_name = cust_name;
        this.cust_mobile = cust_mobile;
        this.cust_apartment = cust_apartment;
        this.cust_parking_slot = cust_parking_slot;
        this.cust_block = cust_block;
        this.cust_email = cust_email;
        this.cust_flat = cust_flat;
        this.cust_parking = cust_parking;
        this.cust_Uid = cust_Uid;
        this.cust_imagUrl = cust_imagUrl;
    }


    protected Add_Customer_Object(Parcel in) {
        cust_name = in.readString();
        cust_mobile = in.readString();
        cust_apartment = in.readString();
        cust_parking_slot = in.readString();
        cust_block = in.readString();
        cust_email = in.readString();
        cust_flat = in.readString();
        cust_parking = in.readString();
        cust_Uid = in.readString();
        cust_imagUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(cust_name);
        dest.writeString(cust_mobile);
        dest.writeString(cust_apartment);
        dest.writeString(cust_parking_slot);
        dest.writeString(cust_block);
        dest.writeString(cust_email);
        dest.writeString(cust_flat);
        dest.writeString(cust_parking);
        dest.writeString(cust_Uid);
        dest.writeString(cust_imagUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Add_Customer_Object> CREATOR = new Creator<Add_Customer_Object>() {
        @Override
        public Add_Customer_Object createFromParcel(Parcel in) {
            return new Add_Customer_Object(in);
        }

        @Override
        public Add_Customer_Object[] newArray(int size) {
            return new Add_Customer_Object[size];
        }
    };

    public String getCust_name() {
        return cust_name;
    }

    public void setCust_name(String cust_name) {
        this.cust_name = cust_name;
    }

    public String getCust_mobile() {
        return cust_mobile;
    }

    public void setCust_mobile(String cust_mobile) {
        this.cust_mobile = cust_mobile;
    }

    public String getCust_apartment() {
        return cust_apartment;
    }

    public void setCust_apartment(String cust_apartment) {
        this.cust_apartment = cust_apartment;
    }

    public String getCust_parking_slot() {
        return cust_parking_slot;
    }

    public void setCust_parking_slot(String cust_parking_slot) {
        this.cust_parking_slot = cust_parking_slot;
    }

    public String getCust_block() {
        return cust_block;
    }

    public void setCust_block(String cust_block) {
        this.cust_block = cust_block;
    }

    public String getCust_email() {
        return cust_email;
    }

    public void setCust_email(String cust_email) {
        this.cust_email = cust_email;
    }

    public String getCust_flat() {
        return cust_flat;
    }

    public void setCust_flat(String cust_flat) {
        this.cust_flat = cust_flat;
    }

    public String getCust_parking() {
        return cust_parking;
    }

    public void setCust_parking(String cust_parking) {
        this.cust_parking = cust_parking;
    }

    public String getCust_Uid() {
        return cust_Uid;
    }

    public void setCust_Uid(String cust_Uid) {
        this.cust_Uid = cust_Uid;
    }

    public String getCust_imagUrl() {
        return cust_imagUrl;
    }

    public void setCust_imagUrl(String cust_imagUrl) {
        this.cust_imagUrl = cust_imagUrl;
    }

}
