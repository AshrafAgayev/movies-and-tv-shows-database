package com.example.moviesmvvm.viewmodels;

import android.app.Application;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.moviesmvvm.database.TVShowDatabase;
import com.example.moviesmvvm.models.TVShow;

import java.util.List;

import io.reactivex.Flowable;

public class WatchlistViewmodel extends AndroidViewModel {
    private TVShowDatabase tvShowDatabase;

    public WatchlistViewmodel(@NonNull Application application) {
        super(application);
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchlist() {
        return tvShowDatabase.tvShowDao().getWatchList();
    }
}
