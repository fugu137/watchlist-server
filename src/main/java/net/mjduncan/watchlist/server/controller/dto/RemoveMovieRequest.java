package net.mjduncan.watchlist.server.controller.dto;

import java.util.Objects;

public class RemoveMovieRequest {

    private String imdbID;

    public RemoveMovieRequest() {}

    public RemoveMovieRequest(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemoveMovieRequest that = (RemoveMovieRequest) o;
        return Objects.equals(imdbID, that.imdbID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID);
    }
}
