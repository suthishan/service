package com.example.service;

import android.Manifest;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.service.Http.HttpCommunication;
import com.example.service.url.Defines;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class EventLocation extends FragmentActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

//private GoogleMap mMap;

        //  private GoogleMap mMap;
        Location mLastLocation;
        Marker mCurrLocationMarker;
        GoogleApiClient mGoogleApiClient;
        LocationRequest mLocationRequest;

private static final int HANDLER_DELAY = 1000*60*2;

        Location gpslocation = null;

private static final int GPS_TIME_INTERVAL = 60000; // get gps location every 1 min
private static final int GPS_DISTANCE= 0; // set the distance value in meter

private Handler handler = new Handler();
        int count=0;

        double strLat=0.0,strLang=0.0;
        double latt=0.0,langt=0.0;

        String lattstr,longtstr;

        JSONArray jarr = null;
        JSONObject json;

private static final String TAG_SUCCESS = "success";
private static final String TAG_MESSAGE = "message";

        String resultdata = "data";


private GoogleMap mMap;
    Bundle p;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eventlocation);

        p=getIntent().getExtras();

        name= p.getString("Collegename");
            getBusGpsLocation();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

            setTimers();
    }


@Override
public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PERMISSION_GRANTED)
        {
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }
        }
        else
        {
        buildGoogleApiClient();
        mMap.setMyLocationEnabled(true);
        }

        }

public void setTimers()
        {
        try
        {
        Runnable runnable = new Runnable() {
public void run() {
        if (mLastLocation != null) {
                getBusGpsLocation();

        onLocationChanged(mLastLocation);
        } else {
        System.out.println("Location not avilable");
        }

        handler.postDelayed(this, 60000);
        }
        };


        handler.postDelayed(runnable, 60000);

        }
        catch(Exception e)
        {
        Log.i("timer Error : ",e.getMessage().toString());
        }
        }


protected synchronized void buildGoogleApiClient()
        {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
        }

@Override
public void onConnected(Bundle bundle)
        {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_FINE_LOCATION)
        == PERMISSION_GRANTED)
        {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

        }

@Override
public void onConnectionSuspended(int i)
        {

        }

@Override
public void onLocationChanged(Location location) {

        mLastLocation = location;

        if (mCurrLocationMarker != null)
        {
        mCurrLocationMarker.remove();
        }

        String locat=location.getLatitude()+" ,"+location.getLongitude();

        Toast.makeText(getApplicationContext(),locat , Toast.LENGTH_LONG).show();
        // String strduration=TrackDay.GetTodayDuration().toString().trim();

        // geouser.setDuration(strduration);
        // geouser.setLattitude(location.getLatitude());
        // geouser.setLongtitude(location.getLongitude());

        //  Fref.child(strduration).setValue(geouser);


        Log.i("count: "+count++,locat);
        //Place current location marker
        strLat=location.getLatitude();
        strLang=location.getLongitude();

        if(!lattstr.isEmpty() && !longtstr.isEmpty())
        {
                // latt = strLat;
                // langt = strLang;
               // double number = -895.25;
               // String numberAsString = String.valueOf(lattstr);
               // String numberAsString = String.valueOf(longtstr);

                latt= Double.parseDouble(lattstr);
                langt= Double.parseDouble(longtstr);


                LatLng latLngs = new LatLng(latt,langt);
                MarkerOptions fmarkerOptions = new MarkerOptions();

                fmarkerOptions.position(latLngs);
                fmarkerOptions.title(name);

                fmarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                 fmarkerOptions.icon(BitmapDescriptorFactory.defaultMarker());

                mCurrLocationMarker = mMap.addMarker(fmarkerOptions);
                Log.i("Find: ",latt+" : "+langt);

        }


        //String lattstr=String.valueOf(strLat);

        //String longtstr=String.valueOf(strLang);


       // Saves(strLat,strLang);
        //  geoFire.setLocation(currentUser, new GeoLocation(strLat, strLang), new GeoFire.CompletionListener() {
        //@Override
        //    public void onComplete(String key, DatabaseError error) {
        //         if (error != null) {
        //             System.err.println("There was an error saving the location to GeoFire: " + error);
        //        } else {
        //          System.out.println("Location saved on server successfully!");
        //       }
        //  }

        // });

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());

        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));

        //stop location updates
        if (mGoogleApiClient != null)
        {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        }

public void getBusGpsLocation()
        {

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("Collegename", name));

        json = HttpCommunication.makeHttpRequest(Defines.TAG_COLLEGE_LOCATION, "GET", params1,getApplicationContext());

        try {

                if (json != null) {
                        jarr = json.getJSONArray(resultdata);

                        Log.i("array", "" + jarr.length());

                        for (int i = 0; i < jarr.length(); i++) {

                                JSONObject c = jarr.getJSONObject(i);
                                //       String name,driver,phone,sourcetime,desttime,sourceplace,destplace;
                                name = c.getString("Collegename");
                                lattstr = c.getString("Lattitude");
                                longtstr = c.getString("Longtitude");

                                Log.i("fetch gps",name+" ,"+lattstr+","+longtstr);
                        }
                }
        }catch (Exception e)
        {
        Toast.makeText(getApplicationContext(), "Erorr"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
        e.getMessage();
        }



        }



@Override
public void onConnectionFailed(ConnectionResult connectionResult)
        {

        }

        }
