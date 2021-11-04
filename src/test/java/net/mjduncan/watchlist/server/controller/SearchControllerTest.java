package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(SearchController.class)
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SearchService searchService;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;


    @Test
    @WithMockUser
    void shouldImportMoviesIfApiCallSuccessful() throws Exception {
        String param = "movieTitle";
        List<Movie> movies = List.of(new Movie("1", "Gone Girl"), new Movie("2", "Team America"));

        SearchResults searchResults = new SearchResults();
        searchResults.setMovies(movies);
        String resultsAsJson = new ObjectMapper().writeValueAsString(searchResults.getMovies());

        when(searchService.searchMoviesByTitle(param)).thenReturn(ResponseEntity.ok(searchResults));

        mockMvc.perform(get("/search")
                        .param("movieTitle", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultsAsJson));

        verify(searchService, times(1)).searchMoviesByTitle(param);
    }

    @Test
    @WithMockUser
    void shouldNotImportMoviesIfApiCallReturnsError() throws Exception {
        String param = "movieTitle";

        when(searchService.searchMoviesByTitle(param)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/search")
                        .param("movieTitle", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(searchService, times(1)).searchMoviesByTitle(param);
    }

}
