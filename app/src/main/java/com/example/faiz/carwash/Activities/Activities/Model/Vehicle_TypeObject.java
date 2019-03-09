package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 11/9/2017.
 */

public class Vehicle_TypeObject {

    public String vehicle_type;
    public String vehicle_uid;


    public Vehicle_TypeObject() {
    }

    public Vehicle_TypeObject(String vehicle_type, String vehicle_uid) {
        this.vehicle_type = vehicle_type;
        this.vehicle_uid = vehicle_uid;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getVehicle_uid() {
        return vehicle_uid;
    }

    public void setVehicle_uid(String vehicle_uid) {
        this.vehicle_uid = vehicle_uid;
    }
}
