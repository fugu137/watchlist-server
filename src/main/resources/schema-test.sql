--Drop Tables--
DROP TABLE IF EXISTS accounts_movies;

DROP TABLE IF EXISTS accounts;

DROP TABLE IF EXISTS movies;


--Create Tables--
CREATE TABLE accounts (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    username VARCHAR(55) UNIQUE NOT NULL,
    password VARCHAR(155) NOT NULL,
    role VARCHAR(15) NOT NULL
);

CREATE TABLE movies (
    imdb_id VARCHAR(10) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    year SMALLINT,
    imdb_rating VARCHAR(10),
    tomatoes_rating VARCHAR(10),
    metacritic_rating VARCHAR(10),
    poster_url VARCHAR(155)
);

CREATE TABLE accounts_movies (
    account_id BIGINT REFERENCES accounts(id),
    movie_id VARCHAR(10) REFERENCES movies(imdb_id),
    PRIMARY KEY(account_id, movie_id)
);