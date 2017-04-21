package com.kyo.vjrutnat.sunshine.SunShine;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.kyo.vjrutnat.sunshine.R;
import com.kyo.vjrutnat.sunshine.Utils.UrlWeather;
import com.squareup.picasso.Picasso;

/**
 * Created by VjrutNAT on 12/28/2016.
 */

public class FragmentDetails extends Fragment {
    private RelativeLayout mBackground;

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
        mBackground = (RelativeLayout) view.findViewById(R.id.background_image_view);
        getBackgound(mStatus);
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
    private void getBackgound(String description) {

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
        } else if(description.equals("thunder storm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstorm));
        } else if(description.equals("drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.drizzle));
        } else if(description.equals("dust")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.dust));
        } else if(description.equals("fog")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.fog));
        } else if(description.equals("tormado")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.tornado));
        } else if(description.equals("sleet")) {
//            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.sleet));
        } else if(description.equals("squalls")) {
//            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.squalls));
        } else if(description.equals("drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.drizzlerain));
        } else if(description.equals("extreme rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.extremerain));
        } else if(description.equals("freezing rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.freezingrain));
        } else if(description.equals("heavy intensity drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensitydrizzlerain));
        } else if(description.equals("heavy intensity drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensitydrizzle));
        } else if(description.equals("heavy intensity rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensityrain));
        } else if(description.equals("heavy intensity shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyintensityshowerrain));
        } else if(description.equals("heavy shower rain and drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyshowerrainanddrizzle));
        } else if(description.equals("heavy shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavyshowersnow));
        } else if(description.equals("heavy snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavysnow));
        }else if(description.equals("light intensity drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzle));
        }else if(description.equals("light intensity shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzlerain));
        }else if(description.equals("light rain and snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightrainandsnow));
        }else if(description.equals("light shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightshowersnow));
        }else if(description.equals("light rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightrain));
        }else if(description.equals("light snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightsnow));
        }else if(description.equals("light thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightthunderstorm));
        }else if(description.equals("moderate rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.moderaterain));
        }else if(description.equals("overcast clouds")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.overcastclouds));
        }else if(description.equals("ragged shower rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.raggedshowerrain));
        }else if(description.equals("rain and snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.rainandsnow));
        }else if(description.equals("sand, dust whirls")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.sanddustwhirls));
        }else if(description.equals("shower drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showerdrizzle));
        }else if(description.equals("shower rain and drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showerrainanddrizzle));
        }else if(description.equals("shower sleet")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showersleet));
        }else if(description.equals("shower snow")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.showersnow));
        }else if(description.equals("thunderstorm with drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithdrizzle));
        }else if(description.equals("ragged thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.raggedthunderstorm));
        }else if(description.equals("thunderstorm with heavy drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithheavydrizzle));
        }else if(description.equals("thunderstorm with heavy rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithheavyrain));
        }else if(description.equals("thunderstorm with light drizzle")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithlightdrizzle));
        }else if(description.equals("thunderstorm with light rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithlightrain));
        }else if(description.equals("thunderstorm with rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.thunderstormwithrain));
        }else if(description.equals("very heavy rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.veryheavyrain));
        }else if(description.equals("volcani cash ")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.volcanicash));
        } else if(description.equals("heavy thunderstorm")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.heavythunderstorm));
        } else if(description.equals("light intensity drizzle rain")) {
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.lightintensitydrizzlerain));
        }else if(description.equals("sky is clear")){
            mBackground.setBackgroundDrawable(getResources().getDrawable(R.drawable.scatteredclouds));
        }
    }

}
