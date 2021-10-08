--Accounts--

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