package com.test.vasilyevanton.testapp.web;


import android.util.Log;

import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleTrips;
import com.test.vasilyevanton.testapp.model.LocationPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiRequest {

    private final API api;
    private IGoogleMaps iGoogleMaps;
    private IPoints iPoints;


    public ApiRequest(String baseURL) {
        api = RetrofitManager.loadAPI(baseURL);
    }

    public void setOnGoogleRouteListener(IGoogleMaps listener) {
        iGoogleMaps = listener;
    }

    public void setOnPointsListener(IPoints listener) {
        iPoints = listener;
    }


    public void setRequestGetGoogleRoute(String origin, String destination, String waypoints, String key) {
        Call<GoogleTrips> messageCall = api.getGoogleRoute(origin, destination, waypoints, key);
        messageCall.enqueue(new Callback<GoogleTrips>() {
            @Override
            public void onResponse(Call<GoogleTrips> call, Response<GoogleTrips> response) {
                Log.w("response_google", response.code() + "\n" + response.message() + "\n");
                if (response.isSuccessful()) {
                    Log.w("Good", "Good");
                    iGoogleMaps.responseSuccess(response.body());
                } else {
                    iGoogleMaps.responseFail(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<GoogleTrips> call, Throwable t) {
                try {
                    Log.w("Bad", t.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                iGoogleMaps.responseFail(1, "Fail");
            }
        });
    }

    public void setRequestGetPoints() {
        Call<ArrayList<LocationPoint>> messageCall = api.getLocationPoints();
        messageCall.enqueue(new Callback<ArrayList<LocationPoint>>() {
            @Override
            public void onResponse(Call<ArrayList<LocationPoint>> call, Response<ArrayList<LocationPoint>> response) {
                Log.w("response_points", response.code() + "\n" + response.message() + "\n");
                if (response.isSuccessful()) {
                    Log.w("Good", "Good");
                    iPoints.responseSuccess(response.body());
                } else {
                    iPoints.responseFail(response.code(), response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LocationPoint>> call, Throwable t) {
                try {
                    Log.w("Bad", t.getMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                iPoints.responseFail(1, "Fail");
            }
        });
    }
}
