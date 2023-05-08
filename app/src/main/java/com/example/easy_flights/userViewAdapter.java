package com.example.easy_flights;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

import java.util.List;
//TODO: How update Recycler View when removing user/changing admin privileges
public class userViewAdapter extends RecyclerView.Adapter<userView> implements View.OnClickListener{
    private OnUserClickListener mListener;

    public interface OnUserClickListener {
        void onUser(int pos);

    }

    private FlightDAO mFlightDAO;

    public void setOnUserClickListener(OnUserClickListener listener){
        mListener=listener;
    }
    Context mContext;
    private List<User> mUserList;



    public static class adminListViewHolder extends RecyclerView.ViewHolder{
        public TextView AdminListUserName;
        public TextView AdminListUserPassword;
        public TextView AdminListUserID;
        public TextView AdminListUserIsAdmin;

        public adminListViewHolder(@NonNull View itemView, OnUserClickListener listener) {
            super(itemView);
            AdminListUserName=itemView.findViewById(R.id.recViewUsers);
            AdminListUserPassword=itemView.findViewById(R.id.recViewUserPassword);
            AdminListUserID=itemView.findViewById(R.id.recViewUserID);
            AdminListUserIsAdmin=itemView.findViewById(R.id.recViewUserIsAdmin);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null){
                        int position=getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onUser(position);
                        }
                    }

                }
            });
        }
    }



    public userViewAdapter(Context context, List<User> userList) {
        mContext = context;
        mUserList = userList;
    }

    @NonNull
    @Override
    public userView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_view_items,parent,false);
//        userView evh = new userView(v);
        return new userView(LayoutInflater.from(mContext).inflate(R.layout.user_view_items,parent,false));
//        return new adminListViewHolder(v,mListener);


    }


    @Override
    public void onBindViewHolder(userView holder, int position) {
        User currentUser=mUserList.get(position);

        TextView uIDView = holder.userIDView;

        TextView uAdminView=holder.userIsAdmin;

        mFlightDAO = AppDataBase.getInstance(AdminUserListActivity.getAdminUserListActivityContext()).FlightDAO();

        uIDView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminUserListActivity.getAdminUserListActivityContext());

                alertBuilder.setMessage("Remove User?");

                alertBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                currentUser.setAdmin(!(currentUser.getAdmin()));
                                mFlightDAO.delete(currentUser);
                                Toast.makeText(mContext, "User removed", Toast.LENGTH_SHORT).show();

                            }
                        });

                alertBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        });
                alertBuilder.create().show();
            }
        });

        uAdminView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AdminUserListActivity.getAdminUserListActivityContext());

                alertBuilder.setMessage("Change admin privileges?");

                alertBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                currentUser.setAdmin(!(currentUser.getAdmin()));
                                mFlightDAO.update(currentUser);
                                Toast.makeText(mContext, "User admin privileges changed", Toast.LENGTH_SHORT).show();




                            }
                        });

                alertBuilder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //
                            }
                        });
                alertBuilder.create().show();


            }
        });



        holder.userIDView.setText("ID:"+currentUser.getUserId());
        holder.userNameView.setText("Username:"+currentUser.getUserName());
        holder.userPasswordView.setText("Password:"+currentUser.getPassword());
        holder.userIsAdmin.setText("Admin:"+currentUser.getAdmin().toString());



    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    @Override
    public void onClick(View view) {

    }


}
