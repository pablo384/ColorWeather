package com.proeduka.colorweather.models;

/**
 * Created by pablo384 on 29/04/17.
 */

public class Day {
    private String dayName;
    private String weatherDescription;
    private String rainProbability;

    public Day(){}

    public Day(String dayName, String weatherDescription, String rainProbability) {
        this.dayName = dayName;
        this.weatherDescription = weatherDescription;
        this.rainProbability = rainProbability;
    }

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
}
