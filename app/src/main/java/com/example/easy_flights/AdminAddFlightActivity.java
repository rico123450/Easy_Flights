package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

public class AdminAddFlightActivity extends AppCompatActivity {
    private SharedPreferences mPreferences = null;

    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";

    EditText editTextFlightOrigin;
    EditText editTextFlightDest;
    EditText editTextFlightDate;

    private String flightOrigin;
    private String flightDest;
    private String flightDate;

    private FlightDAO mFlightDAO;


    Button addFlightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_flight);
        getPrefs();
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();

        editTextFlightOrigin = (EditText) findViewById(R.id.addFlightFlightOrigin);
        editTextFlightDest= (EditText) findViewById(R.id.addFlightFlightDest);
        editTextFlightDate= (EditText) findViewById(R.id.addFlightFlightDate);

        addFlightButton=(Button) findViewById(R.id.addFlightButton);

        addFlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flightOrigin=editTextFlightOrigin.getText().toString();
                flightDate=editTextFlightDate.getText().toString();
                flightDest=editTextFlightDest.getText().toString();

                if(flightOrigin.equals("")|flightDate.equals("")|flightDest.equals("")){
                    Toast.makeText(getApplicationContext(), "All fields must be filled in", Toast.LENGTH_SHORT).show();
                    return;
                }

                Flight flight = new Flight(flightDest,flightOrigin,flightDate);

                mFlightDAO.insert(flight);
                Toast.makeText(getApplicationContext(), "Flight Added", Toast.LENGTH_SHORT).show();

            }
        });









        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminAddFlightActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }
}