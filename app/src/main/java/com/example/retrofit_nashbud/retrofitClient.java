package com.example.retrofit_nashbud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitClient {
    private static Retrofit retrofit;
    private static String url = "http://universities.hipolabs.com/";

    public static Retrofit getRetrofit() {
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
