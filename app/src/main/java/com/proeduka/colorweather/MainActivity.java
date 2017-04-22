package com.proeduka.colorweather;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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


    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/37.8267,-122.4233?units=si";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            CurrentWeather currentWeather = getCurrenteWeatherFromJson(response);
                            iconWT.setImageDrawable(currentWeather.getIconDrawableResource());
                            descriptionWT.setText(currentWeather.getDescriptionWT());
                            currentTemp.setText(currentWeather.getCurrentTemp());
                            highestTemp.setText(String.format("H: %s°",currentWeather.getHighestTemp()));
                            lowestTemp.setText(String.format("L: %s°",currentWeather.getLowestTemp()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                        Log.d(TAG, "Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                descriptionWT.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
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

    private CurrentWeather getCurrenteWeatherFromJson(String json) throws JSONException{
        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWithCUrrentWeather = jsonObject.getJSONObject("currently");
        String summary = jsonWithCUrrentWeather.getString("summary");
        String icon = jsonWithCUrrentWeather.getString("icon");
        String temperature = Math.round(jsonWithCUrrentWeather.getDouble("temperature"))+"";

        JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject("daily");
        JSONArray jsonArray = jsonWhitDailyWeater.getJSONArray("data");
        JSONObject jsonWhitTodayData= jsonArray.getJSONObject(0);

        String minTemp = Math.round(jsonWhitTodayData.getDouble("temperatureMin"))+"";
        String maxTemp = Math.round(jsonWhitTodayData.getDouble("temperatureMax"))+"";

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);
        currentWeather.setCurrentTemp(temperature);
        currentWeather.setDescriptionWT(summary);
        currentWeather.setIconImageWT(icon);
        currentWeather.setHighestTemp(maxTemp);
        currentWeather.setLowestTemp(minTemp);

        return currentWeather;
    }



}
