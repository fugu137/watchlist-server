package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.SearchResults;
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

    @Value("S{omdb.title.search.prefix}")
    private String titleSearchPrefix;

    @Value("${omdb.id.search.prefix}")
    private String idSearchPrefix;


    public ResponseEntity<SearchResults> searchMoviesByTitle(String movieTitle) {
        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + titleSearchPrefix + movieTitle;

        return restTemplate.getForEntity(url, SearchResults.class);
    }
}
