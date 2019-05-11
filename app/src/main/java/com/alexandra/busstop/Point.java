package com.alexandra.busstop;


import java.io.Serializable;

public class Point implements Serializable {

    double lat, lng;


    public Point(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
