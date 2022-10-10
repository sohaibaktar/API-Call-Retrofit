package com.example.retrofit_nashbud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        processData();
    }

    private void processData() {
        Call<List<university_model>> call = apicontroller.getInstance().getapi().getmodels();

        call.enqueue(new Callback<List<university_model>>() {
            @Override
            public void onResponse(Call<List<university_model>> call, Response<List<university_model>> response) {
                List<university_model> data = response.body();
                myAdapter adapter = new myAdapter(data);
                recyclerView.setAdapter(adapter);
                Toast.makeText(MainActivity.this, "Fetch Complete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<university_model>> call, Throwable t) {
                Log.d("TAG", "onFailure: "+t.toString());
            }
        });
    }
}