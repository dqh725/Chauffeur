package com.chauffr.home.adapter;

/**
 * Created by Wenyue on 1/02/2016.
 */
public class Vehicle {


    String description;
    int seats;
    float fareMin, fareKmsFirst, fareKmsAfter;


    public Vehicle(String description, int seats){
        this.description = description;
        this.seats = seats;
    }

    public Vehicle(String description, int seats, float fareMin, float fareKmsFirst, float fareKmsAfter){
        this.description = description;
        this.seats = seats;
        this.fareMin = fareMin;
        this.fareKmsFirst = fareKmsFirst;
        this.fareKmsAfter = fareKmsAfter;
    }



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }





}
