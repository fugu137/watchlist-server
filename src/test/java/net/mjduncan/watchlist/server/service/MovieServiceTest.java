package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.controller.dto.AddMovieRequest;
import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @InjectMocks
    private MovieService movieService;

    @Mock
    private MovieMapper movieMapper;


    @Test
    void shouldFindAllMovies() {
        List<Movie> movies = List.of(new Movie("Gladiator"), new Movie("Drive"), new Movie("Thin Red Line"));

        when(movieMapper.findAll()).thenReturn(movies);
        List<Movie> results = movieService.getAllMovies();

        verify(movieMapper, times(1)).findAll();
        assertThat(results, is(movies));
    }

    @Test
    void shouldAddMovieByUserId() {
        AddMovieRequest addMovieRequest = new AddMovieRequest("Drive");
        Movie movie = addMovieRequest.toMovie();
        Long userId = 333L;
        Long movieId = 5L;

        doAnswer(invocation -> {
            Movie argument = invocation.getArgument(0);
            argument.setId(movieId);
            movie.setId(movieId);
            return null;
        }).when(movieMapper).insertMovie(movie);

        movieService.addMovieById(userId, addMovieRequest);

        verify(movieMapper, times(1)).insertMovie(movie);
        verify(movieMapper, times(1)).insertMovieByUserId(userId, movieId);
    }

    @Test
    void shouldGetMoviesById() {
        List<Movie> movies = List.of(new Movie("Drive"), new Movie("True Grit"));
        Long userId = 10L;

        when(movieMapper.findAllById(userId)).thenReturn(movies);
        List<Movie> results = movieService.getMoviesById(userId);

        assertThat(results, is(movies));
        verify(movieMapper).findAllById(userId);
    }

}
