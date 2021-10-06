package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;


    @Test
    @WithMockUser
    void shouldReturnAllMoviesIfUserIsAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("The Godfather"), new Movie("Adventureland"), new Movie("Drive"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesAsJson));

        verify(movieService).getAllMovies();
    }

    @Test
    void shouldNotReturnAllMoviesIfUserIsNotAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("The Godfather"), new Movie("Adventureland"), new Movie("Drive"));

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }
}
