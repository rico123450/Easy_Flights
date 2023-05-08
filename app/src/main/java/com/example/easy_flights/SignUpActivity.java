package com.example.easy_flights;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private static final String PREFERENCE_KEY = "com.example.easy_flights.PREFERENCE_KEY";
    private SharedPreferences mPreferences = null;

    private EditText mUserNameField;
    private EditText mPasswordField;
    private EditText mReEnterPasswordField;

    private Button mSignUpButton;

    private FlightDAO mFlightDAO;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getPrefs();

        mUserNameField=findViewById(R.id.signUpEditTextUserName);
        mPasswordField=findViewById(R.id.signUpEditTextPassword);
        mReEnterPasswordField=findViewById(R.id.signUpEditTextReEnterPassword);
        mSignUpButton=findViewById(R.id.signUpSignUpButton);

        mFlightDAO = AppDataBase.getInstance(getApplicationContext()).FlightDAO();
        List<User>currentUsers=mFlightDAO.getAllUsers();

        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName=mUserNameField.getText().toString();
                String passWord=mPasswordField.getText().toString();
                String reEnteredPassword=mReEnterPasswordField.getText().toString();

                if((mFlightDAO.getUserByUsername(userName)!=null)){
                    Toast.makeText(getApplicationContext(), "A user with the username"+userName+" already exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!passWord.equals(reEnteredPassword)){
                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                User user=new User(userName,passWord,false);
                mFlightDAO.insert(user);


                Toast.makeText(getApplicationContext(), "Your account was successfully created", Toast.LENGTH_SHORT).show();

                Intent intent = MainActivity.intentFactory(getApplicationContext(),mFlightDAO.getUserByUsername(userName).getUserId());
                startActivity(intent);
            }
        });




        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getPrefs() {
        mPreferences = this.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
    }

    public static Intent intentFactory(Context context){
        Intent intent = new Intent(context,SignUpActivity.class);
        return intent;
    }
}