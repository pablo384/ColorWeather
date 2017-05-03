package com.proeduka.colorweather.models;

/**
 * Created by pablo384 on 03/05/17.
 */

public class Minute {
    private String title;
    private String rainProbability;

    public Minute(String title, String rainProbability) {
        this.title = title;
        this.rainProbability = rainProbability;
    }

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
}
