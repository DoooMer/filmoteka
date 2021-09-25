-- DROP TABLE IF EXISTS films;
CREATE TABLE IF NOT EXISTS films
(
    id               INT AUTO_INCREMENT PRIMARY KEY,
    disk_number      INTEGER      NOT NULL,
    title            VARCHAR(255) NOT NULL,
    year             INT,
    genre            TEXT,
    director         TEXT,
    role             TEXT,
    cover_image_link VARCHAR(512)
);
CREATE INDEX IF NOT EXISTS film_disk ON films (disk_number);

CREATE TABLE IF NOT EXISTS film_rents
(
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    film_id     INT          NOT NULL,
    name        VARCHAR(512) NOT NULL,
    created_at  DATETIME     NOT NULL,
    returned_at DATETIME,
    FOREIGN KEY (film_id) REFERENCES films (id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS film_links
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    film_id INT          NOT NULL,
    title   VARCHAR(512) NOT NULL,
    link    VARCHAR(512) NOT NULL,
    FOREIGN KEY (film_id) REFERENCES films (id) ON UPDATE CASCADE ON DELETE CASCADE
);