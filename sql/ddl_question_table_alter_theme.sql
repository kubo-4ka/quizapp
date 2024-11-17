ALTER TABLE question ADD COLUMN theme_id INT DEFAULT 1;
ALTER TABLE question ADD FOREIGN KEY (theme_id) REFERENCES theme(id);
