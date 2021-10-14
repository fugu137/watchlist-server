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
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE accounts_movies (
    account_id BIGINT REFERENCES accounts(id),
    movie_id BIGINT REFERENCES movies(id),
    PRIMARY KEY(account_id, movie_id)
);