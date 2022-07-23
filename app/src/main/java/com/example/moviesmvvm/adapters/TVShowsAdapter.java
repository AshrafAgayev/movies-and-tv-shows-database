package com.example.moviesmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.databinding.ItemContainerBinding;
import com.example.moviesmvvm.listeners.TVShowsListener;
import com.example.moviesmvvm.models.TVShow;

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TvShowViewHolder>{

    private List<TVShow> tvshows;
    private LayoutInflater layoutInflater;
    private TVShowsListener tvShowsListener;


    public TVShowsAdapter(List<TVShow> tvshows, TVShowsListener tvShowsListener) {
        this.tvshows = tvshows;
        this.tvShowsListener = tvShowsListener;

    }

    @NonNull
    @Override
    public TvShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater == null){
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
        return  tvshows.size();
    }


    class TvShowViewHolder extends RecyclerView.ViewHolder{

        private ItemContainerBinding itemContainerTvShowBinding;

        public TvShowViewHolder(ItemContainerBinding itemContainerTvShowBinding){
            super(itemContainerTvShowBinding.getRoot());
            this.itemContainerTvShowBinding = itemContainerTvShowBinding;
        }

        public void bindTVShow(TVShow tvshow){
            itemContainerTvShowBinding.setTvShow(tvshow);
            itemContainerTvShowBinding.executePendingBindings();
            itemContainerTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvShowsListener.onTVShowClicked(tvshow);
                }
            });
        }
    }
}
