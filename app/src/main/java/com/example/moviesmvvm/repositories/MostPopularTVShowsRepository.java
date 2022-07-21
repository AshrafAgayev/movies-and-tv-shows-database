package com.example.moviesmvvm.repositories;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviesmvvm.network.ApiClient;
import com.example.moviesmvvm.network.ApiService;
import com.example.moviesmvvm.responses.TVShowResponse;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsRepository {

    private final ApiService apiService;

    public MostPopularTVShowsRepository() {
        apiService = ApiClient.getRetrofit().create(ApiService.class);
    }

    public LiveData<TVShowResponse> getMostPopularTvShows(int page) {

        MutableLiveData<TVShowResponse> data = new MutableLiveData<>();
        apiService.getMostPopularTvShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(@NonNull Call<TVShowResponse> call, @NonNull Response<TVShowResponse> response) {
                data.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<TVShowResponse> call, @NonNull Throwable t) {


            }
        });

        return data;
    }
}
