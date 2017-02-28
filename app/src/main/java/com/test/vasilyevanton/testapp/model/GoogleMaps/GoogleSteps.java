package com.test.vasilyevanton.testapp.model.GoogleMaps;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleSteps {

    public GoogleSteps(Point endPoint, PolylineData polylineData, Point startPoint, String htmlInstructions) {
        this.endPoint = endPoint;
        this.polylineData = polylineData;
        this.startPoint = startPoint;
        this.htmlInstructions = htmlInstructions;
    }

    public PolylineData getPolylineData() {
        return polylineData;
    }

    public void setPolylineData(PolylineData polylineData) {
        this.polylineData = polylineData;
    }

    public String getHtmlInstructions() {
        return htmlInstructions;
    }

    public void setHtmlInstructions(String htmlInstructions) {
        this.htmlInstructions = htmlInstructions;
    }

    @SerializedName("polyline")
    @Expose
    private PolylineData polylineData;

    public Point getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(Point startPoint) {
        this.startPoint = startPoint;
    }

    public Point getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Point endPoint) {
        this.endPoint = endPoint;
    }

    @SerializedName("end_location")
    @Expose
    private Point endPoint;

    @SerializedName("start_location")
    @Expose
    private Point startPoint;


    @SerializedName("html_instructions")
    @Expose
    private String htmlInstructions;

    public GoogleSteps() {
    }
}
