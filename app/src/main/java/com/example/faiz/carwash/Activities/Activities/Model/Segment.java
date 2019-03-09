package com.example.faiz.carwash.Activities.Activities.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AST on 10/16/2017.
 */

public class Segment implements Parcelable {

    public String segmentName;
    public String serviceNumber;
    public String service_type;
    public String vehicle_type;
    public String segment_cost;
    public String segment_UID;
    public String segment_app_UID;


    public Segment() {
    }

    public Segment(String segmentName, String serviceNumber, String service_type, String vehicle_type, String segment_cost, String segment_UID, String segment_app_UID) {
        this.segmentName = segmentName;
        this.serviceNumber = serviceNumber;
        this.service_type = service_type;
        this.vehicle_type = vehicle_type;
        this.segment_cost = segment_cost;
        this.segment_UID = segment_UID;
        this.segment_app_UID = segment_app_UID;
    }


    protected Segment(Parcel in) {
        segmentName = in.readString();
        serviceNumber = in.readString();
        service_type = in.readString();
        vehicle_type = in.readString();
        segment_cost = in.readString();
        segment_UID = in.readString();
        segment_app_UID = in.readString();
    }

    public static final Creator<Segment> CREATOR = new Creator<Segment>() {
        @Override
        public Segment createFromParcel(Parcel in) {
            return new Segment(in);
        }

        @Override
        public Segment[] newArray(int size) {
            return new Segment[size];
        }
    };

    public String getSegmentName() {
        return segmentName;
    }

    public void setSegmentName(String segmentName) {
        this.segmentName = segmentName;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getSegment_cost() {
        return segment_cost;
    }

    public void setSegment_cost(String segment_cost) {
        this.segment_cost = segment_cost;
    }

    public String getSegment_UID() {
        return segment_UID;
    }

    public void setSegment_UID(String segment_UID) {
        this.segment_UID = segment_UID;
    }

    public String getSegment_app_UID() {
        return segment_app_UID;
    }

    public void setSegment_app_UID(String segment_app_UID) {
        this.segment_app_UID = segment_app_UID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(segmentName);
        parcel.writeString(serviceNumber);
        parcel.writeString(service_type);
        parcel.writeString(vehicle_type);
        parcel.writeString(segment_cost);
        parcel.writeString(segment_UID);
        parcel.writeString(segment_app_UID);
    }
}
