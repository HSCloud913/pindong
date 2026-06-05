CREATE TABLE comments
(
    id         UUID PRIMARY KEY   DEFAULT uuid_generate_v4(),
    pin_id     UUID      NOT NULL REFERENCES pins (id) ON DELETE CASCADE,
    user_id    UUID      NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    content    TEXT      NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    deleted_at TIMESTAMP
);

CREATE TABLE reactions
(
    id         UUID PRIMARY KEY     DEFAULT uuid_generate_v4(),
    pin_id     UUID        NOT NULL REFERENCES pins (id) ON DELETE CASCADE,
    user_id    UUID        NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    type       VARCHAR(20) NOT NULL,
    created_at TIMESTAMP   NOT NULL DEFAULT NOW(),
    CONSTRAINT unique_reaction_per_user UNIQUE (pin_id, user_id, type)
)