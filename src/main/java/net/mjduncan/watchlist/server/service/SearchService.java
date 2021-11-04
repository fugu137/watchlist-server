package net.mjduncan.watchlist.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${omdb.base.url}")
    private String omdbBaseUrl;

    @Value("${omdb.api.key}")
    private String omdbApiKey;

    private final String titleSearchPrefix = "&s=";
    private final String idSearchPrefix = "&i=";


    public ResponseEntity<SearchResults> searchMoviesByTitle(String movieTitle) {
        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + titleSearchPrefix + movieTitle;

        return restTemplate.getForEntity(url, SearchResults.class);
    }
}
