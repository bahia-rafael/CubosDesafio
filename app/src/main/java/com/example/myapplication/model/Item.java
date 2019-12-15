package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("overview")
    private String overview;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
}
