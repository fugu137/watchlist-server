--Accounts--
INSERT INTO accounts (username, password, role)
    VALUES ('Michael', '$2a$10$WDo7DxmFFarRceJWIFVG1eMHVYiqsWYBdMxFdDieo4pdbWkahtutm', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts (username, password, role)
    VALUES ('Jacqueline', '$2a$10$9Otu4K3HW.xp9uwv6rQG9eW/FHbJ1IPJVaMrYVGL4HWhrDgc9qkSm', 'ROLE_USER')
    ON CONFLICT DO NOTHING;


--Movies--
INSERT INTO movies (name)
    VALUES ('Gladiator')
    ON CONFLICT DO NOTHING;

INSERT INTO movies (name)
    VALUES ('Alien')
    ON CONFLICT DO NOTHING;

INSERT INTO movies (name)
    VALUES ('Moonrise Kingdom')
    ON CONFLICT DO NOTHING;


--Accounts_Movies--
INSERT INTO accounts_movies
    VALUES(1, 1)
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(1, 2)
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(1, 3)
    ON CONFLICT DO NOTHING;

INSERT INTO accounts_movies
    VALUES(2, 3)
    ON CONFLICT DO NOTHING;