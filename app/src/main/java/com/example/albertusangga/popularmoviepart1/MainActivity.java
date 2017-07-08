package com.example.albertusangga.popularmoviepart1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.albertusangga.popularmoviepart1.utilities.MovieData;
import com.example.albertusangga.popularmoviepart1.utilities.MoviesJsonUtils;
import com.example.albertusangga.popularmoviepart1.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private Spinner mSpinner;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);
        mRecyclerView.setHasFixedSize(true);

        int numberOfColumns = 0;
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            numberOfColumns = getResources().getInteger(R.integer.number_of_columns_portrait);
        } else {
            numberOfColumns = getResources().getInteger(R.integer.number_of_columns_landscape);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMoviesAdapter = new MoviesAdapter(this, this);
        mRecyclerView.setAdapter(mMoviesAdapter);

        mErrorMessageDisplay = (TextView) findViewById(R.id.error_message);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMoviesData(NetworkUtils.POPULAR_PARAM);
    }

    private void loadMoviesData(String filter) {
        new FetchMoviesTask().execute(filter);
        showMoviesDataView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem item = menu.findItem(R.id.movies_filters_spinner);
        Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.movies_filters_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                if(pos == 0) loadMoviesData(NetworkUtils.POPULAR_PARAM);
                else if(pos == 1) loadMoviesData(NetworkUtils.TOP_RATED_PARAM);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return true;
    }

    @Override
    public void onClick(MovieData movieData) {
        final String MOVIE_DATA = "movie_data";
        Context context = MainActivity.this;
        Class destinationActivity = MovieDetailActivity.class;
        Intent intent = new Intent(context, destinationActivity);
        intent.putExtra(MOVIE_DATA, movieData);
        startActivity(intent);
    }

    private void showMoviesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public void showErrorMessage(String errorMessage) {
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mErrorMessageDisplay.setText(errorMessage);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, MovieData[]> {
        public boolean isOnline() {
            ConnectivityManager cm =
                    (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieData[] doInBackground(String... params) {
            if(params.length == 0) return null;
                String filter = params[0];
                URL moviesRequestUrl = NetworkUtils.buildMoviesUrl(filter);
                Log.v(MainActivity.class.getSimpleName(), moviesRequestUrl.toString());
                try {
                    String jsonMoviesResponse = NetworkUtils.getResponseFromHttpUrl(moviesRequestUrl);
                    return MoviesJsonUtils.getParsedMovieDatas(MainActivity.this, jsonMoviesResponse);
                } catch(Exception e) {
                    e.printStackTrace();
                    return null;
                }
        }

        @Override
        protected void onPostExecute(MovieData[] movieDatas) {
            if(movieDatas != null) {
                try {
                    showMoviesDataView();
                    mMoviesAdapter.setMovieDatas(movieDatas);
                    mLoadingIndicator.setVisibility(View.INVISIBLE);
                } catch(Exception e) {
                    e.printStackTrace();
                    showErrorMessage("Unable to display movie datas. Please try again.");
                }
            } else {
                showErrorMessage("Unable to display movie datas. Please try again.");
            }
        }
    }
}
