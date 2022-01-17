package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.MovieSearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


/**
 *  This class allows access to the OMDB movie database api at https://www.omdbapi.com
 */
@Service
public class OmdbService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${omdb.base.url}")
    private String omdbBaseUrl;

    @Value("${omdb.api.key}")
    private String omdbApiKey;

    private final String titleSearchPrefix = "&s=";
    private final String idSearchPrefix = "&i=";
    private final String fullSynopsis = "&plot=full";


    public ResponseEntity<SearchResults> searchMoviesByTitle(String movieTitle) {
        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + titleSearchPrefix + movieTitle;
        return restTemplate.getForEntity(url, SearchResults.class);
    }

    public ResponseEntity<Movie> searchMoviesByID(String imdbID) {
        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + idSearchPrefix + imdbID + fullSynopsis;
        return restTemplate.getForEntity(url, Movie.class);
    }
}
