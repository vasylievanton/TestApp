package com.test.vasilyevanton.testapp.model.GoogleMaps;

import com.google.gson.annotations.SerializedName;

public class GoogleTotalDistance {
    public String getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(String totalDistance) {
        this.totalDistance = totalDistance;
    }

    public int getTotalDistanceValue() {
        return totalDistanceValue;
    }

    public void setTotalDistanceValue(int totalDistanceValue) {
        this.totalDistanceValue = totalDistanceValue;
    }

    @SerializedName("text")
    private String totalDistance;
    @SerializedName("value")
    private int totalDistanceValue;

    public GoogleTotalDistance(int totalDistanceValue, String totalDistance) {
        this.totalDistanceValue = totalDistanceValue;
        this.totalDistance = totalDistance;
    }

    public GoogleTotalDistance() {
    }
}
