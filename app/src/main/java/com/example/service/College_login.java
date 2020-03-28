package com.example.service;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.service.Http.HttpCommunication;
import com.example.service.custmerRegister.College_register;
import com.example.service.ownerRegister.College_Event;
import com.example.service.url.Defines;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



public class College_login extends AppCompatActivity
{
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin,btnRegister;

    String get_id,get_name, get_pass;
    private ProgressDialog pDialog;
    String resultdata = "UserloginArr";
    String Name = "Name";
    String Password = "Password";
    JSONArray jarr = null;
    JSONObject json;
    ArrayList<String> useral = new ArrayList<String>();
    ArrayList<String> passal = new ArrayList<String>();
    String strUser;
    String strPass;
    String Tutor_Days="";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        etUserName = (EditText) findViewById(R.id.etUsername);

        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnSigin);
        btnRegister = (Button) findViewById(R.id.sign);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(College_login.this, College_register.class);
                startActivity(i1);
            }
        });



        btnLogin.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {
                // TODO Auto-generated method stub

                switch (v.getId()) {
                    case R.id.btnSigin:

                        Name = etUserName.getText().toString().trim();
                        Password = etPassword.getText().toString().trim();

                        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
                        params1.add(new BasicNameValuePair("Name", Name));
                        params1.add(new BasicNameValuePair("Password", Password));

                        json = HttpCommunication.makeHttpRequest(Defines.TAG_COLLEGE_LOGIN, "GET", params1, getBaseContext());

                        try {

                            if (json != null) {

                                //  Log.i("Jsonconvert",getPostDataString(json));

                                String message = json.getString(TAG_MESSAGE);
                                int success = json.getInt(TAG_SUCCESS);

                                if (success == 1) {
                                    Intent intent = new Intent(College_login.this, College_Event.class);

                                    Toast.makeText(getApplicationContext(), "Login Succesfull", Toast.LENGTH_LONG).show();
                                    //  Intent i1 = new Intent(Student_Login.this, StudentHomeMaps.class);
                                    // startActivity(i1);

                                    startActivity(intent);

                                }
                                if (success == 0) {
                                    Toast.makeText(getApplicationContext(), "not Login", Toast.LENGTH_LONG).show();

                                }

                            }
                        } catch (Exception e) {
                            Log.i("Error", e.getMessage());
                        }

                        break;
                    default:
                        break;

                }
            }

        });
    }
}