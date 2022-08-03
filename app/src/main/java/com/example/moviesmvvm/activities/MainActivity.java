package com.example.moviesmvvm.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.adapters.TVShowsAdapter;
import com.example.moviesmvvm.databinding.ActivityMainBinding;
import com.example.moviesmvvm.listeners.TVShowsListener;
import com.example.moviesmvvm.models.TVShow;
import com.example.moviesmvvm.viewmodels.MostPopularTvShowsViewModel;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements TVShowsListener {

    private ActivityMainBinding activityMainBinding;
    private MostPopularTvShowsViewModel viewModel;
    private List<TVShow> tvshows = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        doInitialization();
    }

    private void doInitialization() {
        activityMainBinding.tvShowsRecyclerView.setHasFixedSize(true);
        viewModel = new ViewModelProvider(this).get(MostPopularTvShowsViewModel.class);
        tvShowsAdapter = new TVShowsAdapter(tvshows, this);
        activityMainBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);
        activityMainBinding.tvShowsRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!activityMainBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (currentPage <= totalAvailablePages) {
                        currentPage += 1;
                        getMostPopularTvShows();
                    }

                }
            }
        });

        Intent intent = new Intent(this, WatchlistActivity.class);
        activityMainBinding.imageWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });
        getMostPopularTvShows();
    }

    private void getMostPopularTvShows() {
        toggleLoading();
        viewModel.getMostPopularTvShows(currentPage).observe(this, mostPopularTvShowsResponse -> {

            toggleLoading();

            totalAvailablePages = mostPopularTvShowsResponse.getPages();
            if (mostPopularTvShowsResponse.getTvShows() != null) {
                int oldCount = tvshows.size();
                tvshows.addAll(mostPopularTvShowsResponse.getTvShows());
                tvShowsAdapter.notifyItemRangeInserted(oldCount, tvshows.size());

            }
        });
    }

    private void toggleLoading() {
        if (currentPage == 1) {
            activityMainBinding.setIsLoading(activityMainBinding.getIsLoading() == null || !activityMainBinding.getIsLoading());
        } else {
            if (activityMainBinding.getIsLoadingMore() != null && activityMainBinding.getIsLoadingMore()) {
                activityMainBinding.setIsLoadingMore(true);
            } else {
                activityMainBinding.setIsLoadingMore(false);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(getApplicationContext(), TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);
    }
}

