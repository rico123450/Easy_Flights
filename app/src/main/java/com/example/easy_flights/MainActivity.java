package com.example.easy_flights;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;
import com.example.easy_flights.databinding.ActivityMainBinding;

import java.util.List;
//TODO: Change admin check from string to getting ifAdmin
public class MainActivity extends AppCompatActivity {
    private static final String USER_ID_KEY = "com.example.easy_flights.userIdKey";
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private TextView mMainDisplay;

    private EditText mOrigin;
    private EditText mDestination;
    private EditText mDate;

    private Button mSubmit;
    private FlightDAO mFlightDAO;
    private TextView mAdmin;
    private Button mAdmin2;

    private User mUser;

    private Button mAdminButton;

    private Button removeBookingButton;
    List<Flight> mFlightList;

    ActivityMainBinding binding;





    private int mUserId = -1;

    private SharedPreferences mPreferences = null;
    private List<Booking> mBookingList;
    //  private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDataBase();
        checkForUser();
        addUserToPreference(mUserId);
        loginUser(mUserId);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        mMainDisplay = binding.mainGymLogDisplay;
        mOrigin = binding.mainOriginEditText;
        mDestination = binding.mainDestinationEditText;
        mDate = binding.mainDateEditText;
        removeBookingButton=binding.buttonRemoveBookingButton;

        mAdmin = findViewById(R.id.textView_admin);
        mAdminButton = binding.adminButton;

        mSubmit = binding.mainSubmitButton;
        mMainDisplay.setMovementMethod(new ScrollingMovementMethod());
//        mFlightDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build().FlightDAO();
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        refreshDisplay();

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Flight searchedFlight=getSearchFlight();
                System.out.println(searchedFlight);
                Intent intent=SearchResultsActivity.intentFactory(getApplicationContext(),searchedFlight.getDestination(),searchedFlight.getOrigin(),searchedFlight.getDate(),mUser.getUserName());
                startActivity(intent);

                //submitFlight();
                refreshDisplay();
            }
        });
        removeBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = RemoveBookingActivity.intentFactory(getApplicationContext(),mUser.getUserName());
                startActivity(intent);



            }
        });

        if(mUser!=null && mUser.getAdmin()){
            mAdmin.setVisibility(View.VISIBLE);
            mAdminButton.setVisibility(View.VISIBLE);
            // mA=true;
        }else{
            mAdmin.setVisibility(View.GONE);
            mAdminButton.setVisibility(View.GONE);
            //isAdmin=false;
        }

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=AdminMenuActivity.intentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context,MainActivity.class);
        intent.putExtra(USER_ID_KEY,userId);

        return intent;
    }



    //TODO: Update to use Bookings

    private void refreshDisplay() {
        mBookingList = mFlightDAO.getBookingByUserId(mUserId);

        if (!mBookingList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Booking booking :  mBookingList) {
                sb.append("Booking ID:"+booking.getBookingId()+"\n"+
                        "Flight Info: \n"+
                        mFlightDAO.getFlightsById(booking.getFlightID())+"\n" +
                        "Quantity:"+booking.getQuantity()+"\n"+"\n");
            }
            mMainDisplay.setText(sb.toString());
        } else {
            mMainDisplay.setText("No Bookings");

        }

    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        if(mUser != null){
            MenuItem item = menu.findItem(R.id.userMenuLogout);
            item.setTitle(mUser.getUserName());

        }
        return super.onPrepareOptionsMenu(menu);
    }



    //TODO: Change Function Below

    private Flight getSearchFlight() {
        String origin = mOrigin.getText().toString();
        String destination = mDestination.getText().toString();
        String date = mDate.getText().toString();

        Flight flight = new Flight(origin, destination, date);
        return flight;
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
//        if(isAdmin){
//
//        }else{
//
//        }
        return true;
    }
    //WAS NOT USED IN ORIGINAL COMMENTED OUT
//    private Flight getValuesFromDisplay(){
//        String exercise = "No record found";
//        double weight = 0.0;
//        int reps=0;
//
//        exercise = mExercise.getText().toString();
//
//        try{
//            weight = Double.parseDouble(mWeight.getText().toString());
//        }catch (NumberFormatException e){
//            Log.d("GYMLOG","Couldn't convert weight");
//        }
//
//        try{
//            reps=Integer.parseInt(mReps.getText().toString());
//        }catch (NumberFormatException e){
//            Log.d("GYMLOG","Couldn't convert reps");
//        }
//        return new GymLog(exercise,weight,reps,mUserId);
//    }


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


        List<User>users = mFlightDAO.getAllUsers();
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

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    private void logoutUser(){
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        clearUserFromIntent();
                        clearUserFromPref();
                        mUserId = -1;
                        checkForUser();
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

    private void getDataBase() {
        mFlightDAO = Room.databaseBuilder(this, AppDataBase.class, AppDataBase.DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
                .FlightDAO();
    }

    private void loginUser(int userId) {
        mUser=mFlightDAO.getUserByUserId(userId);


        invalidateOptionsMenu();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.userMenuLogout:
                logoutUser();
                return true;
            case R.id.AccountInfo:
                Intent intent = AccountInfoActivity.intentFactory(this);


                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }








    //private void getSearchFlight() {
//        String origin = mOrigin.getText().toString();
//        String destination = mDestination.getText().toString();
//        String date = mDate.getText().toString();
//
//        //Flight flight = new Flight(origin, destination, date,mUserId);
//        mFlightDAO.insert(flight);
//        refreshDisplay();
//    }

}