package com.proeduka.colorweather;

import android.app.ListActivity;
import android.os.Bundle;

import com.proeduka.colorweather.adapters.DailyWeatherAdapter;
import com.proeduka.colorweather.models.Day;

import java.util.ArrayList;

public class DailyWeatherActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);

        ArrayList<Day> daysArray = new ArrayList<Day>();
        Day day = new Day("Monday","Partly MOjao", "muchisima %");

        for (int i=0; i<500; i++){
            daysArray.add(day);
        }



        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this,daysArray);

        setListAdapter(dailyWeatherAdapter);
    }
}
