package com.example.myapplication.view;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Result;
import com.example.myapplication.presenter.Config;
import com.example.myapplication.presenter.RetrofitHttp;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static List<Movie> movies = new ArrayList<>();
    private static GridView gridView;
    private static ArrayAdapter adapter;
    private ProgressBar progressBar;
    private SearchView searchView;

    private static String querySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_1));
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_2));
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_3));
        tabs.addTab(tabs.newTab().setText(R.string.tab_text_4));
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        gridView = findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        new RetrofitRequest().execute(Config.ACAO, 1);

        searchView = findViewById(R.id.searchView2);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                querySearch = s;
                movies.clear();
                if(s.isEmpty()) {
                    new RetrofitRequest().execute(Config.getGeneroToTabs(tabs.getSelectedTabPosition()), 1);
                } else {
                    new RetrofitRequest().execute(Config.NOT_GENRE, 0);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(() -> {
            tabs.setVisibility(View.VISIBLE);
            movies.clear();
            new RetrofitRequest().execute(Config.getGeneroToTabs(tabs.getSelectedTabPosition()), 1);
            return false;
        });

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                movies.clear();
                changeGridView();
                List<Movie> aux = Movie.getMovies(Config.getGeneroToTabs(tab.getPosition()));
                if (aux.isEmpty()) {
                    new RetrofitRequest().execute(Config.getGeneroToTabs(tab.getPosition()), 1);
                } else {
                    movies.addAll(aux);
                    changeGridView();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void changeGridView() {

        adapter = new ArrayAdapter(this, R.layout.item_main, R.id.txt, movies) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                ImageView imageView = view.findViewById(R.id.img);
                TextView textView = view.findViewById(R.id.txt);

                textView.setText(movies.get(position).getTitle());
                imageView.setImageBitmap(movies.get(position).getPicture());

                return view;
            }
        };
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Intent intent = new Intent(this, ItemActivity.class);

        ItemActivity.movie = movies.get(position);

        startActivity(intent);
    }

    class RetrofitRequest extends AsyncTask<Integer, Integer, Integer> {

        private Result moviesLista = null;
        private static final String URI_MOVIE = "https://api.themoviedb.org/3/";
        private static final String URI_IMG = "https://image.tmdb.org/t/p/original";

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(Integer... ints) {

            String genero = String.valueOf(ints[0]);

            Retrofit retrofit = new Retrofit.Builder().baseUrl(URI_MOVIE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitHttp service = retrofit.create(RetrofitHttp.class);

            Call<Result> movies = null;

            if(ints[1] == 1) {
                movies = service.listMovies("1", genero);
            } else {
                movies = service.searchMovie(querySearch);
            }

            try {
                Result aux = movies.execute().body();
                moviesLista = aux;
                for (Movie x : aux.getMovies()) {
                    if (x.getImage() != null) {
                        x.setPicture(BitmapFactory.decodeStream((InputStream) new URL(URI_IMG + x.getImage()).getContent()));
                    } else {
                        x.setPicture(BitmapFactory.decodeResource(getResources(), R.drawable.image_not_found));
                    }
                    MainActivity.movies.add(x);
                }
            } catch (IOException | NullPointerException e) {
                Log.i("ErrorMovies", Objects.requireNonNull(e.getMessage()));
            }

            return ints[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            changeGridView();
        }

        @Override
        protected void onPostExecute(Integer s) {
            super.onPostExecute(s);
            changeGridView();
            progressBar.setVisibility(View.INVISIBLE);

            switch (s) {
                case Config.ACAO:
                    Movie.setACAO(movies);
                    break;
                case Config.DRAMA:
                    Movie.setDRAMA(movies);
                    break;
                case Config.FANTASIA:
                    Movie.setFANTASIA(movies);
                    break;
                case Config.FICCAO:
                    Movie.setFICCAO(movies);
                    break;
            }
        }
    }
}
