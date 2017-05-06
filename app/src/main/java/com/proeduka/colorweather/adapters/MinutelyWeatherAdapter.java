package com.proeduka.colorweather.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.proeduka.colorweather.R;
import com.proeduka.colorweather.models.Minute;

import java.util.ArrayList;

/**
 * Created by pablo384 on 03/05/17.
 */

public class MinutelyWeatherAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<Minute> minuteArrayList;

    public MinutelyWeatherAdapter(Context context, ArrayList<Minute> minuteArrayList) {
        this.context = context;
        this.minuteArrayList = minuteArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.minutely_list_item,parent,false);
        MinuteViewHolder minuteViewHolder = new MinuteViewHolder(view);
        return minuteViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((MinuteViewHolder)holder).onBind(position);
    }

    @Override
    public int getItemCount() {
        if (minuteArrayList==null){
            return 0;
        }else {
            return minuteArrayList.size();
        }
    }

    class MinuteViewHolder extends RecyclerView.ViewHolder{

        TextView titleTextView;
        TextView rainProbabilityTextView;

        public MinuteViewHolder(View itemView) {
            super(itemView);

            titleTextView = (TextView) itemView.findViewById(R.id.minutelyTitleTextView);
            rainProbabilityTextView = (TextView) itemView.findViewById(R.id.minutelyRainProbabilityTextView);
        }

        public void onBind(int position){

            Minute minute = minuteArrayList.get(position);
            titleTextView.setText(minute.getTitle());
            rainProbabilityTextView.setText(minute.getRainProbability());
        }
    }
}
