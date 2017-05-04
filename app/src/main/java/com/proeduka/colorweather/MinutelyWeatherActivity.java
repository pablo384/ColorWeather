package com.proeduka.colorweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.proeduka.colorweather.adapters.MinutelyWeatherAdapter;
import com.proeduka.colorweather.models.Minute;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinutelyWeatherActivity extends Activity{

    @BindView(R.id.minutelyRecyclerView)
    RecyclerView recyclerViewMinutely;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutely_weather);
        ButterKnife.bind(this);

        ArrayList<Minute> minuteArrayList = new ArrayList<Minute>();

        Minute minute = new Minute("15:25","99%");

        for (int i=0; i<500; i++) {
            minuteArrayList.add(minute);
        }

        MinutelyWeatherAdapter minutelyWeatherAdapter = new MinutelyWeatherAdapter(this,minuteArrayList);
        recyclerViewMinutely.setAdapter(minutelyWeatherAdapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMinutely.setLayoutManager(layoutManager);
        recyclerViewMinutely.setHasFixedSize(true);

    }
}
