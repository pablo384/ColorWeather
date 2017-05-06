package com.proeduka.colorweather.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.proeduka.colorweather.models.Day;
import com.proeduka.colorweather.R;

import java.util.ArrayList;

/**
 * Created by pablo384 on 29/04/17.
 */

public class DailyWeatherAdapter extends BaseAdapter {

    ArrayList<Day> days;
    Context context;
    public static String TAG= "Vista";

    public DailyWeatherAdapter (Context context, ArrayList<Day> days){
        this.context=context;
        this.days=days;

    }
    @Override
    public int getCount() {
        if (days==null){
            return 0;
        }else {
        return days.size();}
    }

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

        ViewHolder viewHolder;


        if (view==null){

            view = LayoutInflater.from(context).inflate(R.layout.daily_list_item,parent,false);
            Log.d(TAG,"Crendo vista desde 0");
            viewHolder=new ViewHolder();
            viewHolder.dayTitle=(TextView) view.findViewById(R.id.dailyListTitle);
            viewHolder.dayDescription= (TextView) view.findViewById(R.id.dailyListDescription);
            viewHolder.dayProbability=(TextView) view.findViewById(R.id.dailyListProbability);

            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Day day= days.get(position);
        viewHolder.dayTitle.setText(day.getDayName());
        viewHolder.dayDescription.setText(day.getWeatherDescription());
        viewHolder.dayProbability.setText(day.getRainProbability());

        return view;
    }

    static class ViewHolder{
        TextView dayTitle;
        TextView dayDescription;
        TextView dayProbability;

    }
}
