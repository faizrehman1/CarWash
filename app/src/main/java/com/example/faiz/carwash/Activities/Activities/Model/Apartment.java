package com.example.faiz.carwash.Activities.Activities.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by AST on 10/16/2017.
 */

public class Apartment implements Parcelable {
    public String apartmentName;
    public String apartmentBlock;
    public String apartmentParking;
    public String apartmentUid;
  //  public ArrayList<Segment> segmentArrayList;


    public Apartment() {
    }



    public Apartment(String apartmentName, String apartmentBlock, String apartmentParking, String apartmentUid) {
        this.apartmentName = apartmentName;
        this.apartmentBlock = apartmentBlock;
        this.apartmentParking = apartmentParking;
        this.apartmentUid = apartmentUid;
    }


    public Apartment(Parcel in) {
        apartmentName = in.readString();
        apartmentBlock = in.readString();
        apartmentParking = in.readString();
        apartmentUid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apartmentName);
        dest.writeString(apartmentBlock);
        dest.writeString(apartmentParking);
        dest.writeString(apartmentUid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Apartment> CREATOR = new Creator<Apartment>() {
        @Override
        public Apartment createFromParcel(Parcel in) {
            return new Apartment(in);
        }

        @Override
        public Apartment[] newArray(int size) {
            return new Apartment[size];
        }
    };

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getApartmentBlock() {
        return apartmentBlock;
    }

    public void setApartmentBlock(String apartmentBlock) {
        this.apartmentBlock = apartmentBlock;
    }

    public String getApartmentParking() {
        return apartmentParking;
    }

    public void setApartmentParking(String apartmentParking) {
        this.apartmentParking = apartmentParking;
    }

    public String getApartmentUid() {
        return apartmentUid;
    }

    public void setApartmentUid(String apartmentUid) {
        this.apartmentUid = apartmentUid;
    }


}
