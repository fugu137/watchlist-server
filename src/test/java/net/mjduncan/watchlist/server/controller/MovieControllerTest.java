package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.AccountService;
import net.mjduncan.watchlist.server.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;


    @Test
    @WithMockUser
    void shouldReturnAllMoviesIfUserIsAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("The Godfather"), new Movie("Adventureland"), new Movie("Drive"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesAsJson));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void shouldNotReturnAllMoviesIfUserIsNotAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("The Godfather"), new Movie("Adventureland"), new Movie("Drive"));

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Jacqueline")
    void shouldAddMovieById() throws Exception {
        String username = "Jacqueline";
        Long id = 233L;

        Account account = new Account(username, "password");
        account.setId(id);

        String movieName = "Adventureland";
        AddMovieRequest addMovieRequest = new AddMovieRequest(movieName);
        String jsonRequest = new ObjectMapper().writeValueAsString(addMovieRequest);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));

        mockMvc.perform(post("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(movieService, times(1)).addMovieById(id, addMovieRequest);
    }

    @Test
    @WithMockUser(username = "Jacqueline")
    void shouldNotAddMovieByIdIfUserAccountDoesNotExist() throws Exception {
        String username = "Jacqueline";
        String movieName = "Adventureland";

        AddMovieRequest addMovieRequest = new AddMovieRequest(movieName);
        String jsonRequest = new ObjectMapper().writeValueAsString(addMovieRequest);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(post("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Michael")
    void shouldReturnMoviesByUser() throws Exception {
        String username = "Michael";
        Long id = 133L;

        Account account = new Account(username, "password");
        account.setId(id);

        List<Movie> movies = List.of(new Movie("Drive"), new Movie("Hot Fuzz"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));
        when(movieService.getMoviesById(id)).thenReturn(movies);

        mockMvc.perform(get("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
               .andExpect(content().json(moviesAsJson));

        verify(movieService, times(1)).getMoviesById(id);
        verify(accountService, times(1)).getAccountByUsername(username);
    }

    @Test
    @WithMockUser(username = "Michael")
    void shouldNotReturnMoviesByUserIfUserAccountDoesNotExist() throws Exception {
        String username = "Michael";
        Long id = 133L;

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
               .andExpect(content().string(""));

        verify(movieService, times(0)).getMoviesById(id);
        verify(accountService, times(1)).getAccountByUsername(username);
    }

    @Test
    @WithMockUser
    void shouldImportMoviesIfApiCallSuccessful() throws Exception {
        String param = "movieTitle";
        List<Movie> movies = List.of(new Movie("Gone Girl"), new Movie("Team America"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(movieService.importBySearchTerm(param)).thenReturn(movies);

        mockMvc.perform(get("/movies/search")
                        .param("searchTerm", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesAsJson));

        verify(movieService, times(1)).importBySearchTerm(param);
    }

    @Test
    @WithMockUser
    void shouldNotImportMoviesIfApiCallReturnsNull() throws Exception {
        String param = "movieTitle";

        when(movieService.importBySearchTerm(param)).thenReturn(null);

        mockMvc.perform(get("/movies/search")
                        .param("searchTerm", param)
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(movieService, times(1)).importBySearchTerm(param);
    }

}
