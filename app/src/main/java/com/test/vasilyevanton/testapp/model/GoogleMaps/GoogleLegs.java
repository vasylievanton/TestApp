package com.test.vasilyevanton.testapp.model.GoogleMaps;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GoogleLegs {
    public List<GoogleSteps> getStepsList() {
        return stepsList;
    }

    public void setStepsList(List<GoogleSteps> stepsList) {
        this.stepsList = stepsList;
    }

    public GoogleTotalDuration getGoogleTotalDuration() {
        return googleTotalDuration;
    }

    public void setGoogleTotalDuration(GoogleTotalDuration googleTotalDuration) {
        this.googleTotalDuration = googleTotalDuration;
    }

    public GoogleTotalDistance getGoogleTotalDistance() {
        return googleTotalDistance;
    }

    public void setGoogleTotalDistance(GoogleTotalDistance googleTotalDistance) {
        this.googleTotalDistance = googleTotalDistance;
    }

    @SerializedName("steps")
    private List<GoogleSteps> stepsList;
    @SerializedName("duration")
    private GoogleTotalDuration googleTotalDuration;
    @SerializedName("distance")
    private GoogleTotalDistance googleTotalDistance;

    public GoogleLegs(List<GoogleSteps> stepsList, GoogleTotalDuration googleTotalDuration, GoogleTotalDistance googleTotalDistance) {
        this.stepsList = stepsList;
        this.googleTotalDuration = googleTotalDuration;
        this.googleTotalDistance = googleTotalDistance;
    }

    public GoogleLegs() {
    }
}
