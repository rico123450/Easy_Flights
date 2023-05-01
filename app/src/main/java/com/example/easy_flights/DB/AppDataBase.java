package com.example.easy_flights.DB;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.easy_flights.Flight;
import com.example.easy_flights.User;

@Database(entities = {Flight.class, User.class},version = 3)
public abstract class AppDataBase extends RoomDatabase {
    public static final String FLIGHT_TABLE = "flight_table";
    public static final String DATABASE_NAME ="Flight.DB";

    public static final String USER_TABLE = "USER_TABLE";

    public static final String BOOKING_TABLE="BOOKING_TABLE";

    private static volatile AppDataBase instance;
    private static final Object LOCK = new Object();

    public abstract FlightDAO FlightDAO();


    public static AppDataBase getInstance(Context context){

        if(instance==null){
            synchronized (LOCK){
                if(instance==null){
                    instance= Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class,DATABASE_NAME)
                            .fallbackToDestructiveMigration()//dr.c edit
                            .allowMainThreadQueries()//dr.c edit
                            .build();
                }
            }
        }
        return instance;
    }
}
