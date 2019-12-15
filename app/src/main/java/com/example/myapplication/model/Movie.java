package com.example.myapplication.model;

import android.graphics.Bitmap;
import android.media.Image;

import com.example.myapplication.presenter.Config;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Comparable {

    public static List<Movie> MOVIES = new ArrayList<>();

    public static List<Movie> ACAO = new ArrayList<>();
    public static List<Movie> DRAMA = new ArrayList<>();
    public static List<Movie> FICCAO = new ArrayList<>();
    public static List<Movie> FANTASIA = new ArrayList<>();


    private String id;
    private String title;
    private String image;
    private String genero;
    private String descricao;

    private Bitmap picture;

    public Movie(String title) {
        this.title = title;
    }

    public Movie(String id, String title, String image, String overview) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.descricao = overview;
    }

    public String getDescricao() {
        return descricao;
    }

    public static List<Movie> getACAO() {
        return ACAO;
    }

    public static List<Movie> getDRAMA() {
        return DRAMA;
    }

    public static List<Movie> getFICCAO() {
        return FICCAO;
    }

    public static List<Movie> getFANTASIA() {
        return FANTASIA;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public static void setACAO(List<Movie> ACAO) {
        Movie.ACAO = ACAO;
    }

    public static void setDRAMA(List<Movie> DRAMA) {
        Movie.DRAMA = DRAMA;
    }

    public static void setFICCAO(List<Movie> FICCAO) {
        Movie.FICCAO = FICCAO;
    }

    public static void setFANTASIA(List<Movie> FANTASIA) {
        Movie.FANTASIA = FANTASIA;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public static List<Movie> getMovies(int type) {

        List<Movie> aux = new ArrayList<>();

        switch (type) {
            case Config.ACAO:
                aux = Movie.getACAO();
                break;
            case Config.DRAMA:
                aux = Movie.getDRAMA();
                break;
            case Config.FANTASIA:
                aux = Movie.getFANTASIA();
                break;
            case Config.FICCAO:
                aux = Movie.getFICCAO();
                break;
        }

        return aux;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public int compareTo(Object o) {
        String compareage = ((Movie) o).getTitle();
        return this.getTitle().compareTo(compareage);
    }
}


