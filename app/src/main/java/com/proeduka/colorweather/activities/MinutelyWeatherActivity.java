package com.proeduka.colorweather.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.proeduka.colorweather.R;
import com.proeduka.colorweather.adapters.MinutelyWeatherAdapter;
import com.proeduka.colorweather.models.Minute;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MinutelyWeatherActivity extends Activity{

    @BindView(R.id.minutelyRecyclerView)
    RecyclerView recyclerViewMinutely;

    @BindView(R.id.minutelyNoDataFound)
    TextView noDataFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minutely_weather);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        ArrayList<Minute> minuteArrayList = intent.getParcelableArrayListExtra(MainActivity.MINUTES_ARRAY_LIST);
        MinutelyWeatherAdapter minutelyWeatherAdapter= new MinutelyWeatherAdapter(this,minuteArrayList);
        if (minutelyWeatherAdapter.getItemCount() == 0){
            noDataFound.setVisibility(View.VISIBLE);
            recyclerViewMinutely.setAdapter(minutelyWeatherAdapter);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerViewMinutely.setLayoutManager(layoutManager);
            recyclerViewMinutely.setHasFixedSize(true);
        }else {
            recyclerViewMinutely.setVisibility(View.GONE);
        }


    }
}
