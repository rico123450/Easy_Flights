package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

import java.util.List;

public class RemoveBookingActivity extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private static final String USER_NAME_KEY = "com.example.easy_flights.userNameKey";

    private String mUserName;

    private User mUser;

    private List<Flight> mFlightList;

    private List<Booking> mBookingList;

    private FlightDAO mFlightDAO;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static RemoveBookingActivity removeBookingContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_booking);
        removeBookingContext=this;
        getPrefs();
        sortData();
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        mUser=mFlightDAO.getUserByUsername(mUserName);


        mBookingList=mFlightDAO.getBookingByUserId(mUser.getUserId());

        mRecyclerView=findViewById(R.id.bookingRecView);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter = new BookingAdapter(mBookingList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);










        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sortData(){
        mUserName=getIntent().getStringExtra(USER_NAME_KEY);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    public static Context getRemoveBookingActivityContext(){
        return removeBookingContext;
    }

    public static Intent intentFactory(Context context, String userName){
        Intent intent = new Intent(context,RemoveBookingActivity.class);
        intent.putExtra(USER_NAME_KEY,userName);

        return intent;
    }

}