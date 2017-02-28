package com.test.vasilyevanton.testapp.web;


import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleTrips;
import com.test.vasilyevanton.testapp.model.LocationPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface API {

    String GOOGLE_BASE_URL = "https://maps.googleapis.com";
    String MOCKABLE_BASE_URL = "http://www.mocky.io";


    @Headers(
            {
                    "Content-Type: application/json",
                    "Accept: application/json"
            })
    @GET("/maps/api/directions/json")
    Call<GoogleTrips> getGoogleRoute(@Query("origin") String origin, @Query("destination") String destinations, @Query("waypoints") String waypoints, @Query("key") String key);

    @Headers(
            {
                    "Content-Type: application/json",
                    "Accept: application/json"
            })
    @GET("/v2/58aaa8f9100000730e4b62b7")
    Call<ArrayList<LocationPoint>> getLocationPoints();
}
