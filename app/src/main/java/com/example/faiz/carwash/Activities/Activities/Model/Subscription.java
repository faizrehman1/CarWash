package com.example.faiz.carwash.Activities.Activities.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AST on 10/10/2017.
 */

public class Subscription implements Parcelable {

    public String vehicle_type;
    public String vehicle_make;
    public String vehicle_Reg_no;
    public long subsription_start_date;
    public boolean hold_subscription;
    public boolean resume_subscription;
    public boolean default_subscription;
    public long subsription_end_date;
    public String subs_uid;
    public String cust_sub_uid;

    public Subscription() {
    }

    public Subscription(String vehicle_type, String vehicle_make, String vehicle_Reg_no, long subsription_start_date, boolean hold_subscription, boolean resume_subscription, boolean default_subscription, long subsription_end_date, String subs_uid, String cust_sub_uid) {
        this.vehicle_type = vehicle_type;
        this.vehicle_make = vehicle_make;
        this.vehicle_Reg_no = vehicle_Reg_no;
        this.subsription_start_date = subsription_start_date;
        this.hold_subscription = hold_subscription;
        this.resume_subscription = resume_subscription;
        this.default_subscription = default_subscription;
        this.subsription_end_date = subsription_end_date;
        this.subs_uid = subs_uid;
        this.cust_sub_uid = cust_sub_uid;
    }

    protected Subscription(Parcel in) {
        vehicle_type = in.readString();
        vehicle_make = in.readString();
        vehicle_Reg_no = in.readString();
        subsription_start_date = in.readLong();
        hold_subscription = in.readByte() != 0;
        resume_subscription = in.readByte() != 0;
        default_subscription = in.readByte() != 0;
        subsription_end_date = in.readLong();
        subs_uid = in.readString();
        cust_sub_uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vehicle_type);
        dest.writeString(vehicle_make);
        dest.writeString(vehicle_Reg_no);
        dest.writeLong(subsription_start_date);
        dest.writeByte((byte) (hold_subscription ? 1 : 0));
        dest.writeByte((byte) (resume_subscription ? 1 : 0));
        dest.writeByte((byte) (default_subscription ? 1 : 0));
        dest.writeLong(subsription_end_date);
        dest.writeString(subs_uid);
        dest.writeString(cust_sub_uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subscription> CREATOR = new Creator<Subscription>() {
        @Override
        public Subscription createFromParcel(Parcel in) {
            return new Subscription(in);
        }

        @Override
        public Subscription[] newArray(int size) {
            return new Subscription[size];
        }
    };

    public String getSubs_uid() {
        return subs_uid;
    }

    public void setSubs_uid(String subs_uid) {
        this.subs_uid = subs_uid;
    }

    public String getCust_sub_uid() {
        return cust_sub_uid;
    }

    public void setCust_sub_uid(String cust_sub_uid) {
        this.cust_sub_uid = cust_sub_uid;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_make() {
        return vehicle_make;
    }

    public void setVehicle_make(String vehicle_make) {
        this.vehicle_make = vehicle_make;
    }

    public String getVehicle_Reg_no() {
        return vehicle_Reg_no;
    }

    public void setVehicle_Reg_no(String vehicle_Reg_no) {
        this.vehicle_Reg_no = vehicle_Reg_no;
    }

    public long getSubsription_start_date() {
        return subsription_start_date;
    }

    public void setSubsription_start_date(long subsription_start_date) {
        this.subsription_start_date = subsription_start_date;
    }

    public boolean isHold_subscription() {
        return hold_subscription;
    }

    public void setHold_subscription(boolean hold_subscription) {
        this.hold_subscription = hold_subscription;
    }

    public boolean isResume_subscription() {
        return resume_subscription;
    }

    public void setResume_subscription(boolean resume_subscription) {
        this.resume_subscription = resume_subscription;
    }

    public boolean isDefault_subscription() {
        return default_subscription;
    }

    public void setDefault_subscription(boolean default_subscription) {
        this.default_subscription = default_subscription;
    }

    public long getSubsription_end_date() {
        return subsription_end_date;
    }

    public void setSubsription_end_date(long subsription_end_date) {
        this.subsription_end_date = subsription_end_date;
    }

}
