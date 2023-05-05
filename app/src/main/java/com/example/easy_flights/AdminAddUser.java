package com.example.easy_flights;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
//TODO: REMOVE THROW?
public class AdminAddUser extends AppCompatDialogFragment {

    private EditText mEditTextUsername;
    private EditText editTextPassword;
    private AdminAddUserListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_user_dialog,null);



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

    public interface AdminAddUserListener{
        void applyTexts(String username,String password);
    }
}
