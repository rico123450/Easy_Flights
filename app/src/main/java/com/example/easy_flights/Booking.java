package com.example.easy_flights;

public class Booking {
    private int mUserId;
    private int mFlightID;
    private int mQuantity;

    public Booking(int userId, int flightID, int quantity) {
        mUserId = userId;
        mFlightID = flightID;
        mQuantity = quantity;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public int getFlightID() {
        return mFlightID;
    }

    public void setFlightID(int flightID) {
        mFlightID = flightID;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }
}
