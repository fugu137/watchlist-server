package net.mjduncan.watchlist.server.controller.dto;


import net.mjduncan.watchlist.server.model.Movie;

import java.util.Objects;

public class AddMovieRequest {

    private String imdbId;
    private String title;


    public AddMovieRequest() {}

    public AddMovieRequest(String imdbId, String title) {
        this.imdbId = imdbId;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Movie toMovie() {
        return new Movie(imdbId, title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMovieRequest that = (AddMovieRequest) o;
        return imdbId.equals(that.imdbId) && Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbId, title);
    }
}
