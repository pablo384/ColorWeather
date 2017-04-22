package com.proeduka.colorweather;

import android.graphics.drawable.Drawable;

import butterknife.BindDrawable;

/**
 * Created by pablo384 on 22/04/17.
 */

public class CurrentWeather {


    private String currentTemp;
    private String descriptionWT;
    private String highestTemp;
    private String lowestTemp;
    private String iconImageWT;

    public static final String CLEAR_NIGHT = "clear-night";
    public static final String CLEAR_DAY = "clear-day";
    public static final String CLOUDY = "cloudy";
    public static final String PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";
    public static final String FOG = "fog";
    public static final String NA = "na";
    public static final String PARTLY_CLOUDY_DAY = "partly-cloudy-day";
    public static final String RAIN = "rain";
    public static final String SLEET = "sleet";
    public static final String SNOW = "snow";
    public static final String SUNNY = "sunny";
    public static final String WIND = "wind";


    @BindDrawable(R.drawable.clear_night) Drawable clearNight;
    @BindDrawable(R.drawable.clear_day) Drawable clearDay;
    @BindDrawable(R.drawable.cloudy) Drawable cloudy;
    @BindDrawable(R.drawable.cloudy_night) Drawable cloudyNight;
    @BindDrawable(R.drawable.fog) Drawable fog;
    @BindDrawable(R.drawable.na) Drawable na;
    @BindDrawable(R.drawable.partly_cloudy) Drawable partlyCloudy;
    @BindDrawable(R.drawable.rain) Drawable rain;
    @BindDrawable(R.drawable.sleet) Drawable sleet;
    @BindDrawable(R.drawable.snow) Drawable snow;
    @BindDrawable(R.drawable.sunny) Drawable sunny;
    @BindDrawable(R.drawable.wind) Drawable wind;

    public CurrentWeather(){}

    public CurrentWeather(String currentTemp, String descriptionWT, String highestTemp, String lowestTemp, String iconImageWT) {
        this.currentTemp = currentTemp;
        this.descriptionWT = descriptionWT;
        this.highestTemp = highestTemp;
        this.lowestTemp = lowestTemp;
        this.iconImageWT = iconImageWT;
    }

    public String getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(String currentTemp) {
        this.currentTemp = currentTemp;
    }

    public String getDescriptionWT() {
        return descriptionWT;
    }

    public void setDescriptionWT(String descriptionWT) {
        this.descriptionWT = descriptionWT;
    }

    public String getHighestTemp() {
        return highestTemp;
    }

    public void setHighestTemp(String highestTemp) {
        this.highestTemp = highestTemp;
    }

    public String getLowestTemp() {
        return lowestTemp;
    }

    public void setLowestTemp(String lowestTemp) {
        this.lowestTemp = lowestTemp;
    }

    public String getIconImageWT() {
        return iconImageWT;
    }

    public void setIconImageWT(String iconImageWT) {
        this.iconImageWT = iconImageWT;
    }
    private Drawable getIconDrawableResource(){

        switch (iconImageWT) {
            case CLEAR_NIGHT:
                return clearNight;
            case CLEAR_DAY:
                return clearDay;
            case CLOUDY:
                return cloudy;
            case PARTLY_CLOUDY_NIGHT:
                return cloudyNight;
            case FOG:
                return fog;
            case NA:
                return na;
            case PARTLY_CLOUDY_DAY:
                return partlyCloudy;
            case RAIN:
                return rain;
            case SLEET:
                return sleet;
            case SNOW:
                return snow;
            case SUNNY:
                return sunny;
            case WIND:
                return wind;
            default:
                return na;
        }
    }
}
