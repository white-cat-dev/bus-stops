package com.alexandra.busstop;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class AddBusStopActivity extends AppCompatActivity {

    EditText latView, lngView, nameView, distanceView;

    LocationManager locationManager;
    double myLat = 0;
    double myLng = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bus_stop);

        latView = findViewById(R.id.lat);
        lngView = findViewById(R.id.lng);
        nameView = findViewById(R.id.name);
        distanceView = findViewById(R.id.distance);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                myLat = location.getLatitude();
                myLng = location.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}

            public void onProviderEnabled(String provider) {}

            public void onProviderDisabled(String provider) {}
        };


        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        }
        catch (SecurityException e) {};
    }


    public void onAddButtonClicked(View view) {
        double lat = Double.parseDouble(latView.getText().toString());
        double lng = Double.parseDouble(lngView.getText().toString());
        String name = nameView.getText().toString();
        float distance = Float.parseFloat(distanceView.getText().toString());

        BusStop busStop = new BusStop(lat, lng, name, distance);

        Intent intent = new Intent();
        intent.putExtra("busStop", busStop);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void onMyLocationButtonClicked(View view) {
        latView.setText("" + myLat);
        lngView.setText("" + myLng);
    }

    public void onMapLocationButtonClicked(View view) {

    }
}
