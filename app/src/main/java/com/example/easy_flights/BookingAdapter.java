package com.example.easy_flights;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easy_flights.DB.AppDataBase;
import com.example.easy_flights.DB.FlightDAO;

import java.util.List;



public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {
    private List<Booking>mBookingArrayList;

    private FlightDAO mFlightDAO;
    public static class BookingViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewBookingID;
        public TextView mTextViewFlightOrigin;
        public TextView mTextViewFlightDest;
        public TextView mTextViewFlightDate;

        public TextView mTextViewBookingCapacity;





        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewBookingID=itemView.findViewById(R.id.recRemoveUserBookingID);
            mTextViewFlightDate=itemView.findViewById(R.id.recRemoveUserFlightDate);
            mTextViewFlightDest=itemView.findViewById(R.id.recRemoveUserFlightDest);
            mTextViewFlightOrigin=itemView.findViewById(R.id.recRemoveUserFlightOrigin);
            mTextViewBookingCapacity=itemView.findViewById(R.id.recRemoveUserBookingCapacity);

        }
    }

    public BookingAdapter(List<Booking> bookingArrayList){
        mBookingArrayList=bookingArrayList;
    }

    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_item,parent,false);
        BookingViewHolder bvh=new BookingViewHolder(v);
        return bvh;
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking currentBooking = mBookingArrayList.get(position);
        mFlightDAO = AppDataBase.getInstance(RemoveBookingActivity.getRemoveBookingActivityContext()).FlightDAO();

        Flight flight = mFlightDAO.getFlightById(currentBooking.getFlightID());
        holder.mTextViewFlightDest.setText("Destination: "+flight.getDestination());
        holder.mTextViewFlightOrigin.setText("Origin: "+flight.getOrigin());
        holder.mTextViewBookingID.setText("Booking ID:"+Integer.toString(currentBooking.getBookingId()));
        holder.mTextViewFlightDate.setText("Date: "+flight.getDate());
        holder.mTextViewBookingCapacity.setText("Total Booked: "+currentBooking.getQuantity());


    }

    @Override
    public int getItemCount() {
        return mBookingArrayList.size();
    }
}
