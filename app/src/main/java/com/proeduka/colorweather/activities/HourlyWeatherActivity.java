package com.proeduka.colorweather.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import com.proeduka.colorweather.R;
import com.proeduka.colorweather.adapters.HourlyWeatherAdapter;
import com.proeduka.colorweather.models.Hour;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HourlyWeatherActivity extends Activity {

    @BindView(R.id.hourlyListView)
    ListView hourlyListView;
    @BindView(R.id.hourlyNoDataFound)
    TextView noDatFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ArrayList<Hour> hourArrayList = intent.getParcelableArrayListExtra(MainActivity.HOURS_ARRAY_LIST);
        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(hourArrayList,this);
        hourlyListView.setEmptyView(noDatFound);
        hourlyListView.setAdapter(hourlyWeatherAdapter);



    }
}
