package com.example.easy_flights.DB;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.easy_flights.Booking;
import com.example.easy_flights.Flight;
import com.example.easy_flights.User;

import java.util.List;

@Dao
public interface FlightDAO {

    @Insert
    void insert(Flight...flights);

    @Update
    void update(Flight...flights);

    @Delete
    void delete(Flight flight);

    @Query("SELECT * FROM "+ AppDataBase.FLIGHT_TABLE)
    List<Flight> getFlights();

    @Query("SELECT * FROM "+ AppDataBase.FLIGHT_TABLE + " WHERE mFlightId = :flightId")
    List<Flight> getFlightsById(int flightId);


    @Query("SELECT * FROM "+ AppDataBase.FLIGHT_TABLE + " WHERE mFlightId = :flightId")
    Flight getFlightById(int flightId);

    @Query("SELECT * FROM "+ AppDataBase.FLIGHT_TABLE+" WHERE mDestination LIKE :destination AND mOrigin LIKE :origin")
    List<Flight> getFlightByDestinationOrigin(String destination,String origin);


    @Query("SELECT * FROM "+ AppDataBase.FLIGHT_TABLE+" WHERE mDestination LIKE :destination AND mOrigin LIKE :origin AND mDate LIKE :Date")
    List<Flight> getFlightByDestinationOriginDate(String destination,String origin,String Date);

    @Insert
    void insert(User...users);

    @Update
    void update(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE+ " WHERE mUserName = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDataBase.USER_TABLE+ " WHERE mUserId = :userId")
    User getUserByUserId(int userId);


    @Insert
    void insert(Booking...bookings);
    @Update
    void update(Booking...bookings);

    @Delete
    void delete(Booking booking);



    @Query("SELECT * FROM " + AppDataBase.BOOKING_TABLE+" WHERE mUserId=:userId")
    List<Booking> getBookingByUserId(int userId);

    @Query("SELECT * FROM " + AppDataBase.BOOKING_TABLE+" WHERE mFlightID=:FlightID")
    List<Booking> getBookingByFlightId(int FlightID);


}
