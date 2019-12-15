package com.example.myapplication.view;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.model.Item;
import com.example.myapplication.model.Movie;
import com.example.myapplication.model.Result;
import com.example.myapplication.presenter.RetrofitHttp;

import java.io.IOException;
import java.util.Objects;

public class ItemActivity extends AppCompatActivity {

    public static Movie movie;

    private static EditText descricao;
    private static ImageView viewback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        TextView textView = findViewById(R.id.title);
        textView.setText(movie.getTitle());

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(movie.getPicture());

        descricao = findViewById(R.id.descricao);
        descricao.setEnabled(false);

        if (movie.getDescricao().isEmpty()) {
            new Descricao().execute(movie.getId());
        } else {
            descricao.setText(movie.getDescricao());
        }
    }
    
    public void onBackPressed(View v) {
        super.onBackPressed();
        finish();
    }

    class Descricao extends AsyncTask<String, String, String> {

        private static final String URI_MOVIE = "https://api.themoviedb.org/3/movie/";

        @Override
        protected String doInBackground(String... strings) {

            Retrofit retrofit = new Retrofit.Builder().baseUrl(URI_MOVIE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitHttp service = retrofit.create(RetrofitHttp.class);

            Call<Item> movies = service.getOverView(strings[0]);

            String retorno = null;
            try {
                retorno = movies.execute().body().getOverview();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return retorno;
        }

        @Override
        protected void onPostExecute(String s) {

            if (s.isEmpty()) {
                descricao.setText("Não há descrição");
            } else {
                descricao.setText(s);
            }
        }
    }
}
