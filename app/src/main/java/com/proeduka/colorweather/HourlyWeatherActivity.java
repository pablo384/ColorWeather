package com.proeduka.colorweather;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;

import com.proeduka.colorweather.adapters.HourlyWeatherAdapter;

import java.util.ArrayList;

public class HourlyWeatherActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);

        ArrayList<Hour> hourArrayList = new ArrayList<Hour>();

        Hour hour = new Hour("20:15","Sunny");

        for (int i=0; i <500; i++){
            hourArrayList.add(hour);
        }

        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(hourArrayList,this);

        setListAdapter(hourlyWeatherAdapter);



    }
}
