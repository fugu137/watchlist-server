package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie {

    private String imdbID;
    private String title;
    private int year;

    public Movie() {}

    public Movie(String imdbID) {
        this.imdbID = imdbID;
    }

    public Movie(String imdbID, String title) {
        this.imdbID = imdbID;
        this.title = title;
    }

    public Movie(String imdbID, String title, int year) {
        this.imdbID = imdbID;
        this.title = title;
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("Title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("year")
    public int getYear() {
        return year;
    }

    @JsonProperty("Year")
    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return imdbID.equals(movie.imdbID) && Objects.equals(title, movie.title) && year == movie.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(imdbID, title, year);
    }

    @Override
    public String toString() {
        return String.format("imdbID: %s, title: %s, year: %s", this.imdbID, this.title, this.year);
    }
}
