package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;


import java.util.List;


public class SearchResultsActivity extends AppCompatActivity {

    private List<Flight> mFlightList;

    private Flight searchedFlight;
    private FlightDAO mFlightDAO;
    private static final String FLIGHT_DESTINATION_KEY = "com.example.easy_flights.flightDestinationKey";
    private static final String FLIGHT_ORIGIN_KEY = "com.example.easy_flights.flightOriginKey";
    private static final String FLIGHT_DATE_KEY = "com.example.easy_flights.flightDateKey";

    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        searchedFlight=new Flight(FLIGHT_DESTINATION_KEY,FLIGHT_ORIGIN_KEY,FLIGHT_DATE_KEY);
        mFlightList = mFlightDAO.getFlights();

        //sortFlightList();

        mListView = (ListView) findViewById(R.id.flightSearchResults);


        ArrayAdapter<Flight>arrayAdapter = new ArrayAdapter(this,R.layout.activity_flight_search_results,R.id.textView_SearchResults,mFlightList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void sortFlightList() {
        String searchedFlightDest=searchedFlight.getDestination().toLowerCase().trim();
        String searchedFlightOrigin=searchedFlight.getOrigin().toLowerCase().trim();
        String searchedFlightDate=searchedFlight.getDate().toLowerCase().trim();



        for(Flight f:mFlightList){
            String fCleanedDest = f.getDestination().toLowerCase().trim();
            String fCleanedOrigin = f.getOrigin().toLowerCase().trim();
            String fCleanedDate = f.getDate().toLowerCase().trim();


            if(fCleanedOrigin.equals(searchedFlightOrigin)) {
                if(fCleanedDest.equals(searchedFlightDest)) {
                    if (fCleanedDate.equals(searchedFlightDate)) {
                        Flight temp = new Flight(f.getDestination(), f.getOrigin(), f.getDate());
                        mFlightList.remove(f);
                        mFlightList.add(0, temp);
                    }
                }else{
                    mFlightList.remove(f);
                }
            }else{
                mFlightList.remove(f);
            }
        }
    }

    //TODO: Make list of applicable flights with given origin and Destination,insert closest dates at first

    public static Intent intentFactory(Context context,Flight f){
        Intent intent = new Intent(context,SearchResultsActivity.class);
        intent.putExtra(FLIGHT_DESTINATION_KEY,f.getDestination());
        intent.putExtra(FLIGHT_ORIGIN_KEY,f.getOrigin());
        intent.putExtra(FLIGHT_DATE_KEY,f.getDate());
        return intent;
    }


//        private void createSearchResults() {
//        mFlightList = mFlightDAO.getFlights();
//        if (!mFlightList.isEmpty()) {
//            StringBuilder sb = new StringBuilder();
//            for (Flight log : mFlightList) {
//                sb.append(log.toString());
//            }
//        }
//
//    }
}