package com.proeduka.colorweather.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.proeduka.colorweather.models.CurrentWeather;
import com.proeduka.colorweather.R;
import com.proeduka.colorweather.models.Day;
import com.proeduka.colorweather.models.Hour;
import com.proeduka.colorweather.models.Minute;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {



    @BindView(R.id.descriptionTextView) TextView descriptionWT;
    @BindView(R.id.currentTempTextView) TextView currentTemp;
    @BindView(R.id.highestTempTextView) TextView highestTemp;
    @BindView(R.id.lowestTempTextView) TextView lowestTemp;
    @BindView(R.id.iconImageView) ImageView iconWT;
    public static ArrayList<Day> dayArrayListPrueba;
    public static ArrayList<Hour> hourArrayListPrueba;


    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/19.223319, -70.513781?units=si";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hourArrayListPrueba = getHourlyWeatherJSON(response);
                            dayArrayListPrueba = getDailyWeatherwithJSON(response);
                            for (Hour hour: hourArrayListPrueba){
                                Log.d(TAG,hour.getTitle());
                                Log.d(TAG,hour.getWeatherdescription());
                                //Log.d(TAG,hour.getRainProbability());
                            }

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
        Intent intent = new Intent(MainActivity.this, MinutelyWeatherActivity.class);
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
    private ArrayList<Day> getDailyWeatherwithJSON(String json) throws JSONException{

        DateFormat date = new SimpleDateFormat("EEEE");

        ArrayList<Day> dayArrayList = new ArrayList<Day>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject("daily");
        JSONArray jsonArraywithDailyWeatherDATA = jsonWhitDailyWeater.getJSONArray("data");

        for (int i=0; i < jsonArraywithDailyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithDailyWeatherDATA.getJSONObject(i);

            String rainProbability = arrayOfDATA.getDouble("precipProbability")+"";
            String description = arrayOfDATA.getString("summary");
            String dateDay = date.format(arrayOfDATA.getLong("time")*1000);

            Day day = new Day(dateDay, description, rainProbability);
            dayArrayList.add(day);
        }

        return dayArrayList;
    }
    private ArrayList<Hour> getHourlyWeatherJSON(String json) throws JSONException{

        DateFormat date = new SimpleDateFormat("h:mm a");

        ArrayList<Hour> hourArrayList = new ArrayList<Hour>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitHourlyWeater = jsonObject.getJSONObject("hourly");
        JSONArray jsonArraywithHourlyWeatherDATA = jsonWhitHourlyWeater.getJSONArray("data");

        for (int i=0; i < jsonArraywithHourlyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithHourlyWeatherDATA.getJSONObject(i);


            String description = arrayOfDATA.getString("summary");
            String dateHour = date.format(arrayOfDATA.getLong("time")*1000);

            Hour hour = new Hour(dateHour, description);
            hourArrayList.add(hour);
        }
        return hourArrayList;
    }
    private ArrayList<Minute> getMinutelyWeatherJSON(String json) throws JSONException{

        DateFormat date = new SimpleDateFormat("h:mm a");

        ArrayList<Minute> minuteArrayList = new ArrayList<Minute>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitMinutelyWeater = jsonObject.getJSONObject("hourly");
        JSONArray jsonArraywithMinutelyWeatherDATA = jsonWhitMinutelyWeater.getJSONArray("data");

        for (int i=0; i < jsonArraywithMinutelyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithMinutelyWeatherDATA.getJSONObject(i);


            String description = arrayOfDATA.getString("summary");
            String dateHour = date.format(arrayOfDATA.getLong("time")*1000);

            Minute minute = new Minute(dateHour, description);
            minuteArrayList.add(minute);
        }
        return minuteArrayList;
    }

}
