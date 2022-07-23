package com.example.moviesmvvm.models;

import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("season")
    private int season;

    @SerializedName("episode")
    private int episode;

    @SerializedName("name")
    private String episodeName;

    @SerializedName("air_date")
    private String airDate;

    public int getSeason() {
        return season;
    }

    public int getEpisode() {
        return episode;
    }

    public String getName() {
        return episodeName;
    }

    public String getAirDate() {
        return airDate;
    }
}
