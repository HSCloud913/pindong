CREATE TABLE users
(
    id            UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    nickname      VARCHAR(50)  NOT NULL,
    email         VARCHAR(255) NOT NULL UNIQUE,
    profile_image VARCHAR(500),
    created_at    TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMP    NOT NULL DEFAULT NOW()
);