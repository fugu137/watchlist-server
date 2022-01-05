package net.mjduncan.watchlist.server.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import net.mjduncan.watchlist.server.model.MovieSearchResult;

import java.util.List;


public class SearchResults {

    @JsonProperty("Search")
    private List<MovieSearchResult> searchResults;


    public List<MovieSearchResult> getSearchResults() {
        return searchResults;
    }

    public void setSearchResults(List<MovieSearchResult> searchResults) {
        this.searchResults = searchResults;
    }

    @Override
    public String toString() {
        return searchResults.toString();
    }
}
