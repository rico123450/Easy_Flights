package com.example.easy_flights;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.MenuInflater;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;


import java.util.List;

//TODO:IMPLEMENT BACK BUTTON,DATE SEARCH.
public class SearchResultsActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = " com.example.easy_flights.userIdKey";

    private static final String PREFERENCE_KEY = " com.example.easy_flights.PREFERENCE_KEY";

    private List<Flight> mFlightList;

    private SharedPreferences mPreferences = null;

    private Flight searchedFlight;
    private FlightDAO mFlightDAO;

    private Button backButton;
    private static final String FLIGHT_DESTINATION_KEY = "com.example.easy_flights.flightDestinationKey";
    private static final String FLIGHT_ORIGIN_KEY = "com.example.easy_flights.flightOriginKey";
    private static final String FLIGHT_DATE_KEY = "com.example.easy_flights.flightDateKey";

    private String mFlightDestination;

    private String mFlightOrigin;

    private String mDate;

    private String mUsername;

    private static final String USER_NAME_KEY_BOOKING = "com.example.easy_flights.flightUserBookingKey";

    private User mUser;



     //SearchResultsActivity SearchBinding;


    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
       // SearchBinding=FlightSearchResultsActivity.inflate(getLayoutInflater());
       // SearchBinding=FlightSearchResultsActivity.inflate(getLayoutInflater());
        //backButton=SearchBinding.backButton;
        sortData();





        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
//        try{
//            mUser=mFlightDAO.getUserByUserId(Integer.parseInt(USER_NAME_KEY_BOOKING));
//        }catch(NumberFormatException e){
//            System.out.println("Could not find user");
//        }
//        System.out.println("FLIGHT_DESTINATION_KEY:\n"+FLIGHT_DESTINATION_KEY);
//        System.out.println("USER_NAME_KEY_BOOKING:\n"+USER_NAME_KEY_BOOKING);
        mUser=mFlightDAO.getUserByUsername(mUsername);
        addUserToPreference(mUser.getUserId());

//        System.out.println("USER IS:\n"+mUser);

        searchedFlight=new Flight(mFlightDestination,mFlightOrigin,mDate);

        if(searchedFlight.getDate().equals("")){
            mFlightList = mFlightDAO.getFlightByDestinationOrigin(searchedFlight.getDestination(), searchedFlight.getOrigin());
        }else{
            mFlightList=mFlightDAO.getFlights();
        }



        //sortFlightList();

        mListView = (ListView) findViewById(R.id.flightSearchResults);


        ArrayAdapter<Flight>arrayAdapter = new ArrayAdapter(this,R.layout.activity_flight_search_results,R.id.textView_SearchResults,mFlightList);
        mListView.setAdapter(arrayAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                askUserToBook(i);

            }
        });

//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//               Intent intent = MainActivity.intentFactory(getApplicationContext(),mUser.getUserId());
//               startActivity(intent);
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public static Intent intentFactory(Context context,String flightDestination,String flightOrigin,String flightDate,String userName){
        Intent intent = new Intent(context,SearchResultsActivity.class);
        intent.putExtra(FLIGHT_DESTINATION_KEY,flightDestination);
        intent.putExtra(FLIGHT_ORIGIN_KEY,flightOrigin);
        intent.putExtra(FLIGHT_DATE_KEY,flightDate);
        intent.putExtra(USER_NAME_KEY_BOOKING,userName);
        return intent;
    }

    private void askUserToBook(int positionOnArray){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.bookFlight);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       bookFlight(positionOnArray);
                    }
                });

        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //
                    }
                });
        alertBuilder.create().show();

    }



    private void bookFlight(int positionOnArray){
        //TODO:QUANTITY NOT CHANGING KEEP ADDING NEW FLIGHTS
        List<Booking> userBooking = mFlightDAO.getBookingByUserId(mUser.getUserId());
        for(Booking booking:userBooking){
            if(booking.getFlightID()==searchedFlight.getFlightId()){
                booking.setQuantity(booking.getQuantity()+1);
                return;
            }
        }
        System.out.println("ENTERED OUTSIDE");
        Booking currentBooking= new Booking(mUser.getUserId(),mFlightList.get(positionOnArray).getFlightId(),1);
        mFlightDAO.insert(currentBooking);
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

    void sortData(){
        mFlightDestination=getIntent().getStringExtra(FLIGHT_DESTINATION_KEY);
        mFlightOrigin=getIntent().getStringExtra(FLIGHT_ORIGIN_KEY);
        mUsername=getIntent().getStringExtra(USER_NAME_KEY_BOOKING);
        mDate=getIntent().getStringExtra(FLIGHT_DATE_KEY);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    private void addUserToPreference(int userId) {
        if(mPreferences==null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY,userId);
    }
}