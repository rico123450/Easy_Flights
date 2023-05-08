package com.example.easy_flights;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class userView extends RecyclerView.ViewHolder {

    TextView userIDView;
    TextView userNameView;
    TextView userPasswordView;
    TextView userIsAdmin;

    public userView(@NonNull View itemView) {
        super(itemView);
        userIDView=itemView.findViewById(R.id.recViewUserID);
        userNameView=itemView.findViewById(R.id.recViewUserName);
        userPasswordView=itemView.findViewById(R.id.recViewUserPassword);
        userIsAdmin=itemView.findViewById(R.id.recViewUserIsAdmin);
    }
}
