package com.example.service.location;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class LocationFetch implements LocationListener {

    private Context mContext;
    private Location mLocation;
    private LocationManager locationManager;
    private PendingIntent intent;
    private Location cLocation;


    private static long distance;
    private static long minutes;

    //public LocationManager locationManager;
    private String provider,provider_info;

    public String latitude;

   public String longtitude;


   public LocationFetch(Context mContext)
    {
        this.mContext=mContext;
        locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider_info = LocationManager.NETWORK_PROVIDER;
        // provider = locationManager.getBestProvider(criteria, false);
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_GRANTED)
        {

            Location location = locationManager.getLastKnownLocation(provider_info);


          // onLocationChanged(location);
        }
    }

    protected void onResume() {
       // super.onResume();
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_GRANTED) {

            locationManager.requestLocationUpdates(provider_info, 400, 0, this);
        }
    }

    /* Remove the locationlistener updates when Activity is paused */

    protected void onPause() {
        //super.onPause();
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location)
    {

        Log.i("C Loc",""+location.getLatitude()+" , "+location.getLongitude());
        Criteria criteria = new Criteria();
        provider_info = LocationManager.NETWORK_PROVIDER;
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_GRANTED) {
            cLocation = locationManager.getLastKnownLocation(provider_info);
            Log.i("last loc  ",""+cLocation);
        }

        latitude= String.valueOf(location.getLatitude());
        longtitude= String.valueOf(location.getLongitude());

        Toast.makeText(mContext, ""+location.getLatitude()+" , "+location.getLongitude(), Toast.LENGTH_LONG).show();
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
