package com.dciets.androidutils.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.dciets.androidutils.listener.MyLocationListener;

/**
 * Created by marc-antoinehinse on 2016-07-06.
 */
@SuppressWarnings("MissingPermission")
public class LocationUtils implements LocationListener{
    private static final String TAG = "LocationUtils";
    private static LocationUtils instance;
    private LocationManager locationManager;
    private Context context;
    private MyLocationListener listener;

    private LocationUtils(final Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public static void init(final Context context) {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission is not granted");
                return;
            }
        }
        instance = new LocationUtils(context);
    }

    public static LocationUtils getInstance() {
        return instance;
    }

    public Location getLastKnownLocation() {
        Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if(locationGPS != null && locationNetwork != null) {
            if(locationGPS.getAccuracy() >= locationNetwork.getAccuracy())
            {
                return locationGPS;
            }

            return locationNetwork;
        } else if(locationGPS != null) {
            return locationGPS;
        }

        return locationNetwork;
    }

    public void triggerLocationRequest() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 100, this);
    }

    public void setLocationListener(final MyLocationListener listener) {
        this.listener = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
