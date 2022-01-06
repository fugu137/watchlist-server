package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rating {

    private String source;
    private String rating;

    public Rating(String source, String rating) {
        this.source = source;
        this.rating = rating;
    }

    public String getSource() {
        return source;
    }

    @JsonProperty("Source")
    public void setSource(String source) {
        this.source = source;
    }

    public String getRating() {
        return rating;
    }

    @JsonProperty("Value")
    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "{ source: " + source + ", rating: " + rating + " }";
    }
}

