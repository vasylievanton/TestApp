package com.test.vasilyevanton.testapp.web;

import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleTrips;

/**
 * Created by Vasilyev Anton on 27.02.2017.
 */

public interface IGoogleMaps {
    void responseSuccess(GoogleTrips body);

    void responseFail(int code, String message);

}
