package com.example.moviesmvvm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviesmvvm.R;
import com.example.moviesmvvm.databinding.ItemContainerEpisodeBinding;
import com.example.moviesmvvm.models.Episode;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class EpisodesAdapter extends RecyclerView.Adapter<EpisodesAdapter.EpisodeViewHolder>{

    private final List<Episode> episodeList;
    private LayoutInflater layoutInflater;
    private BottomSheetDialog bottomSheetDialog;

    public EpisodesAdapter(List<Episode> episodes){
        this.episodeList = episodes;
    }

    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(layoutInflater ==null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }

        ItemContainerEpisodeBinding itemContainerEpisodeBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_episode,
                parent,
                false);

        return new EpisodeViewHolder(itemContainerEpisodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindEpisode(episodeList.get(position));
    }

    @Override
    public int getItemCount() {
        return episodeList.size();
    }

    static class EpisodeViewHolder extends RecyclerView.ViewHolder{

        ItemContainerEpisodeBinding itemContainerEpisodeBinding;

        public EpisodeViewHolder( ItemContainerEpisodeBinding itemContainerEpisodeBinding) {
            super(itemContainerEpisodeBinding.getRoot());
            this.itemContainerEpisodeBinding = itemContainerEpisodeBinding;
        }

        public void bindEpisode(Episode episode){
            String episodenum = episode.getEpisode();
            if(episodenum.length() == 1){
                episodenum = "0".concat(episodenum);
            }

            String season = episode.getSeason();
            if(season.length() == 1){
                season = "0".concat(season);
            }

            itemContainerEpisodeBinding.setName(episode.getName());
            itemContainerEpisodeBinding.setEpisode(episodenum);
            itemContainerEpisodeBinding.setSeason(season);
            itemContainerEpisodeBinding.setAirDate(episode.getAirDate());

        }
    }
}
