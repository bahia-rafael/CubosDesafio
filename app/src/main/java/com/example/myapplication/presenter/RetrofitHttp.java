package com.example.myapplication.presenter;

import com.example.myapplication.model.Item;
import com.example.myapplication.model.Result;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitHttp {

    @Headers("Content-Type: aplication/json")
    @GET("discover/movie?api_key=b0c663f722b88a597611437c781e227f&language=pt-BR&sort_by=original_title.asc&include_adult=false&include_video=false")
    Call<Result> listMovies(@Query("page") String pagina, @Query("with_genres") String genero);

    @GET("{movie}?api_key=b0c663f722b88a597611437c781e227f&language=pt-BR")
    Call<Item> getOverView(@Path("movie") String id);

    @GET("search/movie?api_key=b0c663f722b88a597611437c781e227f&language=pt-BR")
    Call<Result> searchMovie(@Query("query") String query);
}
