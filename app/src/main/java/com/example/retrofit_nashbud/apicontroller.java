package com.example.retrofit_nashbud;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class apicontroller {
    private static Retrofit retrofit;
    private static final String url = "http://universities.hipolabs.com/";
    private static apicontroller object;

    apicontroller()
    {
        retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    public static synchronized apicontroller getInstance()
    {
        if (object == null)
            object = new apicontroller();
        return object;
    }

    CallApi getapi(){
        return retrofit.create(CallApi.class);
    }

}
