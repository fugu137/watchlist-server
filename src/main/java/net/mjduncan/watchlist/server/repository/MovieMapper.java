package net.mjduncan.watchlist.server.repository;

import net.mjduncan.watchlist.server.model.Movie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface MovieMapper {

    @Select("SELECT * FROM movies")
    List<Movie> findAll();


    @Select("SELECT movies.id, movies.name FROM accounts_movies INNER JOIN movies ON movie_id = movies.id WHERE account_id = ${userId}")
    List<Movie> findAllById(Long userId);


    @Insert("INSERT INTO movies (name) VALUES (#{name})")
    @Options(keyProperty = "id", keyColumn = "id", useGeneratedKeys = true)
    void insertMovie(Movie movie);


    @Insert("INSERT INTO accounts_movies (account_id, movie_id) VALUES (${userId}, ${movieId})")
    void insertMovieByUserId(Long userId, Long movieId);
}
