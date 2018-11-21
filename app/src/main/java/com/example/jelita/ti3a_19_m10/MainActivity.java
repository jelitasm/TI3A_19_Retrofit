package com.example.jelita.ti3a_19_m10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.jelita.ti3a_19_m10.Adapters.ClickListener;
import com.example.jelita.ti3a_19_m10.Adapters.MoviesAdapter;
import com.example.jelita.ti3a_19_m10.Adapters.RecyclerTouchListener;
import com.example.jelita.ti3a_19_m10.Models.Movie;
import com.example.jelita.ti3a_19_m10.Models.MovieResponse;
import com.example.jelita.ti3a_19_m10.Rest.ApiClient;
import com.example.jelita.ti3a_19_m10.Rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = "51f9dddc116bca6a16250b1a0640d8f2";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (API_KEY.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your API KEY first from themoviedb.org", Toast.LENGTH_LONG).show();
            return;

        }
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                int statusCode = response.code();
                movies = response.body().getResults();
                recyclerView.setAdapter(new MoviesAdapter(movies, R.layout.list_item_movie, getApplicationContext()));
                recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
                    @Override
                    public void onClick(View view, final int position) {
                        Intent i = new Intent(getApplicationContext(), DetailActivity.class);
                        i.putExtra(DetailActivity.EXTRA_MOVIE,movies.get(position));
                        startActivity(i);
                        Toast.makeText(MainActivity.this, "Single Click on position        :"+position,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClick(View view, int position) {
                        Toast.makeText(MainActivity.this, "Long press on position :"+position,
                                Toast.LENGTH_LONG).show();
                    }
                }));
            }
            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
// Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }
}
