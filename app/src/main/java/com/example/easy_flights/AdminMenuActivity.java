package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import com.example.easy_flights.databinding.ActivityAdminMenuBinding;
import com.example.easy_flights.databinding.ActivityMainBinding;

public class AdminMenuActivity extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private Button addUserButton;

    private Button adminViewUserButton;

//    AdminMenuActivity binding;
    ActivityAdminMenuBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        binding=ActivityAdminMenuBinding.inflate(getLayoutInflater());
        getPrefs();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addUserButton=binding.adminAddUser;
        adminViewUserButton=binding.adminAddUser;
    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminMenuActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }
}