package com.proeduka.colorweather.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by pablo384 on 03/05/17.
 */

public class Minute implements Parcelable{
    private String title;
    private String rainProbability;

    public Minute (){}

    public Minute(String title, String rainProbability) {
        this.title = title;
        this.rainProbability = rainProbability;
    }

    protected Minute(Parcel in) {
        title = in.readString();
        rainProbability = in.readString();
    }

    public static final Creator<Minute> CREATOR = new Creator<Minute>() {
        @Override
        public Minute createFromParcel(Parcel in) {
            return new Minute(in);
        }

        @Override
        public Minute[] newArray(int size) {
            return new Minute[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRainProbability() {
        return rainProbability;
    }

    public void setRainProbability(String rainProbability) {
        this.rainProbability = rainProbability;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(title);
        parcel.writeString(rainProbability);

    }
}
