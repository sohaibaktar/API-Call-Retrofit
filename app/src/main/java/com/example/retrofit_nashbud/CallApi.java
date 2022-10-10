package com.example.retrofit_nashbud;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;

public interface CallApi {

    @GET("posts")
    Call<List<model>> getAllData();


    @GET("search?country=India")
    Call<List<university_model>> getmodels(

    );

}
