package com.proeduka.colorweather.activities;

import android.app.ListActivity;
import android.content.Intent;
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
        Intent intent = getIntent();
        ArrayList<Day> daysArray = intent.getParcelableArrayListExtra(MainActivity.DAYS_ARRAY_LIST);
        DailyWeatherAdapter dailyWeatherAdapter = new DailyWeatherAdapter(this,daysArray);
        setListAdapter(dailyWeatherAdapter);
    }
}
