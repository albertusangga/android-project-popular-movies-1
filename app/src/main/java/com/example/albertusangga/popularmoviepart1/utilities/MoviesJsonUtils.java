package com.example.albertusangga.popularmoviepart1.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by Albertus Angga on 7/6/2017.
 */

public class MoviesJsonUtils {
    public static MovieData[] getParsedMovieDatas(Context context, String moviesJsonStr)
            throws JSONException {

        final String RESULTS = "results";
        final String POSTER = "poster_path";
        final String TITLE = "title";
        final String RELEASE_DATE = "release_date";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";

        /* String array to hold each day's weather String */
        MovieData[] parsedMovieDatas = null;

        JSONObject moviesJson = new JSONObject(moviesJsonStr);

        /* Is there an error? */

        JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);
        parsedMovieDatas = new MovieData[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            parsedMovieDatas[i] = new MovieData();
            JSONObject movie = moviesArray.getJSONObject(i);

            String title = movie.getString(TITLE);
            String releaseDate = movie.getString(RELEASE_DATE);
            String overview = movie.getString(OVERVIEW);
            Double voteAverage = movie.getDouble(VOTE_AVERAGE);

            String posterLocation = movie.getString(POSTER).substring(1); // substring(1) to omit the slash in the url
            URL posterURL = NetworkUtils.buildPosterUrl(posterLocation);

            parsedMovieDatas[i].setTitle(title);
            parsedMovieDatas[i].setReleaseDate(releaseDate);
            parsedMovieDatas[i].setVoteAverage(voteAverage);
            parsedMovieDatas[i].setPosterURL(posterURL);
            parsedMovieDatas[i].setOverview(overview);
        }

        return parsedMovieDatas;
    }
}
