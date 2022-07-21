package com.example.moviesmvvm.network;

import com.example.moviesmvvm.responses.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("most-popular")
    Call<TVShowResponse> getMostPopularTvShows(@Query("page") int page);
}
