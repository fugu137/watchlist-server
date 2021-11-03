package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.model.SearchResults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class SearchServiceTest {

    @InjectMocks
    private SearchService searchService;

    @Mock
    private RestTemplate restTemplate;


    @Test
    void shouldImportMoviesIfApiCallSuccessful() {
        String omdbBaseUrl = "http://www.omdbapi.com";
        String omdbApiKey = "12345";
        ReflectionTestUtils.setField(searchService, "omdbBaseUrl", omdbBaseUrl);
        ReflectionTestUtils.setField(searchService, "omdbApiKey", omdbApiKey);

        List<Movie> movies = List.of(new Movie("Dog Day Afternoon"), new Movie("Sharknado"));
        SearchResults searchResults = new SearchResults(movies);

        String url = omdbBaseUrl + "/?apikey=" + omdbApiKey + "&type=movie";
        when(restTemplate.getForObject(url, SearchResults.class))
                .thenReturn(searchResults);

        List<Movie> results = searchService.searchMovies("searchTerm");

        assertThat(results, is(movies));
        verify(restTemplate).getForObject(url, SearchResults.class);
    }

    @Test
    void shouldNotImportMoviesIfApiCallUnsuccessful() {
        String wrongBaseUrl = "http://wrongUrl.com";
        String wrongApiKey = "11111";
        ReflectionTestUtils.setField(searchService, "omdbBaseUrl", wrongBaseUrl);
        ReflectionTestUtils.setField(searchService, "omdbApiKey", wrongApiKey);

        String url = wrongBaseUrl + "/?apikey=" + wrongApiKey + "&type=movie";
        when(restTemplate.getForObject(url, SearchResults.class))
                .thenReturn(null);

        List<Movie> results = searchService.searchMovies("searchTerm");

        assertNull(results);
        verify(restTemplate).getForObject(url, SearchResults.class);
    }
}
