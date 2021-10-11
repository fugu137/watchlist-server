package net.mjduncan.watchlist.server.controller.dto;


import net.mjduncan.watchlist.server.model.Movie;

import java.util.Objects;

public class AddMovieRequest {

    private String name;


    public AddMovieRequest() {}

    public AddMovieRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Movie toMovie() {
        return new Movie(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddMovieRequest that = (AddMovieRequest) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
