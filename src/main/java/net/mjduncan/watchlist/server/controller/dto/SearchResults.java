package net.mjduncan.watchlist.server.controller.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import net.mjduncan.watchlist.server.model.Movie;

import java.util.List;


public class SearchResults {

    @JsonProperty("Search")
    private List<Movie> movies;


    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return movies.toString();
    }
}
