package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


public class SearchResultsActivity extends AppCompatActivity {

    private static final String FLIGHT_DESTINATION_KEY = "com.example.easy_flights.flightDestinationKey";
    private static final String FLIGHT_ORIGIN_KEY = "com.example.easy_flights.flightOriginKey";
    private static final String FLIGHT_DATE_KEY = "com.example.easy_flights.flightDateKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        Flight searchedFlight=new Flight(FLIGHT_DESTINATION_KEY,FLIGHT_ORIGIN_KEY,FLIGHT_DATE_KEY);

    }

    //TODO: Make list of applicable flights with given origin and Destination,insert closest dates at first

    public static Intent intentFactory(Context context,Flight f){
        Intent intent = new Intent(context,SearchResultsActivity.class);
        intent.putExtra(FLIGHT_DESTINATION_KEY,f.getDestination());
        intent.putExtra(FLIGHT_ORIGIN_KEY,f.getOrigin());
        intent.putExtra(FLIGHT_DATE_KEY,f.getDate());
        return intent;
    }
    //    private void refreshDisplay() {
//        mFlightList = mFlightDAO.getFlights();
//        if (!mFlightList.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for (Flight log : mFlightList) {
//                sb.append(log.toString());
//            }
//            mMainDisplay.setText(sb.toString());
//        } else {
//            mMainDisplay.setText("No Bookings");
//
//        }
//
//    }
}