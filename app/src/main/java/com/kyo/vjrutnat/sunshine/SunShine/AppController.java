package com.kyo.vjrutnat.sunshine.SunShine;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by VjrutNAT on 12/29/2016.
 */

public class AppController extends Application {

    private static AppController appController;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        appController = this;
    }

    public static AppController newInstance() {
        return appController;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addRequestQueue(Request<T> reg, String tag) {
        reg.setTag(tag);
        getRequestQueue().add(reg);
    }

    public void cancelRequestQueue(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

    public boolean checkWifiConnected(){
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }
        return false;

    }
}
