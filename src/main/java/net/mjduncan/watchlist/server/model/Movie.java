package net.mjduncan.watchlist.server.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Movie extends MovieSearchResult {

    private String imdbRating;
    private String tomatoesRating;
    private String metacriticRating;
    private String posterURL;

    public Movie() {
    }

    public Movie(String imdbID) {
        super(imdbID);
    }

    public Movie(String imdbID, String title) {
        super(imdbID, title);
    }

    public Movie(String imdbID, String title, int year) {
        super(imdbID, title, year);
    }

    @JsonProperty("Ratings")
    public void setRatings(List<Rating> ratings) {
        ratings.forEach(rating -> {
            switch (rating.getSource()) {
                case "Internet Movie Database":
                    // not needed because imdbRating exists elsewhere
                    break;
                case "Rotten Tomatoes":
                    tomatoesRating = rating.getRating();
                    break;
                case "Metacritic":
                    metacriticRating = rating.getRating();
                    break;
            }
        });
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public void setTomatoesRating(String rating) {
        this.tomatoesRating = rating;
    }

    public String getTomatoesRating() {
        return tomatoesRating;
    }

    public String getMetacriticRating() {
        return metacriticRating;
    }

    public void setMetacriticRating(String metacriticRating) {
        this.metacriticRating = metacriticRating;
    }

    @JsonProperty("posterURL")
    public String getPosterURL() {
        return posterURL;
    }

    @JsonProperty("Poster")
    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    @Override
    public String toString() {
        return super.toString()
                + ", imdbRating: " + imdbRating
                + ", tomatoesRating: " + tomatoesRating
                + ", metacriticRating: " + metacriticRating
                + ", posterURL: " + posterURL;
    }
}
