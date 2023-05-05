package com.example.easy_flights;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

//TODO: REMOVE THROW?
public class AdminAddUser extends AppCompatDialogFragment {

    private EditText mEditTextUsername;
    private EditText editTextPassword;
    private AdminAddUserListener listener;

    private User mUser;

    private FlightDAO mFlightDAO;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_user_dialog,null);

        mFlightDAO = AppDataBase.getInstance(AdminMenuActivity.getAdminMenuActivityContext()).FlightDAO();



        builder.setView(view)
                .setTitle("Create")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String username=mEditTextUsername.getText().toString();
                        String password=editTextPassword.getText().toString();
                        listener.applyTexts(username,password);
                        addUserFromText(username,password);
                    }
                });
        editTextPassword=view.findViewById(R.id.adminAddPasswordName);
        mEditTextUsername=view.findViewById(R.id.adminAddUserName);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{

            listener= (AdminAddUserListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()+"Listener has not been implemented");
        }

    }

    private void addUserFromText(String entered_Username,String entered_Password) {
        mUser=mFlightDAO.getUserByUsername(entered_Username);
        if(mUser==null){
            mUser=new User(entered_Username,entered_Password,false);
            mFlightDAO.insert(mUser);
            Toast.makeText(AdminMenuActivity.getAdminMenuActivityContext(), "User successfully created!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(AdminMenuActivity.getAdminMenuActivityContext(), "A user with the username "+entered_Username+" already exist", Toast.LENGTH_SHORT).show();
        }
        return;
    }


    public interface AdminAddUserListener{
        void applyTexts(String username,String password);
    }
}
