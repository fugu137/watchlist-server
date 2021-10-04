package net.mjduncan.watchlist.server;

import net.mjduncan.watchlist.server.controller.AccountResource;
import net.mjduncan.watchlist.server.controller.MovieResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private MovieResource movieResource;

    @Autowired
    private AccountResource accountResource;

    @Test
    public void contextShouldLoad() {
        assertThat(movieResource).isNotNull();
        assertThat(accountResource).isNotNull();
    }
}
