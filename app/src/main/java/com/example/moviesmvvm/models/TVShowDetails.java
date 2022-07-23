package com.example.moviesmvvm.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowDetails {

    @SerializedName("url")
    private String tvShowName;

    @SerializedName("description")
    private String description;

    @SerializedName("runtime")
    private String runtime;

    @SerializedName("image_path")
    private String imagePath;

    @SerializedName("genres")
    private String[] genres;

    @SerializedName("pictures")
    private String[] pictures;

    @SerializedName("rating")
    private String rating;

    @SerializedName("episodes")
    private List<Episode> episodes;


    public String getTvShowName() {
        return tvShowName;
    }

    public String getDescription() {
        return description;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String[] getGenres() {
        return genres;
    }

    public String[] getPictures() {
        return pictures;
    }

    public String getRating() {
        return rating;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }
}
