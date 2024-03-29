package net.mjduncan.watchlist.server;

import net.mjduncan.watchlist.server.controller.AccountController;
import net.mjduncan.watchlist.server.controller.MovieController;
import net.mjduncan.watchlist.server.controller.OmdbController;
import net.mjduncan.watchlist.server.repository.AccountMapper;
import net.mjduncan.watchlist.server.repository.MovieMapper;
import net.mjduncan.watchlist.server.service.AccountService;
import net.mjduncan.watchlist.server.service.MovieService;
import net.mjduncan.watchlist.server.service.OmdbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@SpringBootTest
class ApplicationIT {

    @Autowired
    private AccountController accountController;

    @Autowired
    private MovieController movieController;

    @Autowired
    private OmdbController omdbController;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private OmdbService omdbService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private MovieMapper movieMapper;


    @Test
    public void contextShouldLoad() {
        assertThat(movieController).isNotNull();
        assertThat(accountController).isNotNull();
        assertThat(omdbController).isNotNull();
        assertThat(movieService).isNotNull();
        assertThat(omdbService).isNotNull();
        assertThat(accountService).isNotNull();
        assertThat(movieMapper).isNotNull();
        assertThat(accountMapper).isNotNull();
    }
}
