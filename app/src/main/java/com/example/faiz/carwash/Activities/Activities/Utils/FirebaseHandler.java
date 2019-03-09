package com.example.faiz.carwash.Activities.Activities.Utils;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Faiz on 7/25/2017.
 */

public class FirebaseHandler {
    private DatabaseReference usersRef;
    private DatabaseReference add_customer;
    private DatabaseReference user_takeit_post;
    private DatabaseReference user_takeit_or_leaveit_post;
    private DatabaseReference user_privacy;
    private static final String TAG = "FirebaseHandler";
    private static FirebaseHandler ourInstance;
    private DatabaseReference firebaseRef;
    private FirebaseAuth authData;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private StorageReference mfirebaseStorage;
    private DatabaseReference activities_seen_by_user;
    private DatabaseReference add_apartment;
    private DatabaseReference add_supervisor;
    private DatabaseReference customer_subscription;
    private DatabaseReference add_blocks;
    public DatabaseReference appartment_segments;
    public DatabaseReference add_parking;
    public DatabaseReference add_owner_serivces;
    public DatabaseReference add_vehicle_type;
    public DatabaseReference apartment_on_demand_service;


    public static FirebaseHandler getInstance() {
        if (ourInstance == null) {
            AppLogs.d("rootRef", "" + FirebaseDatabase.getInstance().getReference());
            ourInstance = new FirebaseHandler();
        }
        return ourInstance;
    }

    private FirebaseHandler() {
        firebaseRef = FirebaseDatabase.getInstance().getReference();
        mfirebaseStorage = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(FirebaseStorage.getInstance().getReference()));
        initializeChildRefs();
    }

    //  DatabaseStorage
    public StorageReference getRootStorageRef() {
        return mfirebaseStorage;
    }

    //    DatabaseReference
    public DatabaseReference getRootFirebaseRef() {
        return firebaseRef;
    }

    private void initializeChildRefs() {
        usersRef = firebaseRef.child("users");
        add_customer = firebaseRef.child("customer");
        activities_seen_by_user = firebaseRef.child("activities-seen-by-user");
        add_apartment = firebaseRef.child("add_apartment");
        add_supervisor = firebaseRef.child("add_supervisor");
        customer_subscription = firebaseRef.child("customer_subscription");
        add_blocks = firebaseRef.child("add_blocks");
        appartment_segments = firebaseRef.child("apartment_segments");
        add_parking = firebaseRef.child("add_parkings");
        add_owner_serivces  = firebaseRef.child("add_owner_services");
        add_vehicle_type = firebaseRef.child("add_vehicle_type");
        apartment_on_demand_service = firebaseRef.child("apartment_ondemand_service");
    }


    public DatabaseReference getUser_leaveit_post() {
        return user_takeit_or_leaveit_post;
    }

    public DatabaseReference getUsersRef() {
        return usersRef;
    }


    public DatabaseReference getAdd_customer() {
        return add_customer;
    }

    public void setAdd_customer(DatabaseReference add_customer) {
        this.add_customer = add_customer;
    }

    public DatabaseReference getUser_privacy() {
        return user_privacy;
    }

    public DatabaseReference getActivitiesSeenByUser() {

        return activities_seen_by_user;
    }

    public DatabaseReference getAdd_apartment() {
        return add_apartment;
    }

    public DatabaseReference getAdd_supervisor() {
        return add_supervisor;
    }

    public DatabaseReference getCustomer_subscription() {
        return customer_subscription;
    }

    public DatabaseReference getAdd_blocks() {
        return add_blocks;
    }

    public DatabaseReference getAppartment_segments() {
        return appartment_segments;
    }

    public DatabaseReference getAdd_parking() {
        return add_parking;
    }

    public DatabaseReference getAdd_owner_serivces() {
        return add_owner_serivces;
    }

    public DatabaseReference getAdd_vehicle_type() {
        return add_vehicle_type;
    }

    public DatabaseReference getApartment_on_demand_service() {
        return apartment_on_demand_service;
    }
}
