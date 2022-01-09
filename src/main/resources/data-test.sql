--Accounts--
INSERT INTO accounts (username, password, role)
    VALUES ('Michael', '$2a$10$WDo7DxmFFarRceJWIFVG1eMHVYiqsWYBdMxFdDieo4pdbWkahtutm', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts (username, password, role)
    VALUES ('Jacqueline', '$2a$10$9Otu4K3HW.xp9uwv6rQG9eW/FHbJ1IPJVaMrYVGL4HWhrDgc9qkSm', 'ROLE_USER')
    ON CONFLICT DO NOTHING;


--Movies--
INSERT INTO movies (imdb_id, title, year, synopsis, imdb_rating, tomatoes_rating, metacritic_rating, poster_url)
    VALUES ('tt0172495', 'Gladiator', 2001, 'A former Roman General sets out to exact vengeance against the corrupt emperor who murdered his family and sent him into slavery.', '8.8/10', '82%', '80%', 'www.gladiator_poster_url.com')
    ON CONFLICT DO NOTHING;

INSERT INTO movies (imdb_id, title, year, synopsis, imdb_rating, tomatoes_rating, metacritic_rating, poster_url)
    VALUES ('tt0078748', 'Alien', 1989, 'After a space merchant vessel receives an unknown transmission as a distress call, one of the crew is attacked by a mysterious life form and they soon realize that its life cycle has merely begun.', '9/10', '92%', '88%', 'www.alien_poster_url.com')
    ON CONFLICT DO NOTHING;

INSERT INTO movies (imdb_id, title, year, synopsis, imdb_rating, tomatoes_rating, metacritic_rating, poster_url)
    VALUES ('tt1748122', 'Moonrise Kingdom', 2007, 'A pair of young lovers flee their New England town, which causes a local search party to fan out to find them.', '8.6/10', '87%', '80%', 'www.moonrisekingdorm_poster_url.com')
    ON CONFLICT DO NOTHING;


--Accounts_Movies--
INSERT INTO accounts_movies
    VALUES(1, 'tt0172495')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(1, 'tt0078748')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(1, 'tt1748122')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(2, 'tt1748122')
    ON CONFLICT DO NOTHING;