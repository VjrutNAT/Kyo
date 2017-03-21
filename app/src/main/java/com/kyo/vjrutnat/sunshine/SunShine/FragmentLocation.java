package com.kyo.vjrutnat.sunshine.SunShine;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.kyo.vjrutnat.sunshine.R;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by VjrutNAT on 1/6/2017.
 */

public class FragmentLocation extends Fragment  {

//    private GoogleApiClient mGoogleApiClient;
//    private Location mLocation;

    private Button mBtnCheckLcation;
    private OnShowSunShineListener mCallBackSunShine;
    private String mLatitude;
    private String mLongitude;
    private GPSTracker gps;

    public FragmentLocation() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBackSunShine = (OnShowSunShineListener) context;
    }

    public static FragmentLocation newInstance( ) {
        FragmentLocation fragmentLocation = new FragmentLocation();
        return fragmentLocation;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);
        mBtnCheckLcation = (Button) view.findViewById(R.id.btn_showsunshine);

        mBtnCheckLcation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkGPS();
            }
        });
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        checkGPS();
//    }

    public interface OnShowSunShineListener {
        void onShowSunShine(String lon, String lat);
    }

    private void checkGPS (){
        gps = new GPSTracker(getActivity());
        if (gps.canGetLocation()){
            mLongitude = gps.getLongitude() + "";
            mLatitude = gps.getLatitude() + "";
            mCallBackSunShine.onShowSunShine(mLongitude, mLatitude);
        }else{
            gps.showSettingsAlert();
        }
    }
}