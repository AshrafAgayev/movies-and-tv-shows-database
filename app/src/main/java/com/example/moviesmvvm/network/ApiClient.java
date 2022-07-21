package com.example.moviesmvvm.network;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit;

    public static Retrofit getRetrofit(){
        if(retrofit ==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.episodate.com/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
