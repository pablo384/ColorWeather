package com.proeduka.colorweather.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proeduka.colorweather.models.Hour;
import com.proeduka.colorweather.R;

import java.util.ArrayList;

/**
 * Created by pablo384 on 03/05/17.
 */

public class HourlyWeatherAdapter extends BaseAdapter {

    ArrayList<Hour> hours;
    Context context;
    public static String TAG= "Vista";

    public HourlyWeatherAdapter(ArrayList<Hour> hours, Context context) {
        this.hours = hours;
        this.context = context;
    }

    @Override
    public int getCount() {

        if (hours==null){
            return 0;
        }else {
        return hours.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return hours.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;//no se usara
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderHour viewHolderHour;

        if (convertView==null){
            Log.d(TAG,"Creando vista desde cero");
            convertView = LayoutInflater.from(context).inflate(R.layout.hourly_list_item, parent,false);

            viewHolderHour = new ViewHolderHour();
            viewHolderHour.hourTitle = (TextView) convertView.findViewById(R.id.hourlyTitleTextView);
            viewHolderHour.weatherDescription = (TextView) convertView.findViewById(R.id.hourlyDescriptionTextView);

            convertView.setTag(viewHolderHour);

        }else {
            viewHolderHour=(ViewHolderHour)convertView.getTag();
        }

        Hour hour = hours.get(position);
        viewHolderHour.hourTitle.setText(hour.getTitle());
        viewHolderHour.weatherDescription.setText(hour.getWeatherdescription());

        return convertView;
    }

    static class ViewHolderHour {
        TextView hourTitle;
        TextView weatherDescription;
    }
}


