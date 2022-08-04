package com.example.moviesmvvm.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.moviesmvvm.database.TVShowDatabase;
import com.example.moviesmvvm.models.TVShow;
import com.example.moviesmvvm.repositories.TVShowDetailsRepository;
import com.example.moviesmvvm.responses.TVShowDetailsResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import kotlinx.coroutines.flow.Flow;

public class TVShowDetailsViewModel extends AndroidViewModel {

    private final TVShowDetailsRepository tvShowDetailsRepository;
    private final TVShowDatabase tvShowDatabase;

    public TVShowDetailsViewModel(@NonNull Application application){
        super(application);
        tvShowDetailsRepository = new TVShowDetailsRepository();
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public LiveData<TVShowDetailsResponse> getTVShowDetails(String tvShowId){
        return tvShowDetailsRepository.getTvShowDetails(tvShowId);
    }

    public Completable addToWatchlist(TVShow tvShow){
        return tvShowDatabase.tvShowDao().addToWatchList(tvShow);
    }

    public Flowable<TVShow> getTVShowFromWatchlist(String tvShowId){
        return tvShowDatabase.tvShowDao().getTVShowFromWatchlist(tvShowId);
    }

    public Completable removeFromWatchlist(TVShow tvShow){
        return tvShowDatabase.tvShowDao().removeFromWatchlist(tvShow);
    }

}
