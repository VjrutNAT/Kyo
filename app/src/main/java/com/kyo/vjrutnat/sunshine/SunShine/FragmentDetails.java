package com.kyo.vjrutnat.sunshine.SunShine;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.kyo.vjrutnat.sunshine.R;
import com.kyo.vjrutnat.sunshine.Utils.UrlWeather;
import com.squareup.picasso.Picasso;

/**
 * Created by VjrutNAT on 12/28/2016.
 */

public class FragmentDetails extends Fragment {

    public static final String TAG = FragmentDetails.class.getName();

    private static final String DAY = "day";
    private static final String STATUS = "status";
    private static final String TEMPMAX = "tempMax";
    private static final String TEMPMIN = "tempMin";
    private static final String ICON = "icon";
    private static final String HUMIDITY = "humidity";
    private static final String PRESSURE = "pressure";
    private static final String WIND = "wind";
    private static final String DATE = "date";


    private String mDay;
    private String mDate;
    private String mStatus;
    private String mTempMax;
    private String mTempMin;
    private String mIcon;
    private String mHumidity;
    private String mPressure;
    private String mWind;


    public FragmentDetails() {
    }

    public static FragmentDetails newInstance(String day, String status, String tempMax, String tempMin,
                                              String icon, String humidity, String pressure, String wind, String date){
        FragmentDetails fragmentDetails = new FragmentDetails();
        Bundle bundle = new Bundle();
        bundle.putString(DAY , day);
        bundle.putString(DATE, date);
        bundle.putString(STATUS , status);
        bundle.putString(TEMPMAX , tempMax);
        bundle.putString(TEMPMIN , tempMin);
        bundle.putString(ICON , icon);
        bundle.putString(HUMIDITY , humidity);
        bundle.putString(PRESSURE , pressure);
        bundle.putString(WIND , wind);
        fragmentDetails.setArguments(bundle);
        return fragmentDetails;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            mDay = bundle.getString(DAY);
            mDate = bundle.getString(DATE);
            mStatus = bundle.getString(STATUS);
            mTempMax = bundle.getString(TEMPMAX);
            mTempMin = bundle.getString(TEMPMIN);
            mIcon = bundle.getString(ICON);
            mHumidity = bundle.getString(HUMIDITY);
            mPressure = bundle.getString(PRESSURE);
            mWind = bundle.getString(WIND);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details, container, false);
        TextView day = (TextView) view.findViewById(R.id.tv_day);
        TextView date = (TextView) view.findViewById(R.id.tv_date);
        TextView status = (TextView) view.findViewById(R.id.tv_clouds);
        TextView tempMax = (TextView) view.findViewById(R.id.tv_temperature_to);
        TextView tempMin = (TextView) view.findViewById(R.id.tv_temperature_from);
        TextView humidity = (TextView) view.findViewById(R.id.tv_humidity);
        TextView pressure = (TextView) view.findViewById(R.id.tv_pressure);
        TextView wind = (TextView) view.findViewById(R.id.tv_wind);
        ImageView imvIcon = (ImageView) view.findViewById(R.id.imv_weather);

        day.setText(mDay);
        date.setText(mDate);
        status.setText(mStatus);
        tempMax.setText(mTempMax);
        tempMin.setText(mTempMin);
        humidity.setText("Humidity: " + mHumidity + " %");
        pressure.setText("Pressure: " + mPressure);
        wind.setText("Speed: "+ mWind + " km/h");
        Picasso.with(getActivity()).load(UrlWeather.ICON_WEATHER_URL + mIcon+ ".png").into(imvIcon);
        return view;
    }

}
