package com.example.helperapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helperapp.R;
import com.example.helperapp.models.HelperBooking;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

public class HelperBookingAdapter extends RecyclerView.Adapter<HelperBookingAdapter.HelperBookingViewHolder> {
    private List<HelperBooking> helperBookingList;
    private OnItemClickListener listener;

    public HelperBookingAdapter(List<HelperBooking> helperBookingList, OnItemClickListener listener) {
        this.helperBookingList = helperBookingList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(HelperBooking helperBooking);
    }
    @NonNull
    @Override
    public HelperBookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_previous_bookings, parent, false);
        return new HelperBookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperBookingViewHolder holder, int position) {
        HelperBooking booking = helperBookingList.get(position);
        holder.bind(booking, listener);
    }

    @Override
    public int getItemCount() {
        return helperBookingList.size();
    }

    public static class HelperBookingViewHolder extends RecyclerView.ViewHolder {
        TextView bookingIdTV, jobTitleTV, dateTV, hoursHiredTV, totalFareTV;
        Button bookAgainBtn, serviceStatusBtn;

        public HelperBookingViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingIdTV = itemView.findViewById(R.id.booking_id_booking_item_tv);
            jobTitleTV = itemView.findViewById(R.id.job_title_booking_item_tv);
            dateTV = itemView.findViewById(R.id.date_booking_item_tv);
            hoursHiredTV = itemView.findViewById(R.id.hours_booking_item_tv);
            totalFareTV = itemView.findViewById(R.id.total_fare_booking_item_tv);
            bookAgainBtn = itemView.findViewById(R.id.book_again_booking_item_btn);
            serviceStatusBtn = itemView.findViewById(R.id.service_status_booking_item_btn);
        }

        public void bind(final HelperBooking helperBooking, OnItemClickListener listener) {
            Instant instant = Instant.parse(helperBooking.getBookingTimestamp());
            String bookingDate = instant.atZone(ZoneId.systemDefault()).toLocalDate().toString();

            bookingIdTV.setText(helperBooking.getBookingId());
            jobTitleTV.setText(helperBooking.getJobTitle());
            dateTV.setText(bookingDate);
            hoursHiredTV.setText(helperBooking.getHoursBooked());
            totalFareTV.setText(helperBooking.getTotalFare());

            bookAgainBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(helperBooking);
                }
            });
            serviceStatusBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(helperBooking);
                }
            });
        }
    }
}
