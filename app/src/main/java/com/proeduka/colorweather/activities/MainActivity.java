package com.proeduka.colorweather.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
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

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {


    public static final String TAG = MainActivity.class.getSimpleName();
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
    public static final String MINUTES_ARRAY_LIST = "MINUTES_ARRAY_LIST";
    public static final String RAIN_PROBABILITY = "Rain Probability: ";
    public static final String CONNECTION_ERROR = "Connection Error";
    public static final String Htemp = "H: %s°";
    public static final String Ltemp = "L: %s°";
    public static final int requestPermission= 1;
    public static final String LOCATION_ERROR = "Location Error";
    public static final String BASE_URL = "https://api.darksky.net/forecast";
    public static final String API_KEY = "a655ff78f6dffeda1202a1790322c87f";
    public static final String UNITS = "units=si";
    public static final String DATE_FORMAT_DAYS = "EEEE";
    public static ArrayList<Day> dayArrayListWeather;
    public static ArrayList<Hour> hourArrayListWeather;
    public static ArrayList<Minute> minuteArrayListWeather;
    public GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;

    @BindView(R.id.descriptionTextView)
    TextView descriptionWT;
    @BindView(R.id.currentTempTextView)
    TextView currentTemp;
    @BindView(R.id.highestTempTextView)
    TextView highestTemp;
    @BindView(R.id.lowestTempTextView)
    TextView lowestTemp;
    @BindView(R.id.iconImageView)
    ImageView iconWT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        }




    }

    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @OnClick(R.id.dailyTextView)
    public void dailyCLic() {
        Intent intent = new Intent(MainActivity.this, DailyWeatherActivity.class);
        intent.putParcelableArrayListExtra(DAYS_ARRAY_LIST, dayArrayListWeather);
        startActivity(intent);
    }

    @OnClick(R.id.hourlyTextView)
    public void hourlyCLic() {
        Intent intent = new Intent(MainActivity.this, HourlyWeatherActivity.class);
        intent.putParcelableArrayListExtra(HOURS_ARRAY_LIST, hourArrayListWeather);
        startActivity(intent);
    }

    @OnClick(R.id.minutelyTextView)
    public void minutelyCLic() {
        Intent intent = new Intent(MainActivity.this, MinutelyWeatherActivity.class);
        intent.putParcelableArrayListExtra(MINUTES_ARRAY_LIST, minuteArrayListWeather);
        startActivity(intent);
    }
    @OnClick (R.id.refreshImageButton)
    public void refreshCLic(){
        onConnected(null);
        locationUI();
    }

    private CurrentWeather getCurrenteWeatherFromJson(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);

        JSONObject jsonWithCUrrentWeather = jsonObject.getJSONObject(CURRENTLY);
        String summary = jsonWithCUrrentWeather.getString(SUMMARY);
        String icon = jsonWithCUrrentWeather.getString(ICON);
        String temperature = Math.round(jsonWithCUrrentWeather.getDouble(TEMPERATURE)) + "";

        JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject(DAILY);
        JSONArray jsonArray = jsonWhitDailyWeater.getJSONArray(DATA);
        JSONObject jsonWhitTodayData = jsonArray.getJSONObject(0);

        String minTemp = Math.round(jsonWhitTodayData.getDouble(TEMPERATURE_MIN)) + "";
        String maxTemp = Math.round(jsonWhitTodayData.getDouble(TEMPERATURE_MAX)) + "";

        CurrentWeather currentWeather = new CurrentWeather(MainActivity.this);
        currentWeather.setCurrentTemp(temperature);
        currentWeather.setDescriptionWT(summary);
        currentWeather.setIconImageWT(icon);
        currentWeather.setHighestTemp(maxTemp);
        currentWeather.setLowestTemp(minTemp);

        return currentWeather;
    }

    private ArrayList<Day> getDailyWeatherwithJSON(String json) throws JSONException {

        DateFormat date = new SimpleDateFormat(DATE_FORMAT_DAYS);
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Day> dayArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        try {
            JSONObject jsonWhitDailyWeater = jsonObject.getJSONObject(DAILY);
            JSONArray jsonArraywithDailyWeatherDATA = jsonWhitDailyWeater.getJSONArray(DATA);

            for (int i = 0; i < jsonArraywithDailyWeatherDATA.length(); i++) {

                JSONObject arrayOfDATA = jsonArraywithDailyWeatherDATA.getJSONObject(i);

                String rainProbability = RAIN_PROBABILITY + arrayOfDATA.getDouble(PRECIP_PROBABILITY) * 100 + "%";
                String description = arrayOfDATA.getString(SUMMARY);
                String dateDay = date.format(arrayOfDATA.getLong(TIME) * 1000);

                Day day = new Day(dateDay, description, rainProbability);
                dayArrayList.add(day);
            }
        }catch (JSONException o){
            o.printStackTrace();
        }


        return dayArrayList;
    }

    private ArrayList<Hour> getHourlyWeatherJSON(String json) throws JSONException {

        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Hour> hourArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        try {
            JSONObject jsonWhitHourlyWeater = jsonObject.getJSONObject(HOURLY);
            JSONArray jsonArraywithHourlyWeatherDATA = jsonWhitHourlyWeater.getJSONArray(DATA);

            for (int i = 0; i < jsonArraywithHourlyWeatherDATA.length(); i++) {

                JSONObject arrayOfDATA = jsonArraywithHourlyWeatherDATA.getJSONObject(i);


                String description = arrayOfDATA.getString(SUMMARY);
                String dateHour = date.format(arrayOfDATA.getLong(TIME) * 1000);

                Hour hour = new Hour(dateHour, description);
                hourArrayList.add(hour);
            }
        }catch (JSONException i){
                i.printStackTrace();
        }

        return hourArrayList;
    }

    private ArrayList<Minute> getMinutelyWeatherJSON(String json) throws JSONException {

        DateFormat date = new SimpleDateFormat("h:mm a");
        date.setTimeZone(TimeZone.getTimeZone(AMERICA_SANTO_DOMINGO));

        ArrayList<Minute> minuteArrayList = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        try {
            JSONObject jsonWhitMinutelyWeater = jsonObject.getJSONObject(MINUTELY);
            JSONArray jsonArraywithMinutelyWeatherDATA = jsonWhitMinutelyWeater.getJSONArray(DATA);
            for (int i = 0; i < jsonArraywithMinutelyWeatherDATA.length(); i++) {

                JSONObject arrayOfDATA = jsonArraywithMinutelyWeatherDATA.getJSONObject(i);


                String rainProbability = RAIN_PROBABILITY + arrayOfDATA.getDouble(PRECIP_PROBABILITY) + "%";
                String dateMinute = date.format(arrayOfDATA.getLong(TIME) * 1000);

                Minute minute = new Minute(dateMinute, rainProbability);
                minuteArrayList.add(minute);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }

        return minuteArrayList;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(MainActivity.this, CONNECTION_ERROR, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            requestPermission();
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        int hola=5;
        if (mLastLocation != null) {
            locationUI();
            Log.d(TAG,String.valueOf(mLastLocation.getLatitude()));
            Log.d(TAG,String.valueOf(mLastLocation.getLongitude()));
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
    public boolean requestPermission(){
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                ,requestPermission);

        return true;
    }
    public StringRequest requestHTTP(String url){
        return new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            hourArrayListWeather = getHourlyWeatherJSON(response);
                            dayArrayListWeather = getDailyWeatherwithJSON(response);
                            minuteArrayListWeather = getMinutelyWeatherJSON(response);
//                            for (Minute minute : minuteArrayListWeather) {
//                                Log.d(TAG, minute.getTitle());
//                                Log.d(TAG, minute.getRainProbability());
//                                //Log.d(TAG,hour.getRainProbability());
//                            }

                            CurrentWeather currentWeather = getCurrenteWeatherFromJson(response);
                            iconWT.setImageDrawable(currentWeather.getIconDrawableResource());
                            descriptionWT.setText(currentWeather.getDescriptionWT());
                            currentTemp.setText(currentWeather.getCurrentTemp());
                            highestTemp.setText(String.format(Htemp, currentWeather.getHighestTemp()));
                            lowestTemp.setText(String.format(Ltemp, currentWeather.getLowestTemp()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                        //Log.d(TAG, "Response is: " + response.substring(0, 500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, CONNECTION_ERROR, Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
    }
    public void locationUI(){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/19.223319, -70.513781?units=si";
        String latitude = null ;//"37.8267";
        String longitude = null; //"-122.4233";
        if (mLastLocation != null) {
            latitude = mLastLocation.getLatitude()+"";
            longitude = mLastLocation.getLongitude()+"";
        }
        String url = BASE_URL + "/" + API_KEY + "/" + latitude + "," + longitude + "?" + UNITS;
        //String url = "https://api.darksky.net/forecast/1454f81bb70550586e66207bcc3aa8d3/37.8267,-122.4233?units=si";
        StringRequest stringRequest = requestHTTP(url);
        queue.add(stringRequest);
        // Request a string response from the provided URL.
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onConnected(null);
                locationUI();

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
                Toast.makeText(this, LOCATION_ERROR,Toast.LENGTH_LONG).show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

            // other 'case' lines to check for other
            // permissions this app might request

    }
}
