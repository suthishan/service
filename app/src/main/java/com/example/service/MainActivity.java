package com.example.service;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.service.custmerRegister.TempStorage;
import com.example.service.location.LocationFetch;

import org.json.JSONArray;
import org.json.JSONObject;



import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements LocationListener
{

    String strPhone;

    TempStorage objTempStorage;

    final Context c = this;

    JSONArray jarr = null;
    JSONObject json;

    LocationFetch objloc;

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


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        objTempStorage=new TempStorage(getApplicationContext());

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        this.mContext=getApplicationContext();
        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

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



        PreviousUserLogin();
    }

    public void PreviousUserLogin()
    {
        String uname=objTempStorage.getUserName();
        // String pass=objTempStorage.getPassword();
        String phone=objTempStorage.getPhone();
        // String mail=objTempStorage.getMail();
        String em,na;

        if(uname!=null && phone!=null) {

            // String password = pass;
            Log.i("Name",uname);
            Log.i("phone",phone);

        }
        else
        {
            Log.i("storage","Empty");

        }

    }

    public void Customer(View v)
    {
        Intent i = new Intent(MainActivity.this, Student_login.class);
        startActivity(i);
    }

    public void Owner(View v)
    {
        Intent i = new Intent(MainActivity.this, College_login.class);
        startActivity(i);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(provider_info, 400, 0, this);
        }
    }

    /* Remove the locationlistener updates when Activity is paused */
    @Override
    protected void onPause() {
        super.onPause();
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
