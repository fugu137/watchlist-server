package net.mjduncan.watchlist.server.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OMDBServiceTest {

    @InjectMocks
    private OMDBService omdbService;

    @Mock
    private RestTemplate restTemplate;

    private final String titleSearchPrefix = "&s=";
    private final String idSearchPrefix = "&i=";


    @Test
    void shouldImportMoviesIfApiCallSuccessful() throws JsonProcessingException {
        String omdbBaseUrl = "http://www.omdbapi.com";
        String omdbApiKey = "12345";
        ReflectionTestUtils.setField(omdbService, "omdbBaseUrl", omdbBaseUrl);
        ReflectionTestUtils.setField(omdbService, "omdbApiKey", omdbApiKey);

        String movieTitle = "Dog";
        List<Movie> movies = List.of(new Movie("1", "Dog Day Afternoon"), new Movie("2", "Donnie Darko"));
        SearchResults searchResults = new SearchResults();
        searchResults.setMovies(movies);

        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + titleSearchPrefix + movieTitle;
        when(restTemplate.getForEntity(url, SearchResults.class)).thenReturn(ResponseEntity.ok(searchResults));

        List<Movie> results = omdbService.searchMoviesByTitle(movieTitle).getBody().getMovies();

        assertThat(results, is(movies));
        verify(restTemplate).getForEntity(url, SearchResults.class);
    }

    @Test
    void shouldNotImportMoviesIfApiCallUnsuccessful() throws JsonProcessingException {
        String wrongBaseUrl = "http://wrongUrl.com";
        String wrongApiKey = "11111";
        ReflectionTestUtils.setField(omdbService, "omdbBaseUrl", wrongBaseUrl);
        ReflectionTestUtils.setField(omdbService, "omdbApiKey", wrongApiKey);

        String movieTitle = "Dog";
        String url = wrongBaseUrl + "/?apikey=" + wrongApiKey + "&type=movie" + titleSearchPrefix + movieTitle;
        when(restTemplate.getForEntity(url, SearchResults.class)).thenReturn(ResponseEntity.badRequest().build());

        ResponseEntity<SearchResults> results = omdbService.searchMoviesByTitle(movieTitle);

        assertNull(results.getBody());
        verify(restTemplate).getForEntity(url, SearchResults.class);
    }
}
