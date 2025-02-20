package com.example.bicyclefinder;

import androidx.annotation.NonNull;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Bike implements Serializable  {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("frameNumber")
    @Expose
    private String frameNumber;
    @SerializedName("kindOfBicycle")
    @Expose
    private String kindOfBicycle;
    @SerializedName("brand")
    @Expose
    private String brand;
    @SerializedName("colors")
    @Expose
    private String colors;
    @SerializedName("place")
    @Expose
    private String place;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("missingFound")
    @Expose
    private String missingFound;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fireBaseUserId")
    @Expose
    private String firebaseUserId;

    public Bike(){

    }

    public Bike(String frameNumber, String kindOfBicycle, String brand, String colors, String place, String date, int userId, String name,  String phone, String missingFound, String firebaseUserId) {
        this.frameNumber = frameNumber;
        this.kindOfBicycle = kindOfBicycle;
        this.brand = brand;
        this.colors=colors;
        this.place=place;
        this.date=date;
        this.name=name;
        this.phone=phone;
        this.missingFound=missingFound;
        this.userId=userId;
        this.firebaseUserId =firebaseUserId;
        
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getKindOfBicycle() {return kindOfBicycle;}

    //public void setKindOfBicycle(Spinner kindOfBicycle) {
    //    this.kindOfBicycle = kindOfBicycle;
    //}

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }

    public void setdate(String date) {
        this.date = date;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMissingFound() {
        return missingFound;
    }

    public String setMissingFound(String missingFound) {
        return missingFound;
    }
    
    public String getName() {return name;}
    
    public void setName(String name) {this.name = name;}
    
    public String getPhone() {return phone;}
    
    public void setPhone(String phone) {this.phone = phone;}

    public String getFirebaseUserId() {return firebaseUserId;}
    public void setFirebaseUserId(String firebaseUserId) {this.firebaseUserId=firebaseUserId;}


  //  public void setMissingFound(Spinner missingFound) {
    //    this.missingFound = missingFound;
    //}



    @NonNull
    @Override
    public String toString() {
        return  "Mærke: " + brand + "\n" + "Stelnummer: " + frameNumber + "\n" + "Type: " + kindOfBicycle + "\n"
                + "Farve: " + colors + "\n" + "Sted: " + place + "\n" + "Navn: " + name + "\n" + "Telefon nr: " +phone + "\n" + "Fundet/Stjålet: " + missingFound + "\n\n" ; }
}

