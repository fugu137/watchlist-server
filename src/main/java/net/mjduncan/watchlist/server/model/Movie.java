package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends MovieSearchResult {

    public Movie(String imdbID) {
        super(imdbID);
    }

    public Movie(String imdbID, String title) {
        super(imdbID, title);
    }

    public Movie(String imdbID, String title, int year) {
        super(imdbID, title, year);
    }
}
