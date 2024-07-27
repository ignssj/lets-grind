CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE solutions (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    language VARCHAR(10) NOT NULL,
    code VARCHAR(1000) NOT NULL,
    user_id UUID,
    problem_id UUID,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (problem_id) REFERENCES problems(id) ON DELETE CASCADE
);