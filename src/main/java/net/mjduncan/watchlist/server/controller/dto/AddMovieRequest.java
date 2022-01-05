package net.mjduncan.watchlist.server.controller.dto;


import net.mjduncan.watchlist.server.model.MovieSearchResult;

import java.util.Objects;

public class AddMovieRequest {

    private String imdbID;


    public AddMovieRequest() {}

    public AddMovieRequest(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbId(String imdbID) {
        this.imdbID = imdbID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMovieRequest that = (AddMovieRequest) o;
        return imdbID.equals(that.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID);
    }
}
