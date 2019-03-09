package com.example.faiz.carwash.Activities.Activities.Model;

/**
 * Created by AST on 9/28/2017.
 */

public class UserModel {

    public String fname;
    public String lname;
    public String email;
    public String passs;
    public String address;
    public String user_uid;
    public String imageUrl;
    public String user_type;

    public UserModel() {
    }

    public UserModel(String fname, String lname, String email, String passs, String address, String user_uid, String imageUrl) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.passs = passs;
        this.address = address;
        this.user_uid = user_uid;
        this.imageUrl = imageUrl;

    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasss() {
        return passs;
    }

    public void setPasss(String passs) {
        this.passs = passs;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static UserModel myObj;
    public static UserModel getInstance(String fname, String lname, String email, String passs, String address, String user_uid, String imageUrl){
        if(myObj == null){
            myObj = new UserModel(fname,lname,email,passs,address,user_uid,imageUrl);
        }
        return myObj;
    }
}
