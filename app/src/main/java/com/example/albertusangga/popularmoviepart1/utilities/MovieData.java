package com.example.albertusangga.popularmoviepart1.utilities;

import java.io.Serializable;
import java.net.URL;

/**
 * Created by Albertus Angga on 7/6/2017.
 */

public class MovieData implements Serializable {
    private String title;
    private String releaseDate;
    private String overview;
    private URL posterURL;
    private double voteAverage;

    public MovieData() {
        this.title = "";
        this.releaseDate = "";
        this.overview = "";
        this.posterURL = null;
        this.voteAverage = 0;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public URL getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(URL posterURL) {
        this.posterURL = posterURL;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

}
