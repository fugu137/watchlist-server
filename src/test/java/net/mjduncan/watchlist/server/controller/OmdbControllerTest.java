package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.dto.SearchResults;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.OmdbService;
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


@WebMvcTest(OmdbController.class)
public class OmdbControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OmdbService omdbService;

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

        when(omdbService.searchMoviesByTitle(param)).thenReturn(ResponseEntity.ok(searchResults));

        mockMvc.perform(get("/omdb")
                        .param("movieTitle", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(resultsAsJson));

        verify(omdbService, times(1)).searchMoviesByTitle(param);
    }

    @Test
    @WithMockUser
    void shouldNotImportMoviesIfApiCallReturnsError() throws Exception {
        String param = "movieTitle";

        when(omdbService.searchMoviesByTitle(param)).thenReturn(ResponseEntity.notFound().build());

        mockMvc.perform(get("/omdb")
                        .param("movieTitle", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(omdbService, times(1)).searchMoviesByTitle(param);
    }

}
