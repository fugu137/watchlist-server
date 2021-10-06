--Accounts--
INSERT INTO accounts (username, password, role)
    VALUES ('Michael', '$2a$10$8LYOG9BWyJ3XB7WuJFV18.ssn8kw7L./fGcYemNRjJu8ZOsRORuBS', 'ROLE_ADMIN')
    ON CONFLICT DO NOTHING;

--Movies--
INSERT INTO movies
    VALUES (1, 'Gladiator')
    ON CONFLICT DO NOTHING;

INSERT INTO movies
    VALUES (2, 'Alien')
    ON CONFLICT DO NOTHING;

INSERT INTO movies
    VALUES (3, 'Moonrise Kingdom')
    ON CONFLICT DO NOTHING;