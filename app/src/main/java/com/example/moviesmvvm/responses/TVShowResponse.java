package com.example.moviesmvvm.responses;

import com.example.moviesmvvm.models.TVShow;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowResponse {
    @SerializedName("total")
    private int total;

    @SerializedName("page")
    private int page;

    @SerializedName("pages")
    private int pages;

    @SerializedName("tv_shows")
    private List<TVShow> tvShows;


    public int getTotal() {
        return total;
    }

    public int getPage() {
        return page;
    }

    public int getPages() {
        return pages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }
}
