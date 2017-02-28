package com.test.vasilyevanton.testapp.web;

import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleTrips;
import com.test.vasilyevanton.testapp.model.LocationPoint;

import java.util.ArrayList;

/**
 * Created by Vasilyev Anton on 27.02.2017.
 */

public interface IPoints {
    void responseSuccess(ArrayList<LocationPoint> body);
    void responseFail(int code, String message);
}
