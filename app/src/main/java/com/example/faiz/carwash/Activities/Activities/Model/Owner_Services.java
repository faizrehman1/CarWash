package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 11/9/2017.
 */

public class Owner_Services {

    public String service_title;
    public String service_cost;
    public String vehicle_type;
    public String service_uid;


    public Owner_Services() {
    }

    public Owner_Services(String service_title, String service_cost, String vehicle_type, String service_uid) {
        this.service_title = service_title;
        this.service_cost = service_cost;
        this.vehicle_type = vehicle_type;
        this.service_uid = service_uid;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getService_title() {
        return service_title;
    }

    public void setService_title(String service_title) {
        this.service_title = service_title;
    }

    public String getService_uid() {
        return service_uid;
    }

    public void setService_uid(String service_uid) {
        this.service_uid = service_uid;
    }
}
