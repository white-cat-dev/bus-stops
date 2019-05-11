package com.alexandra.busstop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class BusStop extends Point implements Serializable {

    String name;
    float distance;


    public BusStop(double lat, double lng, String name, float distance) {
        super(lat, lng);
        this.name = name;
        this.distance = distance;
    }


    public static ArrayList<BusStop> load(SQLiteDatabase db, Cursor сursor) {
        ArrayList<BusStop> busStops = new ArrayList<>();
        сursor =  db.rawQuery("SELECT * FROM " + DatabaseHelper.TABLE, null);

        while (сursor.moveToNext()) {
            String name = сursor.getString(сursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            double lat = сursor.getDouble(сursor.getColumnIndex(DatabaseHelper.COLUMN_LAT));
            double lng = сursor.getDouble(сursor.getColumnIndex(DatabaseHelper.COLUMN_LNG));
            float distance = сursor.getFloat(сursor.getColumnIndex(DatabaseHelper.COLUMN_DISTANCE));

            BusStop busStop = new BusStop(lat, lng, name, distance);
            busStops.add(busStop);
        }
        return busStops;
    }


    public static void save(BusStop busStop, SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLUMN_LAT, busStop.lat);
        contentValues.put(DatabaseHelper.COLUMN_LNG, busStop.lng);
        contentValues.put(DatabaseHelper.COLUMN_NAME, busStop.name);
        contentValues.put(DatabaseHelper.COLUMN_DISTANCE, busStop.distance);

        db.insert(DatabaseHelper.TABLE, null, contentValues);
    }


    public static void save(List<BusStop> busStops, SQLiteDatabase db) {

    }


    public static void saveFromJson(String busStops, SQLiteDatabase db) {
        String dbInsertValues = "";

        try {
            JSONArray busStopsJson = new JSONArray(busStops);

            for (int i = 0; i < busStopsJson.length(); i++) {
                JSONObject busStopJson = (JSONObject) busStopsJson.get(i);
                String name = busStopJson.getString("bus_stop_name");

                JSONObject pointJson = (JSONObject) busStopJson.get("point");
                double lat = pointJson.getDouble("lat");
                double lng = pointJson.getDouble("lng");

                JSONObject pointBeforeJson = (JSONObject) busStopJson.get("point_before");
                double latBefore = pointBeforeJson.getDouble("lat");
                double lngBefore = pointBeforeJson.getDouble("lng");

                float[] distance = new float[3];
                Location.distanceBetween(lat, lng, latBefore, lngBefore, distance);

                dbInsertValues += "('" + name + "', " + lat + ", " + lng + ", " + distance[0] + "), ";
            }
        } catch (JSONException e) {}


        if (dbInsertValues.length() > 0) {
            dbInsertValues = dbInsertValues.substring(0, dbInsertValues.length() - 2);

            db.execSQL("INSERT INTO " + DatabaseHelper.TABLE + " (" +
                    DatabaseHelper.COLUMN_NAME + ", " +
                    DatabaseHelper.COLUMN_LAT + ", " +
                    DatabaseHelper.COLUMN_LNG + ", " +
                    DatabaseHelper.COLUMN_DISTANCE + ") VALUES " + dbInsertValues + ";");
        }
    }
}
