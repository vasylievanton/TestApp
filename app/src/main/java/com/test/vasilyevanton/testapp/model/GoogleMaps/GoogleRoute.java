package com.test.vasilyevanton.testapp.model.GoogleMaps;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GoogleRoute {


    public GoogleRoute(PolylineData polylineData, List<GoogleLegs> googleLegses) {
        this.polylineData = polylineData;
        this.googleLegses = googleLegses;
    }

    public List<GoogleLegs> getGoogleLegses() {
        return googleLegses;
    }

    public void setGoogleLegses(List<GoogleLegs> googleLegses) {
        this.googleLegses = googleLegses;
    }

    @SerializedName("overview_polyline")
    private PolylineData polylineData;

    @SerializedName("legs")
    private List<GoogleLegs> googleLegses;


    public GoogleRoute() {
    }

    public PolylineData getPolylineData() {
        return polylineData;
    }

    public void setPolylineData(PolylineData polylineData) {
        this.polylineData = polylineData;
    }
}
