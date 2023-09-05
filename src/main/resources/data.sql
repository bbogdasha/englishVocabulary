DROP TABLE IF EXISTS languages;

CREATE TABLE languages (
    language_id SERIAL PRIMARY KEY,
    language_name VARCHAR(255) NOT NULL
);

INSERT INTO languages (language_name) VALUES
    ( 'English' ),
    ( 'Ukraine' );