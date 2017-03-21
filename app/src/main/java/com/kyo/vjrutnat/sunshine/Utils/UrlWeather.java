package com.kyo.vjrutnat.sunshine.Utils;

/**
 * Created by VjrutNAT on 12/29/2016.
 */

public class UrlWeather {

//    public static final String CURRENT_WEATHER_URL = "http://api/.openweathermap.org/data/2.5/weather?id=1581129&appid=0634efb262d9398a09fc2693c5120589";
//    public static final String WEEK_WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?id=1581129=&appid=0634efb262d9398a09fc2693c5120589";
    public static final String ICON_WEATHER_URL = "http://openweathermap.org/img/w/";

    public static final String locationWeatherUrl(String lon, String lat){
        String url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=0634efb262d9398a09fc2693c5120589";
        return url;
    }

    public static final String locationWeekWeatherUrl(String lon, String lat){
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=" + lat + "&lon="+ lon + "&cnt=10&appid=0634efb262d9398a09fc2693c5120589";
        return url;
    }

    public static final String locationCity(String lon , String lat){
        String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat + "," + lon + "&sensor=true";
        return url;
    }

}
