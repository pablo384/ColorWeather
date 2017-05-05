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
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    public static final String DATA = "data";
    public static final String HOURLY = "hourly";
    public static final String SUMMARY = "summary";
    public static final String CURRENTLY = "currently";
    public static final String DAILY = "daily";
    public static final String ICON = "icon";
    public static final String TEMPERATURE = "temperature";
    public static final String TEMPERATURE_MIN = "temperatureMin";
    public static final String TEMPERATURE_MAX = "temperatureMax";
    public static final String PRECIP_PROBABILITY = "precipProbability";
    public static final String TIME = "time";
    public static final String MINUTELY = "minutely";
    public static final String AMERICA_SANTO_DOMINGO = "America/Santo_Domingo";
    public static final String DAYS_ARRAY_LIST = "DAYS_ARRAY_LIST";
    public static final String HOURS_ARRAY_LIST = "HOURS_ARRAY_LIST";
    @BindView(R.id.descriptionTextView) TextView descriptionWT;
    @BindView(R.id.currentTempTextView) TextView currentTemp;
    @BindView(R.id.highestTempTextView) TextView highestTemp;
    @BindView(R.id.lowestTempTextView) TextView lowestTemp;
    @BindView(R.id.iconImageView) ImageView iconWT;
    public static ArrayList<Day> dayArrayListPrueba;
    public static ArrayList<Hour> hourArrayListPrueba;
    public static ArrayList<Minute> minuteArrayListPrueba;


    public static final String TAG=MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/19.223319, -70.513781?units=si";
        String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/37.8267,-122.4233?units=si";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hourArrayListPrueba = getHourlyWeatherJSON(response);
                            dayArrayListPrueba = getDailyWeatherwithJSON(response);
                            minuteArrayListPrueba = getMinutelyWeatherJSON(response);
                            for (Minute minute: minuteArrayListPrueba){
                                Log.d(TAG,minute.getTitle());
                                Log.d(TAG,minute.getRainProbability());
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
        intent.putParcelableArrayListExtra(DAYS_ARRAY_LIST,dayArrayListPrueba);
        startActivity(intent);
        Log.d(TAG,"DailyTextView CLic");
    }
    @OnClick(R.id.hourlyTextView)
    public void hourlyCLic(){
        Intent intent = new Intent(MainActivity.this, HourlyWeatherActivity.class);
        intent.putParcelableArrayListExtra(HOURS_ARRAY_LIST,hourArrayListPrueba);
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

        JSONObject jsonWithCUrrentWeather = jsonObject.getJSONObject(CURRENTLY);
        String summary = jsonWithCUrrentWeather.getString(SUMMARY);
        String icon = jsonWithCUrrentWeather.getString(ICON);
        String temperature = Math.round(jsonWithCUrrentWeather.getDouble(TEMPERATURE))+"";

        JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject(DAILY);
        JSONArray jsonArray = jsonWhitDailyWeater.getJSONArray(DATA);
        JSONObject jsonWhitTodayData= jsonArray.getJSONObject(0);

        String minTemp = Math.round(jsonWhitTodayData.getDouble(TEMPERATURE_MIN))+"";
        String maxTemp = Math.round(jsonWhitTodayData.getDouble(TEMPERATURE_MAX))+"";

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
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Day> dayArrayList = new ArrayList<Day>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject(DAILY);
        JSONArray jsonArraywithDailyWeatherDATA = jsonWhitDailyWeater.getJSONArray(DATA);

        for (int i=0; i < jsonArraywithDailyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithDailyWeatherDATA.getJSONObject(i);

            String rainProbability = "Rain Probability: "+arrayOfDATA.getDouble(PRECIP_PROBABILITY)*100+"%";
            String description = arrayOfDATA.getString(SUMMARY);
            String dateDay = date.format(arrayOfDATA.getLong(TIME)*1000);

            Day day = new Day(dateDay, description, rainProbability);
            dayArrayList.add(day);
        }

        return dayArrayList;
    }
    private ArrayList<Hour> getHourlyWeatherJSON(String json) throws JSONException{

        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Hour> hourArrayList = new ArrayList<Hour>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitHourlyWeater = jsonObject.getJSONObject(HOURLY);
        JSONArray jsonArraywithHourlyWeatherDATA = jsonWhitHourlyWeater.getJSONArray(DATA);

        for (int i=0; i < jsonArraywithHourlyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithHourlyWeatherDATA.getJSONObject(i);


            String description = arrayOfDATA.getString(SUMMARY);
            String dateHour = date.format(arrayOfDATA.getLong(TIME)*1000);

            Hour hour = new Hour(dateHour, description);
            hourArrayList.add(hour);
        }
        return hourArrayList;
    }
    private ArrayList<Minute> getMinutelyWeatherJSON(String json) throws JSONException{

        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Minute> minuteArrayList = new ArrayList<Minute>();

        JSONObject jsonObject= new JSONObject(json);

        JSONObject jsonWhitMinutelyWeater = jsonObject.getJSONObject(MINUTELY);
        JSONArray jsonArraywithMinutelyWeatherDATA = jsonWhitMinutelyWeater.getJSONArray(DATA);

        for (int i=0; i < jsonArraywithMinutelyWeatherDATA.length(); i++){

            JSONObject arrayOfDATA = jsonArraywithMinutelyWeatherDATA.getJSONObject(i);


            String rainProbability = arrayOfDATA.getDouble(PRECIP_PROBABILITY)+"%";
            String dateMinute = date.format(arrayOfDATA.getLong(TIME)*1000);

            Minute minute = new Minute(dateMinute, rainProbability);
            minuteArrayList.add(minute);
        }
        return minuteArrayList;
    }

}
