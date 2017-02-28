package com.test.vasilyevanton.testapp.model.GoogleMaps;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GoogleTrips {
    public ArrayList<GoogleRoute> getGoogleRoutes() {
        return googleRoutes;
    }

    public void setGoogleRoutes(ArrayList<GoogleRoute> googleRoutes) {
        this.googleRoutes = googleRoutes;
    }


    @SerializedName("routes")
    private ArrayList<GoogleRoute> googleRoutes;




    public GoogleTrips(ArrayList<GoogleRoute> googleRoutes) {
        this.googleRoutes = googleRoutes;
    }

    public GoogleTrips() {
    }
}
