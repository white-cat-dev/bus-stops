package com.alexandra.busstop;

import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "db";
    private static final int SCHEMA = 2;
    static final String TABLE = "bus_stops";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DISTANCE = "distance";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + TABLE + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_LAT + " REAL, " +
                COLUMN_LNG + " REAL, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_DISTANCE + " REAL);");


        String busStops = "[{ bus_stop_name: \"Центральный рынок\", point: { lat: 52.281355, lng: 104.291827 },  point_before: { lat: 52.279168, lng: 104.287676 } }," +
                "{ bus_stop_name: \"Волжская\", point: { lat: 52.262811, lng: 104.313426 },  point_before: { lat: 52.264775, lng: 104.309257 } }," +
                "{ bus_stop_name: \"Проезд Юрия Тена\", point: { lat: 52.254626, lng: 104.256950 },  point_before: { lat: 52.256018, lng: 104.253419 } }," +
                "{ bus_stop_name: \"Площадь Декабристов\", point: { lat: 52.280235, lng: 104.309316 },  point_before: { lat: 52.282078, lng: 104.305175 } }]";

        BusStop.saveFromJson(busStops, db);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}