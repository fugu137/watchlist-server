--Accounts--
INSERT INTO accounts (username, password, role)
    VALUES ('Michael', 'a0LYOG9BWyJ3XB7WuJFV18.ssn8kw7L./fGcYemNRjJu8ZOsRORuBS', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

INSERT INTO accounts (username, password, role)
    VALUES ('Jie', '$2a$10$BvZ8TR8zh1GBs50BWO5yUe2pcOLqeMrZ9k5RmO6SQQCX8hFcyeedy', 'ROLE_USER')
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