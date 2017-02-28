package com.test.vasilyevanton.testapp.model.GoogleMaps;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class PolylineData implements Parcelable {
    @SerializedName("points")
    @Expose
    private String points;

    public PolylineData() {
    }

    public PolylineData(String points) {
        this.points = points;
    }

    protected PolylineData(Parcel in) {
        points = in.readString();
    }

    public static final Creator<PolylineData> CREATOR = new Creator<PolylineData>() {
        @Override
        public PolylineData createFromParcel(Parcel in) {
            return new PolylineData(in);
        }

        @Override
        public PolylineData[] newArray(int size) {
            return new PolylineData[size];
        }
    };

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(points);
    }
}
