package net.mjduncan.watchlist.server.service;

import net.mjduncan.watchlist.server.model.Movie;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
