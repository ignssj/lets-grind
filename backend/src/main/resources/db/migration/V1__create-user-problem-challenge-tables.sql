CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE users (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    email VARCHAR(30) NOT NULL,
    password VARCHAR(20) NOT NULL
);

CREATE TABLE problems (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    topic VARCHAR(10) NOT NULL,
    title VARCHAR(20) NOT NULL,
    description VARCHAR(250) NOT NULL
);

CREATE TABLE challenges (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);


