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

public class AdminUserListActivity extends AppCompatActivity {
    private FlightDAO mFlightDAO;

    private SharedPreferences mPreferences = null;

    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";

    private List<User> mUserList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        getPrefs();



        mUserList=mFlightDAO.getAllUsers();
        RecyclerView recView=findViewById(R.id.recViewUsers);
        recView.setLayoutManager(new LinearLayoutManager(this));
        recView.setAdapter(new userViewAdapter(getApplicationContext(),mUserList));




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminUserListActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }
}