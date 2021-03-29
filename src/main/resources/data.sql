-- DROP TABLE IF EXISTS films;
CREATE TABLE IF NOT EXISTS films
(
    id INT AUTO_INCREMENT PRIMARY KEY,
    disk_number INTEGER NOT NULL,
    title VARCHAR(255) NOT NULL,
    year INT,
    genre TEXT,
    director TEXT,
    role TEXT,
    cover_image_link VARCHAR(512)
);
CREATE INDEX IF NOT EXISTS film_disk ON films (disk_number);