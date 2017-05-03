package com.proeduka.colorweather;

/**
 * Created by pablo384 on 03/05/17.
 */

public class Hour {

    private String title;
    private String Weatherdescription;

    public Hour(String title, String weatherdescription) {
        this.title = title;
        Weatherdescription = weatherdescription;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWeatherdescription() {
        return Weatherdescription;
    }

    public void setWeatherdescription(String weatherdescription) {
        Weatherdescription = weatherdescription;
    }
}
