package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 11/9/2017.
 */

public class On_DemandServices {

    public String service_name;
    public String service_cost;
    public String service_vehicleType;
    public String ondemand_serviceUid;
    public String ondemand_service_apartUid;


    public On_DemandServices() {
    }

    public On_DemandServices(String service_name, String service_cost, String service_vehicleType, String ondemand_serviceUid, String ondemand_service_apartUid) {
        this.service_name = service_name;
        this.service_cost = service_cost;
        this.service_vehicleType = service_vehicleType;
        this.ondemand_serviceUid = ondemand_serviceUid;
        this.ondemand_service_apartUid = ondemand_service_apartUid;
    }

    public String getOndemand_service_apartUid() {
        return ondemand_service_apartUid;
    }

    public void setOndemand_service_apartUid(String ondemand_service_apartUid) {
        this.ondemand_service_apartUid = ondemand_service_apartUid;
    }

    public String getOndemand_serviceUid() {
        return ondemand_serviceUid;
    }

    public void setOndemand_serviceUid(String ondemand_serviceUid) {
        this.ondemand_serviceUid = ondemand_serviceUid;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getService_cost() {
        return service_cost;
    }

    public void setService_cost(String service_cost) {
        this.service_cost = service_cost;
    }

    public String getService_vehicleType() {
        return service_vehicleType;
    }

    public void setService_vehicleType(String service_vehicleType) {
        this.service_vehicleType = service_vehicleType;
    }
}
