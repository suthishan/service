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
import com.example.service.custmerRegister.StudentRegister;
import com.example.service.custmerRegister.TempStorage;
import com.example.service.url.Defines;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Student_login extends AppCompatActivity
{
    private EditText etUserName;
    private EditText etPassword;
    private Button btnLogin,btnRegister;

    String get_id,get_name, get_pass;
    private ProgressDialog pDialog;
    String resultdata = "data";
    String Name = "Name";
    String Password = "Password";
    JSONArray jarr = null;
    JSONObject json;
    ArrayList<String> useral = new ArrayList<String>();
    ArrayList<String> passal = new ArrayList<String>();
    String strUser;
    String strPass;
    String Tutor_Days="";
    String Email;

    TempStorage tempStorage;

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

        tempStorage = new TempStorage(getApplicationContext());






        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i1 = new Intent(Student_login.this, StudentRegister.class);
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

                        json = HttpCommunication.makeHttpRequest(Defines.TAG_STUDENT_LOGIN, "GET", params1, getApplicationContext());

                        try {

                            if (json != null) {
                                jarr = json.getJSONArray(resultdata);

                                Log.i("array", "" + jarr.length());

                                for (int i = 0; i < jarr.length(); i++) {

                                    JSONObject c = jarr.getJSONObject(i);
                                    //       String name,driver,phone,sourcetime,desttime,sourceplace,destplace;
                                    Name = c.getString("Name");
                                    Email = c.getString("Email");
                                    tempStorage.SetUserName(Name);

                                    tempStorage.SetMail(Email);

                                    Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_LONG).show();
Intent k = new Intent(Student_login.this,StudentHome.class);

startActivity(k);
                                }


                            }
else {
                                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_LONG).show();
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