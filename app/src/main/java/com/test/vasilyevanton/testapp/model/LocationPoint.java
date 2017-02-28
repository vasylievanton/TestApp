package com.test.vasilyevanton.testapp.model;

/**
 * Created by Vasilyev Anton on 27.02.2017.
 */

public class LocationPoint {
    private double lat;
    private double lng;
    private int priority;

    public LocationPoint() {
    }

    public LocationPoint(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
