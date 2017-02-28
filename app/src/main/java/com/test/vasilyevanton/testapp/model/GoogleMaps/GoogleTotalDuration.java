package com.test.vasilyevanton.testapp.model.GoogleMaps;


import com.google.gson.annotations.SerializedName;

public class GoogleTotalDuration {

    public GoogleTotalDuration(String totalDuration, int totalDurationValue) {
        this.totalDuration = totalDuration;
        this.totalDurationValue = totalDurationValue;
    }

    public GoogleTotalDuration() {
    }

    public String getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(String totalDuration) {
        this.totalDuration = totalDuration;
    }

    public int getTotalDurationValue() {
        return totalDurationValue;
    }

    public void setTotalDurationValue(int totalDurationValue) {
        this.totalDurationValue = totalDurationValue;
    }

    @SerializedName("text")
    private String totalDuration;
    @SerializedName("value")
    private int totalDurationValue;

}
