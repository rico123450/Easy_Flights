package com.example.easy_flights;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.easy_flights.DB.AppDataBase;

import java.util.Objects;

@Entity(tableName = AppDataBase.BOOKING_TABLE)
public class Booking {

    @PrimaryKey(autoGenerate = true)
    private int mBookingId;
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

    public int getBookingId() {
        return mBookingId;
    }

    public void setBookingId(int bookingId) {
        mBookingId = bookingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return mUserId == booking.mUserId && mFlightID == booking.mFlightID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mUserId, mFlightID);
    }
}
