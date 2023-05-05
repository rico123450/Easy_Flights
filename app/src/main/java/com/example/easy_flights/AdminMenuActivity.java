package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easy_flights.databinding.ActivityAdminMenuBinding;
import com.example.easy_flights.databinding.ActivityMainBinding;

public class AdminMenuActivity extends AppCompatActivity implements AdminAddUser.AdminAddUserListener {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private Button addUserButton;

    private Button adminViewUserButton;

    private String entered_Username;
    private String entered_Password;
//    AdminMenuActivity binding;
    ActivityAdminMenuBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        getPrefs();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding=ActivityAdminMenuBinding.inflate(getLayoutInflater());
        addUserButton=binding.adminAddUser;
        adminViewUserButton=binding.adminViewUsers;

        adminViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });
    }

    public void openDialog() {
        AdminAddUser adminAddUser = new AdminAddUser();
        adminAddUser.show(getSupportFragmentManager(),"example");


    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminMenuActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    @Override
    public void applyTexts(String username, String password) {
        entered_Password=password;
        entered_Username=username;

    }
}