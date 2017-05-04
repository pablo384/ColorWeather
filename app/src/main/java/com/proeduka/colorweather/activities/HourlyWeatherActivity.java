package com.proeduka.colorweather.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.proeduka.colorweather.R;
import com.proeduka.colorweather.adapters.HourlyWeatherAdapter;
import com.proeduka.colorweather.models.Hour;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyWeatherActivity extends Activity {

    @BindView(R.id.hourlyListView)
    ListView hourlyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        ButterKnife.bind(this);

        ArrayList<Hour> hourArrayList = new ArrayList<Hour>();

        Hour hour = new Hour("20:15","Sunny");

        for (int i=0; i <500; i++){
            hourArrayList.add(hour);
        }

        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(hourArrayList,this);

        hourlyListView.setAdapter(hourlyWeatherAdapter);



    }
}