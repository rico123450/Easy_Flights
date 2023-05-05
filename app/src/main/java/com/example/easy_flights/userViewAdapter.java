package com.example.easy_flights;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class userViewAdapter extends RecyclerView.Adapter<userView>{
    Context mContext;
    List<User> mUserList;

    public userViewAdapter(Context context, List<User> userList) {
        mContext = context;
        mUserList = userList;
    }

    @NonNull
    @Override
    public userView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new userView(LayoutInflater.from(mContext).inflate(R.layout.user_view_items,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull userView holder, int position) {
        holder.userNameView.setText(mUserList.get(position).getUserName());
        holder.userPasswordView.setText(mUserList.get(position).getPassword());
        holder.userIsAdmin.setText(mUserList.get(position).getAdmin().toString());
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }
}
