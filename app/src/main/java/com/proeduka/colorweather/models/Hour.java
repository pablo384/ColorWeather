package com.proeduka.colorweather.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pablo384 on 03/05/17.
 */

public class Hour implements Parcelable{

    private String title;
    private String weatherdescription;

    public Hour(){}

    public Hour(String title, String weatherdescription) {
        this.title = title;
        this.weatherdescription = weatherdescription;
    }

    protected Hour(Parcel in) {
        title = in.readString();
        weatherdescription = in.readString();
    }

    public static final Creator<Hour> CREATOR = new Creator<Hour>() {
        @Override
        public Hour createFromParcel(Parcel in) {
            return new Hour(in);
        }

        @Override
        public Hour[] newArray(int size) {
            return new Hour[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeatherdescription() {
        return weatherdescription;
    }

    public void setWeatherdescription(String weatherdescription) {
        this.weatherdescription = weatherdescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(weatherdescription);

    }
}
