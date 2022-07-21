package com.example.moviesmvvm.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviesmvvm.repositories.MostPopularTVShowsRepository;
import com.example.moviesmvvm.responses.TVShowResponse;

public class MostPopularTvShowsViewModel extends ViewModel {

    private MostPopularTVShowsRepository mostPopularTVShowsRepository;

    public MostPopularTvShowsViewModel(){
        mostPopularTVShowsRepository = new MostPopularTVShowsRepository();
    }

    public LiveData<TVShowResponse> getMostPopularTvShows(int page){
        return mostPopularTVShowsRepository.getMostPopularTvShows(page);
    }

}
