package com.example.service.ownerRegister;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.service.Http.HttpCommunication;
import com.example.service.R;
import com.example.service.custmerRegister.TempStorage;
import com.example.service.location.LocationFetch;
import com.example.service.url.Defines;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class College_Event extends AppCompatActivity implements View.OnClickListener, LocationListener
{

    EditText collegename,department,eventname,discription,eventdate,lastreg,city;
    Button btnSend;

    String Collegename,Department,Eventname,Discription,Eventdate,Lastreg,City;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

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

    String strPhone;

    TempStorage objTempStorage;

    final Context c = this;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.college_event);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        objTempStorage=new TempStorage(getApplicationContext());


        collegename = (EditText)findViewById(R.id.collegename);
        department = (EditText)findViewById(R.id.department);
        eventname = (EditText)findViewById(R.id.eventname);
        discription = (EditText)findViewById(R.id.discription);
        eventdate = (EditText)findViewById(R.id.eventdate);
        lastreg = (EditText)findViewById(R.id.lastreg);
        city = (EditText)findViewById(R.id.City);

        btnSend = (Button) findViewById(R.id.btnSend);

        //  objloc=new LocationFetch(getApplicationContext());

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

        try {
            btnSend.setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View v) {
        // Log.i("Latt",objloc.latitude);
        //  Log.i("Longt",objloc.longtitude);

        getData();
    }

    public void getData()
    {
        Collegename=collegename.getText().toString().trim();
        Department=department.getText().toString().trim();
        Eventname=eventname.getText().toString().trim();
        Discription=discription.getText().toString().trim();
        Eventdate=eventdate.getText().toString().trim();
        Lastreg=lastreg.getText().toString().trim();
        City=city.getText().toString().trim();




        // String Type="Atm";
        Log.i("Data entry validation","success") ;

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("Collegename", Collegename));
        params1.add(new BasicNameValuePair("Department", Department));
        params1.add(new BasicNameValuePair("Eventname", Eventname));
        params1.add(new BasicNameValuePair("Discription", Discription));
        params1.add(new BasicNameValuePair("Eventdate", Eventdate));
        params1.add(new BasicNameValuePair("Lastreg", Lastreg));
        params1.add(new BasicNameValuePair("City", City));

        params1.add(new BasicNameValuePair("Lattitude",latitude));
        params1.add(new BasicNameValuePair("Longtitude", longtitude));

        json = HttpCommunication.makeHttpRequest(Defines.TAG_COLLEGE_EVNETS, "GET", params1,getApplicationContext());
        try {
            //  Log.i("Jsonconvert",getPostDataString(json));
            if (json != null) {

                String message = json.getString(TAG_MESSAGE);
                int success = json.getInt(TAG_SUCCESS);
                if(success==1)
                {


                    Toast.makeText(getApplicationContext(), "Data  Registered Succesfull", Toast.LENGTH_LONG).show();
                    objTempStorage.setAllClear();
                    objTempStorage.SetUserName(Collegename);
                    // objTempStorage.SetPassword(get_pass);
                    // objTempStorage.SetMail(em);
                    objTempStorage.SetPhone(Department);


                }
                if(success==0)
                {
                    Toast.makeText(getApplicationContext(), "Data  not Register", Toast.LENGTH_LONG).show();

                }
                Log.i("success status : " + success, message);
            }
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erorr"+e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();

            e.getMessage();
        }

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


