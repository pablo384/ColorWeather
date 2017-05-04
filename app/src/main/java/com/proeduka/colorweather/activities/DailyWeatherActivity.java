package com.proeduka.colorweather.activities;

import android.app.ListActivity;
import android.os.Bundle;

import com.proeduka.colorweather.R;
import com.proeduka.colorweather.adapters.DailyWeatherAdapter;
import com.proeduka.colorweather.models.Day;

import java.util.ArrayList;

public class DailyWeatherActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_weather);

        ArrayList<Day> daysArray = MainActivity.dayArrayListPrueba;



        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this,daysArray);

        setListAdapter(dailyWeatherAdapter);
    }
}
