package net.mjduncan.watchlist.server.model;

import java.util.List;

public class SearchResults {
    private List<Movie> movies;

    public SearchResults(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
