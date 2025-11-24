CREATE TABLE IF NOT EXISTS leverx_ratings.comments (
    id SERIAL PRIMARY KEY,
    	message TEXT NOT NULL,
    	author_id INT,
    	hashed_ip CHAR(64),
    	target_seller_id INT NOT NULL,
    	is_approved BOOLEAN DEFAULT FALSE,
    	created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
        updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
        FOREIGN KEY (author_id) REFERENCES leverx_ratings.users(id) ON DELETE SET NULL,
        FOREIGN KEY (target_seller_id) REFERENCES leverx_ratings.users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS leverx_ratings.ratings(
	id SERIAL PRIMARY KEY,
	score SMALLINT CHECK (score >= 1 AND score <= 5),
	author_id INT,
	hashed_ip CHAR(64),
	target_seller_id INT NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT NOW(),
    FOREIGN KEY (author_id) REFERENCES leverx_ratings.users(id) ON DELETE SET NULL,
    FOREIGN KEY (target_seller_id) REFERENCES leverx_ratings.users(id) ON DELETE CASCADE
);
