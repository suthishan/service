package com.example.service;

import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.service.Http.HttpCommunication;
import com.example.service.custmerRegister.TempStorage;
import com.example.service.url.Defines;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends AppCompatActivity {
    private RecyclerView recyclerview;
    private String Collegename;
    private String Department;
    private String Eventname;

    String Discription;
    String Eventdate;
    String Lastreg;
    String City;
    String latt;
    String longt;
    private List<EventDetail> memberList;

    String semail, spass;

    JSONArray jarr = null;
    JSONObject json;

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";
    String resultdata = "data";


    TempStorage tempStorage;

    EventDetail objtrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studenthome);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        memberList=new ArrayList<EventDetail>();
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(StudentHome.this);
        //StaggeredGridLayoutManager;
        //GridLayoutManager gridLayoutManager
        tempStorage = new TempStorage(getApplicationContext());

       // objtrack=new TrackBusDetail();
        recyclerview.setLayoutManager(layoutManager);
        BusList();
    }


    public void BusList()
    {
        List<NameValuePair> params1 = new ArrayList<NameValuePair>();
       // params1.add(new BasicNameValuePair("driver_name", strName));
       // params1.add(new BasicNameValuePair("phone", strMobile));

        json = HttpCommunication.makeHttpRequest(Defines.TAG_COLLEGE_EVNETS_LIST, "GET", params1,getApplicationContext());

        try {

            // Log.i("Jsonconvert",getPostDataString(json));
            if (json != null) {
                jarr = json.getJSONArray(resultdata);

                Log.i("array", "" + jarr.length());

                for (int i = 0; i < jarr.length(); i++) {

                    JSONObject c = jarr.getJSONObject(i);
                    //       String name,driver,phone,sourcetime,desttime,sourceplace,destplace;
                    Collegename= c.getString("Collegename");
                    Department= c.getString("Department");
                    Eventname= c.getString("Eventname");
                    Discription=c.getString("Discription");
                    Eventdate=c.getString("Eventdate");
                    Lastreg=c.getString("Lastreg");
                    City=c.getString("City");

                    String Name = tempStorage.getUserName();
                    String Email = tempStorage.getMail();




                    // Log.i("data",name+","+driver+","+phone+","+sourceplace+","+destplace+","+sourcetime+","+desttime+","+dates);
                    objtrack=new EventDetail(Collegename,Department,Eventname,Discription,Eventdate,Lastreg,City,Name,Email);
                    memberList.add(objtrack);

                }

            }

            StudentHomeListAdapter adapter = new StudentHomeListAdapter(memberList, StudentHome.this);
            recyclerview.setAdapter(adapter);

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Erorr"+e.getMessage().toString(), Toast.LENGTH_LONG).show();
            e.getMessage();
        }


    }


}
