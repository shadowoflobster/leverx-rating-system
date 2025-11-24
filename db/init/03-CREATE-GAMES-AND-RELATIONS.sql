CREATE TABLE IF NOT EXISTS leverx_ratings.games (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS leverx_ratings.user_game (
    user_id INT NOT NULL REFERENCES leverx_ratings.users(id) ON DELETE CASCADE,
    game_id INT NOT NULL REFERENCES leverx_ratings.games(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, game_id)
);

CREATE TABLE IF NOT EXISTS leverx_ratings.game_objects (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    user_id INT NOT NULL REFERENCES leverx_ratings.users(id) ON DELETE CASCADE,
    game_id INT NOT NULL REFERENCES leverx_ratings.games(id) ON DELETE CASCADE,
    approved BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT NOW(),
    updated_at TIMESTAMP DEFAULT NOW()
);