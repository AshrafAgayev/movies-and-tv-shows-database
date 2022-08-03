package com.example.moviesmvvm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.adapters.WatchlistAdapter;
import com.example.moviesmvvm.databinding.ActivityWatchlistBinding;
import com.example.moviesmvvm.listeners.WatchlistListener;
import com.example.moviesmvvm.models.TVShow;
import com.example.moviesmvvm.viewmodels.WatchlistViewmodel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity implements WatchlistListener {

    private ActivityWatchlistBinding activityWatchlistBinding;
    private WatchlistViewmodel viewmodel;
    private WatchlistListener watchlistListener;
    private WatchlistAdapter watchlistAdapter;
    private List<TVShow> watchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWatchlistBinding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);

        doInitialization();
    }

    private void doInitialization(){
        viewmodel = new ViewModelProvider(this).get(WatchlistViewmodel.class);
        activityWatchlistBinding.imageBack.setOnClickListener(view -> onBackPressed());
        watchlist = new ArrayList<>();
    }

    private void loadWatchlist(){
        activityWatchlistBinding.setIsLoading(true);

        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(viewmodel.loadWatchlist().subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe( tvShows -> {
                    activityWatchlistBinding.setIsLoading(false);
                    if(watchlist.size() > 0){
                        watchlist.clear();
                    }
                    watchlist.addAll(tvShows);
                    watchlistAdapter = new WatchlistAdapter(watchlist, this);
                    activityWatchlistBinding.watchlistRecyclerview.setAdapter(watchlistAdapter);
                    activityWatchlistBinding.watchlistRecyclerview.setVisibility(View.VISIBLE);
                    compositeDisposable.dispose();

                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }


    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }

    @Override
    public void removeTVShowFromWatchlist(TVShow tvShow, int position) {

    }
}