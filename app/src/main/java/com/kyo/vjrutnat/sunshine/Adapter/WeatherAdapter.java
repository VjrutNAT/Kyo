package com.kyo.vjrutnat.sunshine.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.kyo.vjrutnat.sunshine.Items.Weather;
import com.kyo.vjrutnat.sunshine.R;
import com.kyo.vjrutnat.sunshine.SunShine.FragmentDetails;
import com.kyo.vjrutnat.sunshine.Utils.UrlWeather;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by VjrutNAT on 12/28/2016.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHoller> {
    public static final String TAG = WeatherAdapter.class.getName();

    private Context mContext;
    private ArrayList<Weather> mData;
    private LayoutInflater mInflater;

    public WeatherAdapter(Context mContext, ArrayList<Weather> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHoller onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.items_information, parent, false);
        ViewHoller viewHoller = new ViewHoller(view);
        return viewHoller;
    }

    @Override
    public void onBindViewHolder(ViewHoller holder, int position) {

        Weather weather = mData.get(position);
        Picasso.with(mContext).load(UrlWeather.ICON_WEATHER_URL + weather.getId() + ".png").into(holder.mId);
        holder.mDay.setText(weather.getDay());
        holder.mStatus.setText(weather.getStatus());
        holder.mTempeMax.setText(weather.getTemperutare_from());
        holder.mTempeMin.setText(weather.getTemperutare_to());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHoller extends RecyclerView.ViewHolder {

        ImageView mId;
        TextView mDay;
        TextView mStatus;
        TextView mTempeMax;
        TextView mTempeMin;
        int position;

        public ViewHoller(final View itemView) {
            super(itemView);
            mId = (ImageView) itemView.findViewById(R.id.imv_static);
            mDay = (TextView) itemView.findViewById(R.id.tv_day);
            mStatus = (TextView) itemView.findViewById(R.id.tv_clouds);
            mTempeMax = (TextView) itemView.findViewById(R.id.tv_temperature_from);
            mTempeMin = (TextView) itemView.findViewById(R.id.tv_temperature_to);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Weather weatherDetails = mData.get(position);
                    String day = weatherDetails.getDay();
                    String status = weatherDetails.getStatus();
                    String tempMax = weatherDetails.getTemperutare_to();
                    String tempMin = weatherDetails.getTemperutare_from();
                    String icon = weatherDetails.getId();
                    String humidity = weatherDetails.getHumidity();
                    String pressure = weatherDetails.getPressure();
                    String wind = weatherDetails.getWind();
                    String date = weatherDetails.getDate();

                    FragmentManager fragmentManager = ((FragmentActivity) mContext).getSupportFragmentManager();
                    FragmentTransaction transaction = fragmentManager.beginTransaction();
                    FragmentDetails fragmentDetails = FragmentDetails.newInstance(day, status, tempMax, tempMin, icon, humidity, pressure, wind, date);
                    transaction.replace(R.id.container, fragmentDetails);
                    transaction.addToBackStack(FragmentDetails.TAG);
                    transaction.commit();
                }
            });
        }

    }

}