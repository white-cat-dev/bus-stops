package com.alexandra.busstop;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class BusStopsAdapter extends BaseAdapter {

    ArrayList<BusStop> busStops;
    LayoutInflater lInflater;
    Context context;

    BusStopsAdapter(Context context, ArrayList<BusStop> busStops) {
        this.context = context;
        this.busStops = busStops;

        lInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return busStops.size();
    }

    @Override
    public Object getItem(int position) {
        return busStops.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.bus_stop_item, parent, false);
        }

        BusStop busStop = (BusStop)getItem(position);

        ((TextView)view.findViewById(R.id.name)).setText(busStop.name);
        ((TextView)view.findViewById(R.id.lat_lng)).setText("Координаты: (" + busStop.lat + "; " + busStop.lng + ")");
        ((TextView)view.findViewById(R.id.distance)).setText("Расстояние: " + Math.round(busStop.distance) + " м");

        return view;
    }
}
