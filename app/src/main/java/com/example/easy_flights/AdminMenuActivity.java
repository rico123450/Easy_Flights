package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;
import com.example.easy_flights.databinding.ActivityAdminMenuBinding;
import com.example.easy_flights.databinding.ActivityMainBinding;

import java.util.List;

public class AdminMenuActivity extends AppCompatActivity implements AdminAddUser.AdminAddUserListener {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    Button addUserButton;

    Button adminViewUserButton;

    private String entered_Username;
    private String entered_Password;
    private static AdminMenuActivity adminMenuContext;





//    AdminMenuActivity binding;
    ActivityAdminMenuBinding binding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);
        binding=ActivityAdminMenuBinding.inflate(getLayoutInflater());
        addUserButton=(Button) findViewById(R.id.adminAddUser);
        adminMenuContext=this;

        adminViewUserButton=(Button) findViewById(R.id.adminViewUsers);

        getPrefs();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();






            }
        });

        adminViewUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminUserListActivity.intentFactory(getApplicationContext());
                startActivity(intent);


            }
        });

    }


    public void openDialog() {
        AdminAddUser adminAddUser = new AdminAddUser();
        adminAddUser.show(getSupportFragmentManager(),"AdminAddUser");


    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminMenuActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

     public static Context getAdminMenuActivityContext(){
        return adminMenuContext;
    }

    @Override
    public void applyTexts(String username, String password) {
        entered_Password=password;
        entered_Username=username;

    }
}