package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.MovieSearchResult;
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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OmdbServiceTest {

    @InjectMocks
    private OmdbService omdbService;

    @Mock
    private RestTemplate restTemplate;

    private final String omdbBaseUrl = "http://www.omdbapi.com";
    private final String omdbApiKey = "12345";

    private final String titleSearchPrefix = "&s=";
    private final String idSearchPrefix = "&i=";
    private final String fullSynopsis = "&plot=full";


    @Test
    void shouldFindMoviesByTitle() {
        ReflectionTestUtils.setField(omdbService, "omdbBaseUrl", omdbBaseUrl);
        ReflectionTestUtils.setField(omdbService, "omdbApiKey", omdbApiKey);

        String movieTitle = "Dog";
        List<MovieSearchResult> movies = List.of(new MovieSearchResult("1", "Dog Day Afternoon"), new MovieSearchResult("2", "Donnie Darko"));
        SearchResults searchResults = new SearchResults();
        searchResults.setSearchResults(movies);

        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + titleSearchPrefix + movieTitle;
        when(restTemplate.getForEntity(url, SearchResults.class)).thenReturn(ResponseEntity.ok(searchResults));

        SearchResults results = omdbService.searchMoviesByTitle(movieTitle).getBody();
        assertNotNull(results);

        List<MovieSearchResult> parsedResults = results.getSearchResults();

        assertThat(parsedResults, is(movies));
        verify(restTemplate).getForEntity(url, SearchResults.class);
    }

    @Test
    void shouldNotFindMoviesByTitleIfApiCallUnsuccessful() {
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

    @Test
    void shouldFindMovieByID() {
        String imdbID = "ft10234";

        ReflectionTestUtils.setField(omdbService, "omdbBaseUrl", omdbBaseUrl);
        ReflectionTestUtils.setField(omdbService, "omdbApiKey", omdbApiKey);

        Movie movie = new Movie(imdbID, "New Movie", 1950);

        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie" + idSearchPrefix + imdbID + fullSynopsis;
        when(restTemplate.getForEntity(url, Movie.class)).thenReturn(ResponseEntity.ok(movie));

        Movie result = omdbService.searchMoviesByID(imdbID).getBody();

        assertThat(result, is(movie));
        verify(restTemplate).getForEntity(url, Movie.class);
    }
}
