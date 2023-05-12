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

public class AdminRemoveFlightActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;

    private FlightAdapter mAdapter;

    private RecyclerView.LayoutManager mLayoutManager;
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;
    private FlightDAO mFlightDAO;

    List<Flight> mFlightList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_remove_flight);
        getPrefs();
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();

        mFlightList=mFlightDAO.getFlights();

        mRecyclerView=findViewById(R.id.recViewRemoveFlight);

        mLayoutManager=new LinearLayoutManager(this);
        mAdapter=new FlightAdapter(mFlightList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnFlightClickListener(new FlightAdapter.OnFlightClickListener() {
            @Override
            public void onFlightClick(int position) {

            }
        });






        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminRemoveFlightActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }
}