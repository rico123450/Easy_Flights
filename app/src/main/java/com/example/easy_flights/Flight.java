package com.example.easy_flights;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.easy_flights.DB.AppDataBase;

import java.util.Objects;

@Entity(tableName = AppDataBase.FLIGHT_TABLE)
public class Flight {

    @PrimaryKey(autoGenerate = true)
    private int mFlightId;
    private String mDestination;
    private String mOrigin;
    private String mDate;

//    private int mUserId;

//    public Flight(String destination, String origin, String date,int userId) {
//        mDestination = destination;
//        mOrigin = origin;
//        mDate = date;
//        mUserId=userId;
//    }

    public Flight(String destination, String origin, String date) {
        mDestination = destination;
        mOrigin = origin;
        mDate = date;
    }



    @Override
    public String toString() {
        return
                "Destination: " + mDestination + '\n' +
                "Origin:" + mOrigin + '\n' +
                "Date:" + mDate + '\n' ;
    }


    //TODO: Might need to change to anticipate Date

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(mDestination, flight.mDestination) && Objects.equals(mOrigin, flight.mOrigin) && Objects.equals(mDate, flight.mDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mDestination, mOrigin, mDate);
    }

    public int getFlightId() {
        return mFlightId;
    }

    public void setFlightId(int flightId) {
        mFlightId = flightId;
    }

    public String getDestination() {
        return mDestination;
    }

    public void setDestination(String destination) {
        mDestination = destination;
    }

    public String getOrigin() {
        return mOrigin;
    }

    public void setOrigin(String origin) {
        mOrigin = origin;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

//    public int getUserId() {
//        return mUserId;
//    }
//
//    public void setUserId(int userId) {
//        mUserId = userId;
//    }
}
