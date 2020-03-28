package com.example.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Studenthomeclient {
    private static final String ROOT_URL = "http://www.infoziant.com/android/nearbycollegeevents/";

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static StudentAPI getApiService() {
        return getRetrofitInstance().create(StudentAPI.class);
    }
}
