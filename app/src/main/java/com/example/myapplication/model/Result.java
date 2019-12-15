package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("total_pages")
    private String total_pages;

    @SerializedName("results")
    List<Movies> movies;

    public List<Movie> getMovies() {
        List<Movie> retorno = new ArrayList<>();

        for(Movies aux: movies) {
            if(aux.poster_path == null) {
                retorno.add(new Movie(aux.id, aux.title, aux.backdrop_path, aux.overview));
            } else {
                retorno.add(new Movie(aux.id, aux.title, aux.poster_path, aux.overview));
            }
        }

        return retorno;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }

    public String getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(String total_pages) {
        this.total_pages = total_pages;
    }

    public class Movies {

        @SerializedName("title")
        public String title;

        @SerializedName("id")
        public String id;

        @SerializedName("poster_path")
        public String poster_path;

        @SerializedName("backdrop_path")
        public String backdrop_path;

        @SerializedName("overview")
        public String overview;

        @SerializedName("genre_ids")
        public List<String> genre_ids;
    }
}
