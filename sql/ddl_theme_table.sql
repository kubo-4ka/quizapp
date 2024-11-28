CREATE TABLE theme (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);
ALTER TABLE theme ADD COLUMN is_authentication_required BOOLEAN DEFAULT FALSE;
