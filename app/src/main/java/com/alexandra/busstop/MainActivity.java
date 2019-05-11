package com.alexandra.busstop;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    ListView busStopsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }


    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();

        busStopsView = findViewById(R.id.listView);

        loadBusStops();
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        cursor.close();
    }


    public void loadBusStops() {
        ArrayList<BusStop> busStops = BusStop.load(db, cursor);
        Log.d("123", "busstops");
        for(int i = 0; i < busStops.size(); i++) {
            Log.d("123", busStops.get(i).name + " " + busStops.get(i).lat + " " + busStops.get(i).lng);
        }

        BusStopsAdapter adapter = new BusStopsAdapter(this, busStops);
        busStopsView.setAdapter(adapter);
    }

    public void onAddBusStopButtonClicked(View view) {
        Intent intent = new Intent(this, AddBusStopActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        BusStop busStop = (BusStop) data.getSerializableExtra("busStop");
        BusStop.save(busStop, db);

        Toast toast = Toast.makeText(getApplicationContext(), "Добавлена новая остановка: " + busStop.name, Toast.LENGTH_SHORT);
        toast.show();

        loadBusStops();
    }
}
