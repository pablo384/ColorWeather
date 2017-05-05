package com.proeduka.colorweather.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pablo384 on 29/04/17.
 */

public class Day implements Parcelable{
    private String dayName;
    private String weatherDescription;
    private String rainProbability;

    public Day(){}

    public Day(String dayName, String weatherDescription, String rainProbability) {
        this.dayName = dayName;
        this.weatherDescription = weatherDescription;
        this.rainProbability = rainProbability;
    }

    protected Day(Parcel in) {
        dayName = in.readString();
        weatherDescription = in.readString();
        rainProbability = in.readString();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(String rainProbability) {
        this.rainProbability = rainProbability;
    }

    @Override
    public int describeContents() {
        return 0;//we don't need this
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(dayName);
        parcel.writeString(weatherDescription);
        parcel.writeString(rainProbability);
    }
}
