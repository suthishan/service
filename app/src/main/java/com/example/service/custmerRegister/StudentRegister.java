package com.example.service.custmerRegister;

import android.Manifest;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.service.Http.HttpCommunication;
import com.example.service.R;
import com.example.service.Student_login;
import com.example.service.Validation;
import com.example.service.location.LocationFetch;
import com.example.service.url.Defines;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import static android.content.pm.PackageManager.PERMISSION_GRANTED;


public class StudentRegister extends AppCompatActivity implements View.OnClickListener, LocationListener
{

    EditText etname,etphone,email,password;
    Button btnSend;
    ProgressDialog pDialog;

    String Name,Phone,Email,Password;
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    Validation validation;

    JSONArray jarr = null;
    JSONObject json;
    InputStream is;

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



    String insert_url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        objTempStorage = new TempStorage(getApplicationContext());


        etname = (EditText) findViewById(R.id.etname);
        etphone = (EditText) findViewById(R.id.etphone);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        btnSend = (Button) findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                boolean em = isValidMail(email.getText().toString().trim());
                String mobi = etphone.getText().toString().trim();
                String Password = password.getText().toString();
                if (etname.getText().toString().equals("") || (password.getText().toString().equals("")) || (etphone.getText().toString().equals(""))) {
                    Toast.makeText(StudentRegister.this, "Pleae Enter All Field", Toast.LENGTH_SHORT).show();
                } else if (!em) {
                    email.requestFocus();
                    email.setError("Invalid EMAILID!");
                    Toast.makeText(StudentRegister.this, "please enter valid email", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 10) {
                    password.requestFocus();
                    password.setError("Password cannot be less than 6 characters!");
                } else if (mobi.length() < 10) {
                    etphone.requestFocus();
                    etphone.setError("Please Enter Valid 10 Digit Mobile Number!");
                } else if (!haveNetworkConnection()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StudentRegister.this);
                    builder.setTitle("Track");
                    builder.setMessage("Please On Mobile data or WIFI Connection.");
                    builder.setCancelable(false);

                    builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();


                } else {
                    Name = etname.getText().toString().trim();
                    Email = email.getText().toString().trim();
                    Phone = etphone.getText().toString().trim();
                    Password = password.getText().toString().trim();


                    new Loadregister().execute();
                }
            }

            private boolean isValidMail(String email) {
                return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }
            private boolean haveNetworkConnection() {
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                return haveConnectedWifi || haveConnectedMobile;
            }
            protected void hideKeyboard(View view)
            {
                InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }



        class Loadregister extends AsyncTask<String, String, String> {


                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    pDialog = new ProgressDialog(StudentRegister.this);
                    pDialog.setMessage("Registering Please wait...");
                    pDialog.setIndeterminate(false);
                    pDialog.setCancelable(false);
                    pDialog.show();
                }

                @Override
                protected String doInBackground(String... arg0) {

                    // TODO Auto-generated method stub
                    HttpClient httpclient = new DefaultHttpClient();
                    try {
                        List<NameValuePair> nameValuePair = new ArrayList<>();

                        nameValuePair.add(new BasicNameValuePair("name", Name));
                        nameValuePair.add(new BasicNameValuePair("email", Email));
                        nameValuePair.add(new BasicNameValuePair("password", Password));
                        nameValuePair.add(new BasicNameValuePair("mobile", Phone));


                        String params1 = URLEncodedUtils.format(nameValuePair, "utf-8");
                        insert_url += "?" + params1;
                        Log.i("urlvalue", insert_url);
                        HttpGet httpget = new HttpGet(insert_url);
                        HttpResponse httpresponse = httpclient.execute(httpget);
                        HttpEntity httpentity = httpresponse.getEntity();
                        is = httpentity.getContent();
                    } catch (ClientProtocolException e) {
                    } catch (IOException e) {
                    }

                    return null;
                }

                protected void onPostExecute(String file_url) {
                    // dismiss the dialog after getting all products
                    pDialog.dismiss();

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub

                            try {

                                Toast.makeText(getApplicationContext(), "INFORMATION Save SucessFully", Toast.LENGTH_LONG).show();
                                Intent i1 = new Intent(StudentRegister.this, Student_login.class);
                                startActivity(i1);

						/*overridePendingTransition(R.anim.slide_in_right,
								R.anim.slide_out_right);*/
                                finish();

                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    });

                }

            }
        });
    
        

        validation = new Validation();

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
        Name=etname.getText().toString().trim();
        Phone=etphone.getText().toString().trim();
        Email=email.getText().toString().trim();
        Password=password.getText().toString().trim();
        // String Type="Atm";
        Log.i("Data entry validation","success") ;

        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
        params1.add(new BasicNameValuePair("Name", Name));
        params1.add(new BasicNameValuePair("phone", Phone));
        params1.add(new BasicNameValuePair("Email", Email));
        params1.add(new BasicNameValuePair("Password", Password));

        params1.add(new BasicNameValuePair("Lattitude",latitude));
        params1.add(new BasicNameValuePair("Longtitude", longtitude));

        json = HttpCommunication.makeHttpRequest(Defines.TAG_STUDENT_REGISTER, "GET", params1,getApplicationContext());
        try {
            //  Log.i("Jsonconvert",getPostDataString(json));
            if (json != null) {

                String message = json.getString(TAG_MESSAGE);
                int success = json.getInt(TAG_SUCCESS);
                if(success==1)
                {

                    Intent o = new Intent(StudentRegister.this, Student_login.class);
                    Toast.makeText(getApplicationContext(), "Data  Registered Succesfull", Toast.LENGTH_LONG).show();
                    objTempStorage.setAllClear();
                    objTempStorage.SetUserName(Name);
                    // objTempStorage.SetPassword(get_pass);
                    // objTempStorage.SetMail(em);
                    objTempStorage.SetPhone(Phone);
                    startActivity(o);

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


