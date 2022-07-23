package com.example.moviesmvvm.responses;


import com.example.moviesmvvm.models.TVShowDetails;
import com.google.gson.annotations.SerializedName;

public class TVShowDetailsResponse {

    @SerializedName("tvShow")
    private TVShowDetails tvShow;

    public TVShowDetails getTvShow() {
        return tvShow;
    }
}
