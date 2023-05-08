package com.example.easy_flights;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.easy_flights.DB.AppDataBase;


@Entity(tableName = AppDataBase.USER_TABLE)
public class User {
    @PrimaryKey(autoGenerate = true)
    private int mUserId;

    private String mUserName;

    private String mPassword;

    private Boolean isAdmin;

    public User( String userName, String password,Boolean isAdmin) {

        mUserName = userName;
        mPassword = password;
        this.isAdmin=isAdmin;
    }


    public Boolean getAdmin() {
        return isAdmin;
    }



    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
