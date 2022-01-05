package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieSearchResult {

    private String imdbID;
    private String title;
    private int year;

    public MovieSearchResult() {}

    public MovieSearchResult(String imdbID) {
        this.imdbID = imdbID;
    }

    public MovieSearchResult(String imdbID, String title) {
        this.imdbID = imdbID;
        this.title = title;
    }

    public MovieSearchResult(String imdbID, String title, int year) {
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
        MovieSearchResult that = (MovieSearchResult) o;
        return year == that.year && imdbID.equals(that.imdbID) && Objects.equals(title, that.title);
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
