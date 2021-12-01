--Accounts--
INSERT INTO accounts (username, password, role)
    VALUES ('Michael', '$2a$10$WDo7DxmFFarRceJWIFVG1eMHVYiqsWYBdMxFdDieo4pdbWkahtutm', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts (username, password, role)
    VALUES ('Jacqueline', '$2a$10$9Otu4K3HW.xp9uwv6rQG9eW/FHbJ1IPJVaMrYVGL4HWhrDgc9qkSm', 'ROLE_USER')
    ON CONFLICT DO NOTHING;


--Movies--
--INSERT INTO movies (imdb_id, title)
--    VALUES ('tt0172495', 'Gladiator')
--    ON CONFLICT DO NOTHING;
--
--INSERT INTO movies (imdb_id, title)
--    VALUES ('tt0078748', 'Alien')
--    ON CONFLICT DO NOTHING;
--
--INSERT INTO movies (imdb_id, title)
--    VALUES ('tt1748122', 'Moonrise Kingdom')
--    ON CONFLICT DO NOTHING;
--
--
----Accounts_Movies--
--INSERT INTO accounts_movies
--    VALUES(1, 'tt0172495')
--    ON CONFLICT DO NOTHING;
--
--INSERT INTO accounts_movies
--    VALUES(1, 'tt0078748')
--    ON CONFLICT DO NOTHING;
--
--INSERT INTO accounts_movies
--    VALUES(1, 'tt1748122')
--    ON CONFLICT DO NOTHING;
--
--INSERT INTO accounts_movies
--    VALUES(2, 'tt1748122')
--    ON CONFLICT DO NOTHING;