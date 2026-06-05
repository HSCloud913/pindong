CREATE TABLE oauth_accounts
(
    id           UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    user_id      UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    provider     VARCHAR(20)  NOT NULL,
    provider_uid VARCHAR(255) NOT NULL,
    created_at   TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT unique_oauth_provider UNIQUE (provider, provider_uid)
);