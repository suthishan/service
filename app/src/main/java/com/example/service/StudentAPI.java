package com.example.service;


import retrofit2.Call;
import retrofit2.http.GET;


public interface StudentAPI {
    @GET("college_event.php")
    Call<Studenthomelistdata> getalldetails();
}
