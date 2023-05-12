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

    public static class FlightViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewFlightDest;
        public TextView textViewFlightDate;
        public TextView textViewFlightOrigin;
        public TextView textViewFlightID;

        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFlightDest = itemView.findViewById(R.id.recViewAdminFlightDest);
            textViewFlightDate= itemView.findViewById(R.id.recViewAdminFlightDate);
            textViewFlightOrigin=itemView.findViewById(R.id.recViewAdminFlightOrigin);
            textViewFlightID=itemView.findViewById(R.id.recViewAdminFlightID);
        }
    }

    public FlightAdapter(List<Flight> flightList){
        mFlightList=flightList;
    }

    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_item,parent,false);
       FlightViewHolder fvh = new FlightViewHolder(v);
       return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight currentFlight = mFlightList.get(position);

        holder.textViewFlightID.setText(Integer.toString(currentFlight.getFlightId()));
        holder.textViewFlightOrigin.setText(currentFlight.getOrigin());
        holder.textViewFlightDate.setText(currentFlight.getDate());
        holder.textViewFlightDest.setText(currentFlight.getDestination());


    }

    @Override
    public int getItemCount() {
        return mFlightList.size();
    }
}
