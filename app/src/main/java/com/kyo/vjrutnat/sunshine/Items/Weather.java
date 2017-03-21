package com.kyo.vjrutnat.sunshine.Items;

/**
 * Created by VjrutNAT on 12/28/2016.
 */

public class Weather {
    private String day;
    private String status;
    private String temperutare_from;
    private String temperutare_to;
    private String humidity;
    private String pressure;
    private String wind;
    private String id;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Weather(String day, String status, String temperutare_from, String temperutare_to) {
        this.day = day;
        this.status = status;
        this.temperutare_from = temperutare_from;
        this.temperutare_to = temperutare_to;
    }

    public Weather(String day, String status, String temperutare_from, String temperutare_to, String humidity, String pressure, String wind, String id, String date) {
        this.day = day;
        this.status = status;
        this.temperutare_from = temperutare_from;
        this.temperutare_to = temperutare_to;
        this.humidity = humidity;
        this.pressure = pressure;
        this.wind = wind;
        this.id = id;
        this.date = date;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTemperutare_from() {
        return temperutare_from;
    }

    public void setTemperutare_from(String temperutare_from) {
        this.temperutare_from = temperutare_from;
    }

    public String getTemperutare_to() {
        return temperutare_to;
    }

    public void setTemperutare_to(String temperutare_to) {
        this.temperutare_to = temperutare_to;
    }

    public Weather(String day, String status, String temperutare_from, String temperutare_to, String id) {
        this.day = day;
        this.status = status;
        this.temperutare_from = temperutare_from;
        this.temperutare_to = temperutare_to;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
