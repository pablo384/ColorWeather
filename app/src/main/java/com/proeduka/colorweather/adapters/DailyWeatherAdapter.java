package com.proeduka.colorweather.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proeduka.colorweather.Day;
import com.proeduka.colorweather.R;

import java.util.ArrayList;

/**
 * Created by pablo384 on 29/04/17.
 */

public class DailyWeatherAdapter extends BaseAdapter {

    ArrayList<Day> days;
    Context context;

    public DailyWeatherAdapter (Context context, ArrayList<Day> days){
        this.context=context;
        this.days=days;

    }
    @Override
    public int getCount() {return days.size();}

    @Override
    public Object getItem(int position) {
        return days.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;//no se usara
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = LayoutInflater.from(context).inflate(R.layout.daily_list_item,null);
        TextView dayTitle =(TextView) view.findViewById(R.id.dailyListTitle);
        TextView dayDescription =(TextView) view.findViewById(R.id.dailyListDescription);
        TextView dayProbability =(TextView) view.findViewById(R.id.dailyListProbability);

        Day day= days.get(position);
        dayTitle.setText(day.getDayName());
        dayDescription.setText(day.getWeatherDescription());
        dayProbability.setText(day.getRainProbability());

        return view;
    }
}
