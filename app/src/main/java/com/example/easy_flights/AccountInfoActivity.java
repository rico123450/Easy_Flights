package com.example.easy_flights;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;
import com.example.easy_flights.databinding.ActivityAccountInfoBinding;

import java.util.List;

public class AccountInfoActivity extends AppCompatActivity {
    private User mUser;

    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private static final String USER_ID_KEY = "com.example.easy_flights.userIdKey";

    private String mUserName;

    private FlightDAO mFlightDAO;
    private static final String USER_NAME_KEY = "com.example.easy_flights.userNameKey";

    private TextView mTextViewUsername;

    private TextView mTextViewPassword;

    private TextView mTextViewID;

    private Button mDeleteAccountButton;

    private int mUserId;

    ActivityAccountInfoBinding binding;



    AccountInfoActivity mAccountInfoActivityContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);
        mAccountInfoActivityContext=this;
        getPrefs();
        sortData();
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        mUser=mFlightDAO.getUserByUserId(mUserId);

//        System.out.println("\n\n\n\n\n\nUSERNAME="+mUserName+"\n\n\n\n\n\n");


        mTextViewID=(TextView) findViewById(R.id.TextViewDisplayUserId);
        mTextViewPassword=(TextView) findViewById(R.id.TextViewDisplayPassword);
        mTextViewUsername=(TextView) findViewById(R.id.TextViewDisplayUser);
        mDeleteAccountButton=(Button) findViewById(R.id.userRemoveOwnAccount);

        mTextViewUsername.setText("Username: "+mUser.getUserName());
        mTextViewID.setText("User ID: "+Integer.toString(mUser.getUserId()));
        mTextViewPassword.setText("Password: "+mUser.getPassword());


        mDeleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mAccountInfoActivityContext);

                alertBuilder.setMessage("WARNING:\nDo you want to delete your account? \nThis cannot be undone...");

                alertBuilder.setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                logOut();
                                mFlightDAO.delete(mUser);
                                Intent intent = LoginActivity.intentFactory(getApplicationContext());
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Account Removed", Toast.LENGTH_SHORT).show();

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
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void logOut(){
        //TODO: IMPLEMENT THESE FUNCTIONS FROM MAIN ACTIVITY
        clearUserFromIntent();
        clearUserFromPref();
        mUserId = -1;
        checkForUser();
    }

    private void sortData(){
        mUserId=getIntent().getIntExtra(USER_ID_KEY,-1);
    }

    public static Intent intentFactory(Context context,int userId){
        Intent intent = new Intent(context,AccountInfoActivity.class);
        intent.putExtra(USER_ID_KEY,userId);
        return intent;
    }
    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    private void checkForUser() {
        //Do we have user in intent?
        mUserId=getIntent().getIntExtra(USER_ID_KEY,-1);

        if(mUserId !=-1){
            return;


        }

        if(mPreferences==null){
            getPrefs();
        }
        mUserId = mPreferences.getInt(USER_ID_KEY,-1);



        if(mUserId != -1){
            return;
        }


        List<User> users = mFlightDAO.getAllUsers();
        if(users.size() <=0){
            User defaultUser =  new User("testuser1","123",false);
            User altUser = new User("admin2", "123",true);
            mFlightDAO.insert(defaultUser);
            mFlightDAO.insert(altUser);
        }

        //TODO: See if this is working as intended
        List<Flight>flights = mFlightDAO.getFlights();

        if(flights.size()<=0){
            Flight defaultFlight1= new Flight("San Jose","San Francisco","8/1/23");
            Flight defaultFlight2 = new Flight("San Francisco","San Jose","8/1/23");
            mFlightDAO.insert(defaultFlight1);
            mFlightDAO.insert(defaultFlight2);
        }

        Intent intent = LoginActivity.intentFactory(this);
        startActivity(intent);
        //Do we have user in the preferences?

    }

    private void clearUserFromIntent(){
        getIntent().putExtra(USER_ID_KEY,-1);
    }

    private void clearUserFromPref() {
        addUserToPreference(-1);
    }



    private void addUserToPreference(int userId) {
        if(mPreferences==null){
            getPrefs();
        }
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(USER_ID_KEY,userId);
        editor.commit();
    }

}