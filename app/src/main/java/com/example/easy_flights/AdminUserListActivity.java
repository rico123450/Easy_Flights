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
import com.example.easy_flights.databinding.ActivityAdminUserListBinding;

import java.util.List;

public class AdminUserListActivity extends AppCompatActivity implements userViewAdapter.OnUserClickListener {
    private FlightDAO mFlightDAO;

    private SharedPreferences mPreferences = null;

    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";

    private List<User> mUserList;

    ActivityAdminUserListBinding binding;

    private RecyclerView mRecyclerView;
    private userViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    private static AdminUserListActivity adminUserListContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user_list);
//        binding= ActivityAdminUserListBinding.inflate(getLayoutInflater());
        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        getPrefs();
        adminUserListContext=this;



        mUserList=mFlightDAO.getAllUsers();
        mRecyclerView=findViewById(R.id.recViewUsers);
        mLayoutManager=new LinearLayoutManager(this);
        mAdapter= new userViewAdapter(getApplicationContext(),mUserList);

//        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

//        binding.recViewUsers.setAdapter(recViewAdapter);


        mAdapter.setOnUserClickListener(new userViewAdapter.OnUserClickListener() {
            @Override
            public void onUser(int pos) {

            }
        });







        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AdminUserListActivity.class);
        return intent;
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

    @Override
    public void onUser(int pos) {

    }


    public static Context getAdminUserListActivityContext(){
        return adminUserListContext;
    }

}