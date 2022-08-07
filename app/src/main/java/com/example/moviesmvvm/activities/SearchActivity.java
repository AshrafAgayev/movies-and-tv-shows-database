package com.example.moviesmvvm.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.adapters.TVShowsAdapter;
import com.example.moviesmvvm.databinding.ActivitySearchBinding;
import com.example.moviesmvvm.listeners.TVShowsListener;
import com.example.moviesmvvm.models.TVShow;
import com.example.moviesmvvm.viewmodels.SearchViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity implements TVShowsListener {

    private ActivitySearchBinding searchBinding;
    private SearchViewModel searchViewModel;
    private List<TVShow> tvShowList = new ArrayList<>();
    private TVShowsAdapter tvShowsAdapter;
    private int currentPage = 1;
    private int totalAvailablePages = 1;
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        doInitialization();
    }

    private void doInitialization() {
        searchBinding.imageBack.setOnClickListener(view -> onBackPressed());
        searchBinding.tvShowsRecyclerView.setHasFixedSize(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        tvShowsAdapter = new TVShowsAdapter(tvShowList, this);
        searchBinding.tvShowsRecyclerView.setAdapter(tvShowsAdapter);

        // Edittextde yazanda deyishen texte uygun sorgunun gonderilmesi
        searchBinding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (timer != null) {
                    timer.cancel();  //davamli yazanda timer dayanir ve sorgu gondermir
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    if (!editable.toString().trim().isEmpty()) {
                        timer = new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                currentPage = 1;
                                tvShowList.clear();
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    public void run() {
                                        searchTVShow(editable.toString());
                                    }
                                });
                            }
                        }, 800);  //yazini yazdiqdan 800 millisaniye sonra sorgu gedir
                    }else{
                        timer =new Timer();
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                tvShowList.clear();
                            }
                        }, 1000);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        searchBinding.tvShowsRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!searchBinding.tvShowsRecyclerView.canScrollVertically(1)) {
                    if (!searchBinding.inputSearch.getText().toString().isEmpty()) {
                        if (currentPage < totalAvailablePages) {
                            currentPage += 1;
                            searchTVShow(searchBinding.inputSearch.getText().toString());
                        }
                    }
                }
            }
        });

        searchBinding.inputSearch.requestFocus();

    }

    private void searchTVShow(String query) {

        try{
            toggleLoading();
            searchViewModel.searchTVShow(query, currentPage).observe(this, tvShowResponse -> {
                toggleLoading();
                if (tvShowResponse != null) {
                    if (tvShowResponse.getTvShows().size() !=0) {
                        int oldCount = tvShowList.size();


                        tvShowList.addAll(tvShowResponse.getTvShows());
                        totalAvailablePages = tvShowResponse.getTotal();

                        Toast.makeText(getApplicationContext(), currentPage+" "+totalAvailablePages, Toast.LENGTH_SHORT).show();
                        tvShowsAdapter.notifyItemRangeInserted(oldCount, tvShowList.size());

                    } else {
                        Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }catch(Exception e){
            Toast.makeText(this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void toggleLoading() {
        if (currentPage == 1) {
            if (searchBinding.getIsLoading() != null && searchBinding.getIsLoading()) {
                searchBinding.setIsLoading(false);
            } else {
                searchBinding.setIsLoading(true);
            }
        } else {
            if (searchBinding.getIsLoadingMore() != null && searchBinding.getIsLoadingMore()) {
                searchBinding.setIsLoadingMore(false);
            } else {
                searchBinding.setIsLoadingMore(true);
            }
        }
    }

    @Override
    public void onTVShowClicked(TVShow tvShow) {
        Intent intent = new Intent(this, TVShowDetailsActivity.class);
        intent.putExtra("tvShow", tvShow);
        startActivity(intent);

    }
}