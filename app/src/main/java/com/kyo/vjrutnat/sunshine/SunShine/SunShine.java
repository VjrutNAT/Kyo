package com.kyo.vjrutnat.sunshine.SunShine;


import android.content.Context;
import android.os.Bundle;
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
import android.widget.RelativeLayout;
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

public class SunShine extends Fragment {

    public static final String TAG = SunShine.class.getName();
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
    private RelativeLayout mBackground;

    public SunShine() {

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
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_demo, container, false);

        mBackground = (RelativeLayout) view.findViewById(R.id.background_image_view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rcv_information);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mPrgLoad = (ProgressBar) view.findViewById(R.id.pb_load);
        final TextView tvTemperature = (TextView) view.findViewById(R.id.tv_temperature);
        final TextView tvCity = (TextView) view.findViewById(R.id.tv_city);
        final TextView tvDate = (TextView) view.findViewById(R.id.tv_date);
        final TextView tvTempMax = (TextView) view.findViewById(R.id.tv_temperature_max);
        final TextView tvTempMin = (TextView) view.findViewById(R.id.tv_temperature_min);
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
                tvTemperature.setText(tempMax + "°C");
                tvTempMax.setText(tempMax + "°C");
                tvTempMin.setText(tempMin + "°C");
                Picasso.with(getActivity()).load(UrlWeather.ICON_WEATHER_URL + icon + ".png").into(imvWeather);
                setBackground(description);
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
                        tvCity.setText(mCityName);
                        tvDate.setText(getMonthName(mMonth) + " " + mDay);
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
                    Weather weatherDetails = new Weather(getDayName(dayOfWeek), clouds, temperatureMax + "°C", temperatureMin + "°C", humidity + "",
                            pressure + "", speed + "", icon, dateFormat);
                    mData.add(weatherDetails);
                }
                WeatherAdapter adapter = new WeatherAdapter(getActivity(), mData, SunShine.this);
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

    public void setBackground(String description) {

        if (description.equals("broken clouds")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.brokenclouds));
        } else if (description.equals("clear sky")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.clearsky));
        } else if (description.equals("few clouds")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.fewclouds));
        } else if (description.equals("mist")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.mist));
        } else if (description.equals("rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.rain));
        } else if (description.equals("scattered clounds")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.scatteredclouds));
        } else if (description.equals("shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showerrain));
        } else if (description.equals("snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.snow));
        } else if (description.equals("thunder storm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstorm));
        } else if (description.equals("drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.drizzle));
        } else if (description.equals("dust")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.dust));
        } else if (description.equals("fog")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.fog));
        } else if (description.equals("tormado")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.tornado));
        } else if (description.equals("sleet")) {
//            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.sleet));
        } else if (description.equals("squalls")) {
//            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.squalls));
        } else if (description.equals("drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.drizzlerain));
        } else if (description.equals("extreme rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.extremerain));
        } else if (description.equals("freezing rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.freezingrain));
        } else if (description.equals("heavy intensity drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensitydrizzlerain));
        } else if (description.equals("heavy intensity drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensitydrizzle));
        } else if (description.equals("heavy intensity rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensityrain));
        } else if (description.equals("heavy intensity shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensityshowerrain));
        } else if (description.equals("heavy shower rain and drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyshowerrainanddrizzle));
        } else if (description.equals("heavy shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyshowersnow));
        } else if (description.equals("heavy snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavysnow));
        } else if (description.equals("light intensity drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzle));
        } else if (description.equals("light intensity shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzlerain));
        } else if (description.equals("light rain and snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightrainandsnow));
        } else if (description.equals("light shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightshowersnow));
        } else if (description.equals("light rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightrain));
        } else if (description.equals("light snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightsnow));
        } else if (description.equals("light thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightthunderstorm));
        } else if (description.equals("moderate rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.moderaterain));
        } else if (description.equals("overcast clouds")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.overcastclouds));
        } else if (description.equals("ragged shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.raggedshowerrain));
        } else if (description.equals("rain and snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.rainandsnow));
        } else if (description.equals("sand, dust whirls")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.sanddustwhirls));
        } else if (description.equals("shower drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showerdrizzle));
        } else if (description.equals("shower rain and drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showerrainanddrizzle));
        } else if (description.equals("shower sleet")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showersleet));
        } else if (description.equals("shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showersnow));
        } else if (description.equals("thunderstorm with drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithdrizzle));
        } else if (description.equals("ragged thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.raggedthunderstorm));
        } else if (description.equals("thunderstorm with heavy drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithheavydrizzle));
        } else if (description.equals("thunderstorm with heavy rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithheavyrain));
        } else if (description.equals("thunderstorm with light drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithlightdrizzle));
        } else if (description.equals("thunderstorm with light rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithlightrain));
        } else if (description.equals("thunderstorm with rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithrain));
        } else if (description.equals("very heavy rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.veryheavyrain));
        } else if (description.equals("volcani cash ")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.volcanicash));
        } else if (description.equals("heavy thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavythunderstorm));
        } else if (description.equals("light intensity drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzlerain));
        } else {
        }
    }
}