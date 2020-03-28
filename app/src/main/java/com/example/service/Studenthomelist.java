package com.example.service;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Studenthomelist extends AppCompatActivity {
    ListView list;
    Studenthomeadpt adapter;

    List<StudenthomeViewDataItem> viewall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_home);
        list = (ListView) findViewById(R.id.contactlist);

        viewall=new ArrayList<>();
        try {
            StudentAPI service = Studenthomeclient.getApiService();


            Call<Studenthomelistdata> call = service.getalldetails();

            call.enqueue(new Callback<Studenthomelistdata>() {
                @Override
                public void onResponse(Call<Studenthomelistdata> call, Response<Studenthomelistdata> response) {

                    if (response.isSuccessful()) {

                        viewall = response.body().viewData;
                        adapter = new Studenthomeadpt(Studenthomelist.this, viewall);
                        list.setAdapter(adapter);

                    } else {
                        Toast.makeText(Studenthomelist.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Studenthomelistdata> call, Throwable t) {
                    Toast.makeText(Studenthomelist.this, "Server Problem", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception ex) {
            Log.v("Error", ex.getMessage());
            ex.printStackTrace();

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
