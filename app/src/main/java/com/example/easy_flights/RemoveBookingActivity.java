package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class RemoveBookingActivity extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private static final String USER_NAME_KEY = "com.example.easy_flights.userNameKey";

    private String mUserName;

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_booking);
        getPrefs();
        sortData();





        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sortData(){
        mUserName=getIntent().getStringExtra(USER_NAME_KEY);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    public static Intent intentFactory(Context context, String userName){
        Intent intent = new Intent(context,RemoveBookingActivity.class);
        intent.putExtra(USER_NAME_KEY,userName);

        return intent;
    }

}