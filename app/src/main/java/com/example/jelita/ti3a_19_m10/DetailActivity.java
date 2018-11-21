package com.example.jelita.ti3a_19_m10;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jelita.ti3a_19_m10.Models.Movie;

public class DetailActivity extends AppCompatActivity {

    TextView movieTitle;
    TextView data;
    TextView movieDescription;
    TextView rating;
    public static final String EXTRA_MOVIE = "extra_movie";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieTitle = (TextView) findViewById(R.id.title);
        data = (TextView) findViewById(R.id.subtitle);
        movieDescription = (TextView) findViewById(R.id.description);
        rating = (TextView) findViewById(R.id.rating);

        Intent i = getIntent();
        Movie movie = (Movie) i.getSerializableExtra(EXTRA_MOVIE);

        movieTitle.setText(movie.getTitle());
        data.setText(movie.getReleaseDate());
        movieDescription.setText(movie.getOverview());
        rating.setText(movie.getVoteAverage().toString());
    }
}
