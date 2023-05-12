package com.example.easy_flights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {
    private List<Flight> mFlightList;

    private OnFlightClickListener mListener;

    public interface OnFlightClickListener{
        void onFlightClick(int position);
    }

    public void setOnFlightClickListener(OnFlightClickListener listener){
        mListener=listener;
    }



    public static class FlightViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFlightDest;
        public TextView textViewFlightDate;
        public TextView textViewFlightOrigin;
        public TextView textViewFlightID;

        public FlightViewHolder(@NonNull View itemView,OnFlightClickListener listener) {
            super(itemView);
            textViewFlightDest = itemView.findViewById(R.id.recViewAdminFlightDest);
            textViewFlightDate= itemView.findViewById(R.id.recViewAdminFlightDate);
            textViewFlightOrigin=itemView.findViewById(R.id.recViewAdminFlightOrigin);
            textViewFlightID=itemView.findViewById(R.id.recViewAdminFlightID);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position!=RecyclerView.NO_POSITION){
                            listener.onFlightClick(position);
                        }
                    }

                }
            });
        }
    }

    public FlightAdapter(List<Flight> flightList){
        mFlightList=flightList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item,parent,false);
       FlightViewHolder fvh = new FlightViewHolder(v,mListener);
       return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight currentFlight = mFlightList.get(position);

        holder.textViewFlightID.setText("Flight ID: "+Integer.toString(currentFlight.getFlightId()));
        holder.textViewFlightOrigin.setText("Flight Origin: "+currentFlight.getOrigin());
        holder.textViewFlightDate.setText("Flight Date: "+currentFlight.getDate());
        holder.textViewFlightDest.setText("Flight Destination: "+currentFlight.getDestination());


    }

    @Override
    public int getItemCount() {
        return mFlightList.size();
    }
}
