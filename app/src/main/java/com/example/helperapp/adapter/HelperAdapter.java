package com.example.helperapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.helperapp.R;
import com.example.helperapp.models.Helper;

import java.util.List;

public class HelperAdapter extends RecyclerView.Adapter<HelperAdapter.HelperViewHolder> {
    private List<Helper> helperList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Helper item);
    }

    public HelperAdapter(List<Helper> helperList, OnItemClickListener listener) {
        this.helperList = helperList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HelperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_helper, parent, false);
        return new HelperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelperViewHolder holder, int position) {
        Helper helper = helperList.get(position);
        holder.bind(helper, listener);
    }

    @Override
    public int getItemCount() {
        return helperList.size();
    }

    public static class HelperViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePictureIV;
        TextView helperName;
        TextView helperFarePerHour;
        Button helperBookBtn;

        public HelperViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePictureIV = itemView.findViewById(R.id.list_item_helper_image);
            helperName = itemView.findViewById(R.id.list_item_helper_name_value_tv);
            helperFarePerHour = itemView.findViewById(R.id.list_item_helper_fare_value_tv);
            helperBookBtn = itemView.findViewById(R.id.list_item_call_helper);
        }

        public void bind(final Helper helper, final OnItemClickListener listener) {
            helperName.setText(helper.getName());
            helperFarePerHour.setText(helper.getFarePerHour());

            Glide.with(itemView.getContext())
                    .load(helper.getProfilePictureURL())
                    .placeholder(R.drawable.driver_home_icon)
                    .into(profilePictureIV);
            helperBookBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(helper);
                }
            });
        }
    }
}
