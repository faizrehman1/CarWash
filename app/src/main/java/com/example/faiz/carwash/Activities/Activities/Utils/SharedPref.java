package com.example.faiz.carwash.Activities.Activities.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.faiz.carwash.Activities.Activities.Model.Add_Customer_Object;

/**
 * Created by AST on 10/25/2017.
 */

public class SharedPref {
    private static String NAME = "packageName";
    private static String cust_name = "cname";
    private static String cust_mobile = "cmobile";
    private static String cust_apartment = "cappart";
    private static String cust_parking_slot = "cparkslot";
    private static String cust_block = "cblock";
    private static String cust_email = "cemail";
    private static String cust_flat = "cflat";
    private static String cust_parking = "cparking";
    private static String cust_Uid = "cuid";
    private static String cust_imagUrl = "cimgurl";




    public static void setCurrentUser(Context context, Add_Customer_Object user) {
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        preferences.edit().putString(cust_name, user.getCust_name()).apply();
        preferences.edit().putString(cust_mobile, user.getCust_mobile()).apply();
        preferences.edit().putString(cust_apartment, user.getCust_apartment()).apply();
        preferences.edit().putString(cust_parking_slot, user.getCust_parking_slot()).apply();
        preferences.edit().putString(cust_block, user.getCust_block()).apply();
        preferences.edit().putString(cust_email, user.getCust_email()).apply();
        preferences.edit().putString(cust_flat, user.getCust_flat()).apply();
        preferences.edit().putString(cust_parking,user.getCust_parking()).apply();
        preferences.edit().putString(cust_Uid, user.getCust_Uid()).apply();
        preferences.edit().putString(cust_imagUrl,user.getCust_imagUrl()).apply();
    }

    public static Add_Customer_Object getCurrentUser(Context context) {
        Add_Customer_Object user = new Add_Customer_Object();
        SharedPreferences preferences = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        user.setCust_name(preferences.getString(cust_name, ""));
        user.setCust_mobile(preferences.getString(cust_mobile, ""));
        user.setCust_apartment(preferences.getString(cust_apartment, ""));
        user.setCust_parking_slot(preferences.getString(cust_parking_slot, ""));
        user.setCust_block(preferences.getString(cust_block, ""));
        user.setCust_email(preferences.getString(cust_email, ""));
        user.setCust_flat(preferences.getString(cust_flat, ""));
        user.setCust_parking(preferences.getString(cust_parking, ""));
        user.setCust_Uid(preferences.getString(cust_Uid, ""));
        user.setCust_imagUrl(preferences.getString(cust_imagUrl, ""));
        return user;
    }
}
