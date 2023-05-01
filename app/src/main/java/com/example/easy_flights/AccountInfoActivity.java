package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AccountInfoActivity extends AppCompatActivity {
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);


    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,AccountInfoActivity.class);

        return intent;
    }
}