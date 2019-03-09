package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 11/8/2017.
 */

public class Parkings {

    public String parking_title;
    public String parking_uid;

    public Parkings() {
    }

    public Parkings(String parking_title, String parking_uid) {
        this.parking_title = parking_title;
        this.parking_uid = parking_uid;
    }

    public String getParking_title() {
        return parking_title;
    }

    public void setParking_title(String parking_title) {
        this.parking_title = parking_title;
    }

    public String getParking_uid() {
        return parking_uid;
    }

    public void setParking_uid(String parking_uid) {
        this.parking_uid = parking_uid;
    }
}
