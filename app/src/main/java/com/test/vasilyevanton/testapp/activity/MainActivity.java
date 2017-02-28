package com.test.vasilyevanton.testapp.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import com.test.vasilyevanton.testapp.R;
import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleLegs;
import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleRoute;
import com.test.vasilyevanton.testapp.model.GoogleMaps.GoogleTrips;
import com.test.vasilyevanton.testapp.model.LocationPoint;
import com.test.vasilyevanton.testapp.web.API;
import com.test.vasilyevanton.testapp.web.ApiRequest;
import com.test.vasilyevanton.testapp.web.IGoogleMaps;
import com.test.vasilyevanton.testapp.web.IPoints;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private ApiRequest mGoogleApiRequest;
    private ApiRequest mMockableApiRequest;
    private TextView mTextViewGoogle;
    private TextView mTextViewSelf;
    private Handler mHandler;
    private MapView mapView;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler = new Handler(Looper.getMainLooper());

        mapView = (MapView) findViewById(R.id.map);
        mTextViewGoogle = (TextView) findViewById(R.id.google_route_length);
        mTextViewSelf = (TextView) findViewById(R.id.self_route_length);


        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);

        mMockableApiRequest = new ApiRequest(API.MOCKABLE_BASE_URL);
        mGoogleApiRequest = new ApiRequest(API.GOOGLE_BASE_URL);


        mMockableApiRequest.setOnPointsListener(new IPoints() {
            @Override
            public void responseSuccess(final ArrayList<LocationPoint> list) {
                for (Iterator<LocationPoint> it = list.iterator(); it.hasNext(); ) {
                    LocationPoint point = it.next();
                    if (point.getLat() == 0 && point.getLng() == 0) {
                        it.remove();
                    }
                }
                if (list.size() > 1) {
                    googleApiGetRoute(list);
                    new Thread(new Runnable() {
                        public void run() {
                            jspritGetRoute(list);
                        }
                    }).start();
                } else {
                    Log.w("Point counter", String.valueOf(list.size()));
                }
            }

            @Override
            public void responseFail(int code, String message) {

            }
        });
        mGoogleApiRequest.setOnGoogleRouteListener(new IGoogleMaps() {
            @Override
            public void responseSuccess(GoogleTrips body) {
                getAPIPolyline(body.getGoogleRoutes());
            }

            @Override
            public void responseFail(int code, String message) {

            }
        });
    }

    private void jspritGetRoute(final ArrayList<LocationPoint> list) {
        /*
         * TSP problem can be modelled by defining a vehicle routing problem with either a vehicle that has a sufficiently high capacity (to accomodate all services)
         */
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType").addCapacityDimension(0, Integer.MAX_VALUE);
        VehicleType vehicleType = vehicleTypeBuilder.build();

        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle");
        vehicleBuilder.setStartLocation(Location.newInstance(list.get(0).getLat(), list.get(0).getLng()));
        vehicleBuilder.setEndLocation(Location.newInstance(list.get(list.size() - 1).getLat(), list.get(list.size() - 1).getLng()));
        vehicleBuilder.setType(vehicleType);
        VehicleImpl vehicle = vehicleBuilder.build();

        VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
        vrpBuilder.addVehicle(vehicle);

        for (int i = 1; i < list.size() - 1; i++) {
            vrpBuilder.addJob(Service.Builder.newInstance(String.valueOf(i)).setLocation(Location.newInstance(list.get(i).getLat(), list.get(i).getLng())).build());
        }

        VehicleRoutingProblem problem = vrpBuilder.build();
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(problem);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();

        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
        List<VehicleRoute> routeArrayList = new ArrayList<>(bestSolution.getRoutes());
        VehicleRoute vehicleRoute = routeArrayList.get(0);

        list.get(0).setPriority(0); //Start point
        list.get(list.size() - 1).setPriority(list.size() - 1); //End point
        for (TourActivity act : vehicleRoute.getActivities()) {
            int jobId;
            if (act instanceof TourActivity.JobActivity) {
                jobId = Integer.valueOf(((TourActivity.JobActivity) act).getJob().getId());
                list.get(jobId).setPriority(jobId);
            }
        }

        SolutionPrinter.print(problem, bestSolution, SolutionPrinter.Print.VERBOSE);

        Collections.sort(list, new Comparator<LocationPoint>() {
            public int compare(LocationPoint o1, LocationPoint o2) {
                if (o1.getPriority() < o2.getPriority()) return -1;
                if (o1.getPriority() > o2.getPriority()) return 1;
                return 0;
            }
        });

        double distance = 0;
        for (int i = 1; i < list.size(); i++) {
            android.location.Location startLocation = new android.location.Location("A");
            startLocation.setLatitude(list.get(i - 1).getLat());
            startLocation.setLongitude(list.get(i - 1).getLng());

            android.location.Location endLocation = new android.location.Location("B");
            endLocation.setLatitude(list.get(i).getLat());
            endLocation.setLongitude(list.get(i).getLng());
            distance += startLocation.distanceTo(endLocation);

        }
        distance /= 1000;
        distance = new BigDecimal(distance).setScale(3, RoundingMode.UP).doubleValue();
        final double finalDistance = distance;
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                getSelfPolyline(list, finalDistance);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        this.mGoogleMap.setBuildingsEnabled(true);
        this.mGoogleMap.getUiSettings().setMapToolbarEnabled(false);

        mMockableApiRequest.setRequestGetPoints();
    }

    private void getMarker(LatLng cord, float color) {
        if (mGoogleMap != null) {
            mGoogleMap.addMarker(new MarkerOptions().position(cord).icon(BitmapDescriptorFactory.defaultMarker(color)));
        }
    }

    private void googleApiGetRoute(ArrayList<LocationPoint> list) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i > 0 && i < list.size() - 1) {
                sb.append(list.get(i).getLat());
                sb.append(",");
                sb.append(list.get(i).getLng());
                if (i < list.size() - 2) {
                    sb.append("|");
                }
            }
        }
        Log.w("waypoints", sb.toString());

        mGoogleApiRequest.setRequestGetGoogleRoute(list.get(0).getLat() + "," + list.get(0).getLng(), list.get(list.size() - 1).getLat() + "," + list.get(list.size() - 1).getLng(), sb.toString(), getString(R.string.google_api));

    }

    private void getSelfPolyline(ArrayList<LocationPoint> data, double distance) {
        if (mGoogleMap != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            PolylineOptions route = new PolylineOptions();
            route.geodesic(true);
            for (int j = 0; j < data.size(); j++) {
                LatLng point = new LatLng(data.get(j).getLat(), data.get(j).getLng());
                route.add(point);
                builder.include(point);
            }
            route.width(12).color(this.getResources().getColor(R.color.colorAccent));
            this.mGoogleMap.addPolyline(route);
            getMarker(new LatLng(data.get(0).getLat(), data.get(0).getLng()), BitmapDescriptorFactory.HUE_ROSE);
            getMarker(new LatLng(data.get(data.size() - 1).getLat(), data.get(data.size() - 1).getLng()), BitmapDescriptorFactory.HUE_ROSE);
            mTextViewSelf.setText(String.valueOf(distance) + "km");

            int size = getResources().getDisplayMetrics().widthPixels - 60;
            LatLngBounds latLngBounds = builder.build();
            CameraUpdate track = CameraUpdateFactory.newLatLngBounds(latLngBounds, size, size, 25);
            mGoogleMap.animateCamera(track);
        }
    }

    private void getAPIPolyline(ArrayList<GoogleRoute> data) {
        if (mGoogleMap != null) {
            PolylineOptions line = new PolylineOptions();
            List<LatLng> route = PolyUtil.decode(data.get(0).getPolylineData().getPoints());
            for (int i = 0; i < route.size(); i++) {
                line.add(route.get(i));
            }
            line.width(12).color(this.getResources().getColor(R.color.colorPrimary));
            this.mGoogleMap.addPolyline(line);
            getMarker(new LatLng(route.get(0).latitude, route.get(0).longitude), BitmapDescriptorFactory.HUE_BLUE);
            getMarker(new LatLng(route.get(route.size() - 1).latitude, route.get(route.size() - 1).longitude), BitmapDescriptorFactory.HUE_BLUE);
            List<GoogleLegs> googleLegses = data.get(0).getGoogleLegses();
            double distance = 0;
            for (GoogleLegs googleLegs : googleLegses) {
                distance += googleLegs.getGoogleTotalDistance().getTotalDistanceValue();
            }
            distance /= 1000;
            distance = new BigDecimal(distance).setScale(3, RoundingMode.UP).doubleValue();
            mTextViewGoogle.setText(String.valueOf(distance) + "km");
        }
    }
}
