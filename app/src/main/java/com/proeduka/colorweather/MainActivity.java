package com.proeduka.colorweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.descriptionTextView) TextView descriptionWT;
    @BindView(R.id.currentTempTextView) TextView currentTemp;
    @BindView(R.id.highestTempTextView) TextView highestTemp;
    @BindView(R.id.lowestTempTextView) TextView lowestTemp;
    @BindView(R.id.iconImageView) ImageView iconWT;

    @BindDrawable(R.drawable.clear_night) Drawable clearNight;

    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        iconWT.setImageDrawable(clearNight);
    }

    @OnClick(R.id.dailyTextView)
    public void dailyCLic(){
        Intent intent = new Intent(MainActivity.this, DailyWeatherActivity.class);
        startActivity(intent);
        Log.d(TAG,"DailyTextView CLic");
    }
    @OnClick(R.id.hourlyTextView)
    public void hourlyCLic(){
        Intent intent = new Intent(MainActivity.this, HourlyWeatherActivity.class);
        startActivity(intent);
        Log.d(TAG,"HourlyTextView CLic");
    }
    @OnClick(R.id.minutelyTextView)
    public void minutelyCLic(){
        Intent intent = new Intent(MainActivity.this, MinutlyWeatherActivity.class);
        startActivity(intent);
        Log.d(TAG,"MinutelyTextView CLic");
    }



}
