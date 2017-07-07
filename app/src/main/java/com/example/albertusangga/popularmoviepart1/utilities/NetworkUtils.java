package com.example.albertusangga.popularmoviepart1.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.content.ContentValues.TAG;

/**
 * Created by Albertus Angga on 7/3/2017.
 */

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String API_KEY_PARAM = "api_key";

    // INSERT / CHANGE YOUR API_KEY HERE
    private static final String api_key = "";

    // Popular Movies API URL
    public static final String POPULAR_PARAM = "popular";
    public static final String POPULAR_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/popular";

    // Top Rated Movies API URL
    public static final String TOP_RATED_PARAM = "top_rated";
    public static final String TOP_RATED_MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/top_rated";

    // Movie Poster URL
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
    public static final String DEFAULT_POSTER_SIZE = "w185";

    public static URL buildPosterUrl(String posterLocation) {
        Uri builtUri = Uri.parse(POSTER_BASE_URL).buildUpon()
                .appendPath(DEFAULT_POSTER_SIZE)
                .appendPath(posterLocation)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch(MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * Builds the URL used to get the popular movies from themoviedb
     * @param 'popular' / 'top_rated' (movies filter which change is triggered by Spinner UI)
     * @return The URL to use to query the weather server.
     */
    public static URL buildMoviesUrl(String filter) {
        Uri builtUri = null;
        if(filter.equals(POPULAR_PARAM)) {
            builtUri = Uri.parse(POPULAR_MOVIE_DB_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, api_key).build();
        } else {
            builtUri = Uri.parse(TOP_RATED_MOVIE_DB_URL).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, api_key).build();
        }
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
