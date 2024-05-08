package com.example.helperapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.helperapp.models.Helper;
import com.example.helperapp.R;

import java.util.List;

public class HelperAdapter extends BaseAdapter {

    private final Context context;
    private final List<Helper> helperList;

    public HelperAdapter(Context context, List<Helper> helperList) {
        this.context = context;
        this.helperList = helperList;
    }
    @Override
    public int getCount() {
        return helperList.size();
    }

    @Override
    public Object getItem(int position) {
        return helperList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_helper, parent, false);
        }

        ImageView helperImageIV = convertView.findViewById(R.id.list_item_helper_image);
        TextView helperNameValueTV = convertView.findViewById(R.id.list_item_helper_name_value_tv);
        TextView helperFarePerHourValueTV = convertView.findViewById(R.id.list_item_helper_fare_value_tv);

        Helper helper = helperList.get(position);
        Glide.with(context)
                .load(helper.getProfilePictureURL())
                .placeholder(R.drawable.account_circle_icon)
                .error(R.drawable.genie_splash_img)
                .into(helperImageIV);
        helperNameValueTV.setText(helper.getName());
        helperFarePerHourValueTV.setText(helper.getFarePerHour());

        return convertView;
    }
}
