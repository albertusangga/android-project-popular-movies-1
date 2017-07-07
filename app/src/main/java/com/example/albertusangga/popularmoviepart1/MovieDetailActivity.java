package com.example.albertusangga.popularmoviepart1;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertusangga.popularmoviepart1.utilities.MovieData;
import com.example.albertusangga.popularmoviepart1.utilities.MoviesJsonUtils;
import com.example.albertusangga.popularmoviepart1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mPosterImageView;
    private ProgressBar mLoadingIndicator;
    private TextView mMovieTitleTextView;
    private TextView mMovieReleaseDateTextView;
    private RatingBar mMovieAverageVoteTextView;
    private TextView mMovieOverviewTextView;

    private static final String MOVIE_DATA = "movie_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_movie_detail_loading_indicator);
        mLoadingIndicator.setVisibility(View.VISIBLE);

        mMovieOverviewTextView = (TextView) findViewById(R.id.tv_movie_detail_overview);
        mPosterImageView = (ImageView) findViewById(R.id.iv_movie_detail);
        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_detail_title);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_movie_detail_release_date);
        mMovieAverageVoteTextView = (RatingBar) findViewById(R.id.rb_movie_detail_average_vote);


        Intent parentIntent = getIntent();

        if(parentIntent.hasExtra(MOVIE_DATA)) {
            MovieData movieData = (MovieData) parentIntent.getSerializableExtra("movie_data");
            Picasso.with(this)
                    .load(movieData.getPosterURL().toString())
                    .into(mPosterImageView);
            mMovieTitleTextView.setText(movieData.getTitle());
            mMovieReleaseDateTextView.setText(movieData.getReleaseDate());
            mMovieAverageVoteTextView.setRating((float)movieData.getVoteAverage());
            mMovieOverviewTextView.setText(movieData.getOverview());
        }

        mLoadingIndicator.setVisibility(View.INVISIBLE);
    }

}
