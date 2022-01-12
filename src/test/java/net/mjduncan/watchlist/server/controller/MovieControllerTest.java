package net.mjduncan.watchlist.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.mjduncan.watchlist.server.configuration.UserAccountDetailsService;
import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Account;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.service.AccountService;
import net.mjduncan.watchlist.server.service.MovieService;
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
    private OmdbService omdbService;

    @MockBean
    private AccountService accountService;

    @MockBean
    private UserAccountDetailsService userAccountDetailsService;


    @Test
    @WithMockUser
    void shouldReturnAllMoviesIfUserIsAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("1", "The Godfather"), new Movie("2", "Adventureland"), new Movie("3", "Drive"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies/all"))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesAsJson));

        verify(movieService, times(1)).getAllMovies();
    }

    @Test
    void shouldNotReturnAllMoviesIfUserIsNotAuthenticated() throws Exception {
        List<Movie> movies = List.of(new Movie("1", "The Godfather"), new Movie("2", "Adventureland"), new Movie("3", "Drive"));

        when(movieService.getAllMovies()).thenReturn(movies);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }

    @Test
    @WithMockUser(username = "Jacqueline")
    void shouldAddMovie() throws Exception {
        String username = "Jacqueline";
        Long userId = 233L;

        Account account = new Account(username, "password");
        account.setId(userId);

        String movieId = "tt1091722";
        AddMovieRequest addMovieRequest = new AddMovieRequest(movieId);
        Movie movie = new Movie(addMovieRequest.getImdbID());

        String jsonRequest = new ObjectMapper().writeValueAsString(addMovieRequest);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));
        when(movieService.getUserMovieByImdbID(userId, movieId)).thenReturn(Optional.empty());
        when(omdbService.searchMoviesByID(movieId)).thenReturn(ResponseEntity.ok(movie));

        mockMvc.perform(post("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());

        verify(movieService, times(1)).addUserMovie(userId, movie);
    }

    @Test
    @WithMockUser(username = "Jacqueline")
    void shouldNotAddMovieIfUserAccountDoesNotExist() throws Exception {
        String username = "Jacqueline";
        String movieId = "tt1091722";

        AddMovieRequest addMovieRequest = new AddMovieRequest(movieId);
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

        List<Movie> movies = List.of(new Movie("1", "Drive"), new Movie("2", "Hot Fuzz"));
        String moviesAsJson = new ObjectMapper().writeValueAsString(movies);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));
        when(movieService.getUserMovies(id)).thenReturn(movies);

        mockMvc.perform(get("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(moviesAsJson));

        verify(movieService, times(1)).getUserMovies(id);
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

        verify(movieService, times(0)).getUserMovies(id);
        verify(accountService, times(1)).getAccountByUsername(username);
    }

    @Test
    @WithMockUser(username = "Michael")
    void shouldDeleteMoviesByUser() throws Exception {
        String username = "Michael";
        Long id = 133L;
        String imdbID = "jb12345";

        Account account = new Account(username, "password");
        account.setId(id);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));

        mockMvc.perform(post("/movies/remove")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imdbID))
                .andExpect(status().isOk());

        verify(accountService, times(1)).getAccountByUsername(username);
        verify(movieService, times(1)).removeUserMovie(id, imdbID);
    }

    @Test
    @WithMockUser(username = "Michael")
    void shouldNotDeleteMoviesByUserIfUserAccountDoesNotExist() throws Exception {
        String username = "Michael";
        Long id = 133L;
        String imdbID = "jb12345";

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/movies")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));

        verify(accountService, times(1)).getAccountByUsername(username);
        verify(movieService, times(0)).removeUserMovie(id, imdbID);
    }

    @Test
    @WithMockUser(username = "Michael")
    void shouldNoteDeleteMoviesByUserIfImdbIDisInvalid() throws Exception {
        String username = "Michael";
        Long id = 133L;
        String imdbID = "";

        Account account = new Account(username, "password");
        account.setId(id);

        when(accountService.getAccountByUsername(username)).thenReturn(Optional.of(account));

        mockMvc.perform(post("/movies/remove")
                        .with(csrf().asHeader())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(imdbID))
                .andExpect(status().isBadRequest());

        verify(accountService, times(0)).getAccountByUsername(username);
        verify(movieService, times(0)).removeUserMovie(id, imdbID);
    }

}
