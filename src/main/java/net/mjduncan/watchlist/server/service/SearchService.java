package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.SearchResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${omdb.base.url}")
    private String omdbBaseUrl;

    @Value("${omdb.api.key")
    private String omdbApiKey;


    public List<Movie> searchMovies(String searchTerm) {
        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie";

        SearchResults results = restTemplate.getForObject(url, SearchResults.class);
        if (results != null) {
            return results.getMovies();
        }
        return null;
    }
}
