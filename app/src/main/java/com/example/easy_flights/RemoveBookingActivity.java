package com.example.easy_flights;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

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
    private BookingAdapter mAdapter;
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

        mAdapter.setOnBookingClickListener(new BookingAdapter.OnBookingClickListener() {
            @Override
            public void onBookingClick(int position) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(removeBookingContext);

                alertBuilder.setMessage("Remove this booking? (This can't be undone...)");

                alertBuilder.setPositiveButton(getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeBooking(position);
                                Toast.makeText(getApplicationContext(), "Booking removed", Toast.LENGTH_SHORT).show();

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

    public void removeBooking(int pos){
        Booking currentBooking = mBookingList.get(pos);
        mFlightDAO.delete(currentBooking);
        mBookingList.remove(pos);
        mAdapter.notifyDataSetChanged();


    }

}