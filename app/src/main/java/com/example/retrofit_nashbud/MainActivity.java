package com.example.retrofit_nashbud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.txt);
        tv.setText("");

        CallApi callapi = retrofitClient.getRetrofit().create(CallApi.class);
        Call<List<university_model>> call = callapi.getmodels();

        call.enqueue(new Callback<List<university_model>>() {
            @Override
            public void onResponse(Call<List<university_model>> call, Response<List<university_model>> response) {
                Log.d("TAG", "onResponse: "+response.body());
                List<university_model> data = response.body();
                for (int i = 0; i < data.size(); i++) {
                    tv.append("name: "+data.get(i).getName()+
                                "\nstate:"+data.get(i).getState_province()+
                                "\nCountry:"+data.get(i).getState_province()+
                                "\ncode:"+data.get(i).getAlpha_two_code()+
                                "\ndomain:"+data.get(i).getDomains()+
                                "\nweb_pages:"+data.get(i).getWeb_pages()+"\n\n\n");
                }


            }

            @Override
            public void onFailure(Call<List<university_model>> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.toString());
            }
        });



    }
}