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

public class AccountInfoActivity extends AppCompatActivity {
    private User mUser;

    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private String mUserName;

    private FlightDAO mFlightDAO;
    private static final String USER_NAME_KEY = "com.example.easy_flights.userNameKey";

    private TextView mTextViewUsername;

    private TextView mTextViewPassword;

    private TextView mTextViewID;

    private Button mDeleteAccountButton;

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
        mUser=mFlightDAO.getUserByUsername(mUserName);

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

    private void sortData(){
        mUserName=getIntent().getStringExtra(USER_NAME_KEY);
    }

    public static Intent intentFactory(Context context,String userName){
        Intent intent = new Intent(context,AccountInfoActivity.class);
        intent.putExtra(USER_NAME_KEY,userName);
        return intent;
    }
    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE);
    }

}