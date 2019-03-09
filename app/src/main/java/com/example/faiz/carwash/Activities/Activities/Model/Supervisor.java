package com.example.faiz.carwash.Activities.Activities.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AST on 10/17/2017.
 */

public class Supervisor implements Parcelable {

    //type 0 for boys
    //type 1 for cheif Supervisor
    //type 2 for Supervisor


    public String superVisor_name;
    public String superVisor_email;
    public String superVisor_mobile;
    public String superVisor_address;
    public String superVisor_apartment;
    public String superVisor_block;
    public int superVisor_type;
    public String super_visorImgUrl;
    public String superVisor_id;

    public Supervisor() {
    }

    public Supervisor(String superVisor_name, String superVisor_email, String superVisor_mobile, String superVisor_address, String superVisor_apartment, String superVisor_block, int superVisor_type, String super_visorImgUrl, String superVisor_id) {
        this.superVisor_name = superVisor_name;
        this.superVisor_email = superVisor_email;
        this.superVisor_mobile = superVisor_mobile;
        this.superVisor_address = superVisor_address;
        this.superVisor_apartment = superVisor_apartment;
        this.superVisor_block = superVisor_block;
        this.superVisor_type = superVisor_type;
        this.super_visorImgUrl = super_visorImgUrl;
        this.superVisor_id = superVisor_id;
    }


    protected Supervisor(Parcel in) {
        superVisor_name = in.readString();
        superVisor_email = in.readString();
        superVisor_mobile = in.readString();
        superVisor_address = in.readString();
        superVisor_apartment = in.readString();
        superVisor_block = in.readString();
        superVisor_type = in.readInt();
        super_visorImgUrl = in.readString();
        superVisor_id = in.readString();
    }

    public static final Creator<Supervisor> CREATOR = new Creator<Supervisor>() {
        @Override
        public Supervisor createFromParcel(Parcel in) {
            return new Supervisor(in);
        }

        @Override
        public Supervisor[] newArray(int size) {
            return new Supervisor[size];
        }
    };

    public String getSuperVisor_name() {
        return superVisor_name;
    }

    public void setSuperVisor_name(String superVisor_name) {
        this.superVisor_name = superVisor_name;
    }

    public String getSuperVisor_email() {
        return superVisor_email;
    }

    public void setSuperVisor_email(String superVisor_email) {
        this.superVisor_email = superVisor_email;
    }

    public String getSuperVisor_mobile() {
        return superVisor_mobile;
    }

    public void setSuperVisor_mobile(String superVisor_mobile) {
        this.superVisor_mobile = superVisor_mobile;
    }

    public String getSuperVisor_address() {
        return superVisor_address;
    }

    public void setSuperVisor_address(String superVisor_address) {
        this.superVisor_address = superVisor_address;
    }

    public String getSuperVisor_apartment() {
        return superVisor_apartment;
    }

    public void setSuperVisor_apartment(String superVisor_apartment) {
        this.superVisor_apartment = superVisor_apartment;
    }

    public String getSuperVisor_block() {
        return superVisor_block;
    }

    public void setSuperVisor_block(String superVisor_block) {
        this.superVisor_block = superVisor_block;
    }

    public int getSuperVisor_type() {
        return superVisor_type;
    }

    public void setSuperVisor_type(int superVisor_type) {
        this.superVisor_type = superVisor_type;
    }

    public String getSuperVisor_id() {
        return superVisor_id;
    }

    public void setSuperVisor_id(String superVisor_id) {
        this.superVisor_id = superVisor_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(superVisor_name);
        parcel.writeString(superVisor_email);
        parcel.writeString(superVisor_mobile);
        parcel.writeString(superVisor_address);
        parcel.writeString(superVisor_apartment);
        parcel.writeString(superVisor_block);
        parcel.writeInt(superVisor_type);
        parcel.writeString(super_visorImgUrl);
        parcel.writeString(superVisor_id);
    }

    public String getSuper_visorImgUrl() {
        return super_visorImgUrl;
    }

    public void setSuper_visorImgUrl(String super_visorImgUrl) {
        this.super_visorImgUrl = super_visorImgUrl;
    }
}
