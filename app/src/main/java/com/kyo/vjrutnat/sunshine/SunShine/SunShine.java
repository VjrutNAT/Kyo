package com.kyo.vjrutnat.sunshine.SunShine;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.kyo.vjrutnat.sunshine.Adapter.WeatherAdapter;
import com.kyo.vjrutnat.sunshine.Items.Weather;
import com.kyo.vjrutnat.sunshine.R;
import com.kyo.vjrutnat.sunshine.Utils.UrlWeather;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by VjrutNAT on 12/28/2016.
 */

public class SunShine extends Fragment {

    public static final String TAG = SunShine.class.getName();
    public static OnShowDetailsListener mCallBackShowDetails;
    private ArrayList<Weather> mData;
    private RecyclerView mRecyclerView;
    private ProgressBar mPrgLoad;
    private static final String LON = "lon";
    private static final String LAT = "lat";
    private String mLon;
    private String mLat;
    private int mDay;
    private int mMonth;
    private String mCityName;

    public SunShine() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackShowDetails = (OnShowDetailsListener) context;
    }

    public static SunShine newInstance(String lon, String lat) {
        SunShine sunShine = new SunShine();
        Bundle bundle = new Bundle();
        bundle.putString(LON, lon);
        bundle.putString(LAT, lat);

        sunShine.setArguments(bundle);
        return sunShine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mLon = bundle.getString(LON);
            mLat = bundle.getString(LAT);
            Log.d("lon", mLon);
            Log.d("lat", mLat);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunshine, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.rcv_information);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mPrgLoad = (ProgressBar) view.findViewById(R.id.pb_load);
        final TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        final TextView tvTempMax = (TextView) view.findViewById(R.id.tv_temperature_to);
        final TextView tvTempMin = (TextView) view.findViewById(R.id.tv_temperature_from);
        final TextView tvClouds = (TextView) view.findViewById(R.id.tv_static);
        final ImageView imvWeather = (ImageView) view.findViewById(R.id.imv_weather);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(UrlWeather.locationWeatherUrl(mLon, mLat), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray weather = response.optJSONArray("weather");
                JSONObject clouds = weather.optJSONObject(0);
                String description = clouds.optString("description");
                String icon = clouds.optString("icon");
                String date = response.optString("dt");
                JSONObject temperature = response.optJSONObject("main");
                int temperatureMax = temperature.optInt("temp_max");
                int temperatureMin = temperature.optInt("temp_min");

                Calendar calendar = Calendar.getInstance();
                TimeZone tz = TimeZone.getDefault();
                calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                java.util.Date currentTimeZone = new java.util.Date((long) Integer.parseInt(date) * 1000);
                calendar.setTime(currentTimeZone);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                mMonth = calendar.get(Calendar.MONTH);

                int tempMax = temperatureMax - 273;
                int tempMin = temperatureMin - 273;
                tvClouds.setText(description);
                tvTempMax.setText("" + tempMax + "℃");
                tvTempMin.setText("" + tempMin + "℃");
                Picasso.with(getActivity()).load(UrlWeather.ICON_WEATHER_URL + icon + ".png").into(imvWeather);

                JsonObjectRequest jsonObject = new JsonObjectRequest(UrlWeather.locationCity(mLon, mLat), null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        JSONArray arrayCity = response.optJSONArray("results");
                        for (int j = 0; j < arrayCity.length(); j++) {
                            JSONObject objCity = arrayCity.optJSONObject(j);
                            JSONArray arrayInnerCity = objCity.optJSONArray("address_components");
                            for (int i = 0; i < arrayInnerCity.length(); i++) {
                                JSONObject zero = arrayInnerCity.optJSONObject(i);
                                JSONArray types = zero.optJSONArray("types");
                                String localCity = types.optString(0);
                                if (localCity.equals("locality")) {
                                    String address = zero.optString("long_name");
                                    mCityName = address.replace("City", "");
                                    Log.d(TAG, "city" + mCityName);
                                }
                            }
                        }
                        tvDate.setText(mCityName + ", Today, " + getMonthName(mMonth) + " " + mDay);
                        Log.d(TAG, getMonthName(mMonth) + "");
                        Log.d(TAG, mDay + "");
                        Log.d(TAG, tvDate.getText().toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                AppController.newInstance().addRequestQueue(jsonObject, "namecity");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.newInstance().addRequestQueue(jsonObjectRequest, "currentweather");

        mData = new ArrayList<>();
        JsonObjectRequest objectRequest = new JsonObjectRequest(UrlWeather.locationWeekWeatherUrl(mLon, mLat), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                for (int i = 0; i < response.length(); i++) {
                    JSONArray list = response.optJSONArray("list");
                    JSONObject obj = list.optJSONObject(i + 1);
                    String date = obj.optString("dt");
                    JSONObject temp = obj.optJSONObject("temp");
                    int tempMax = temp.optInt("max");
                    int tempMin = temp.optInt("min");
                    int pressure = obj.optInt("pressure");
                    int humidity = obj.optInt("humidity");
                    double speed = obj.optDouble("speed");
                    JSONArray weather = obj.optJSONArray("weather");
                    JSONObject objWeather = weather.optJSONObject(0);
                    String clouds = objWeather.optString("description");
                    String icon = objWeather.optString("icon");


                    Calendar calendar = Calendar.getInstance();
                    TimeZone tz = TimeZone.getDefault();
                    calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                    java.util.Date currentTimeZone = new java.util.Date((long) Integer.parseInt(date) * 1000);
                    calendar.setTime(currentTimeZone);
                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int temperatureMax = tempMax - 273;
                    int temperatureMin = tempMin - 273;
                    String dateFormat = getMonthName(month) + " " + day;
                    Weather weatherDetails = new Weather(getDayName(dayOfWeek), clouds, temperatureMax + "℃", temperatureMin + "℃", humidity + "",
                            pressure + "", speed + "", icon, dateFormat);
                    mData.add(weatherDetails);
                }
                WeatherAdapter adapter = new WeatherAdapter(getActivity(), mData);
                mRecyclerView.setAdapter(adapter);

                mPrgLoad.setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.newInstance().addRequestQueue(objectRequest, "weekweather");

        return view;
    }

    public interface OnShowDetailsListener {
        void onShowDetails(String mDay, String mStatus, String mTempMax, String mTempMin, String mIcon, String mHumidity,
                           String mPressure, String mWind, String mDate);

    }

    private String getDayName(int day) {
        switch (day) {
            case 1:
                return "Sunday";
            case 2:
                return "Monday";
            case 3:
                return "Tuesday";
            case 4:
                return "Wednesday";
            case 5:
                return "Thursday";
            case 6:
                return "Friday";
            case 7:
                return "Saturday";
        }

        return "Wrong Day";
    }

    private String getMonthName(int month) {
        switch (month) {
            case 0:
                return "January";
            case 1:
                return "February";
            case 2:
                return "March";
            case 3:
                return "April";
            case 4:
                return "May";
            case 5:
                return "June";
            case 6:
                return "July";
            case 7:
                return "August";
            case 8:
                return "September";
            case 9:
                return "October";
            case 10:
                return "November";
            case 11:
                return "December";
        }
        return "Wrong month";
    }


}