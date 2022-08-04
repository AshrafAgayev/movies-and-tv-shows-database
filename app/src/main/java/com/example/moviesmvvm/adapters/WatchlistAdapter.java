package com.example.moviesmvvm.adapters;

import static android.view.View.*;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.databinding.ItemContainerBinding;
import com.example.moviesmvvm.listeners.WatchlistListener;
import com.example.moviesmvvm.models.TVShow;

import java.util.List;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.TvShowViewHolder> {

    private List<TVShow> tvshows;
    private LayoutInflater layoutInflater;
    private WatchlistListener watchlistListener;


    public WatchlistAdapter(List<TVShow> tvshows, WatchlistListener watchlistListener) {
        this.tvshows = tvshows;
        this.watchlistListener = watchlistListener;

    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerBinding tvShowBinding = DataBindingUtil.inflate(
                layoutInflater, R.layout.item_container, parent, false
        );

        return new TvShowViewHolder(tvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowViewHolder holder, int position) {
        holder.bindTVShow(tvshows.get(position));
    }

    @Override
    public int getItemCount() {
        return tvshows.size();
    }


    class TvShowViewHolder extends RecyclerView.ViewHolder {

        private final ItemContainerBinding itemContainerTvShowBinding;

        public TvShowViewHolder(ItemContainerBinding itemContainerTvShowBinding) {
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVShow(TVShow tvshow) {
            itemContainerTvShowBinding.executePendingBindings();

            itemContainerTvShowBinding.setTvShow(tvshow);
            itemContainerTvShowBinding.getRoot().setOnClickListener(view -> watchlistListener.onTVShowClicked(tvshow));
            itemContainerTvShowBinding.imageDeleteWatchlist.setOnClickListener(view -> watchlistListener.removeTVShowFromWatchlist(tvshow, getAdapterPosition()));
            itemContainerTvShowBinding.imageDeleteWatchlist.setVisibility(VISIBLE);
        }
    }
}
