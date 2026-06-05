CREATE TABLE pins
(
    id              UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    user_id         UUID         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    neighborhood_id UUID         REFERENCES neighborhoods (id) ON DELETE SET NULL,
    category        VARCHAR(50)  NOT NULL,
    title           VARCHAR(200) NOT NULL,
    content         TEXT         NOT NULL,
    location        GEOMETRY(POINT, 4326) NOT NULL,
    status          VARCHAR(20)  NOT NULL DEFAULT 'active',
    view_count      INT          NOT NULL DEFAULT 0,
    expires_at      TIMESTAMP    NOT NULL DEFAULT NOW() + INTERVAL '7 days',
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP    NOT NULL DEFAULT NOW(),
    CONSTRAINT check_pin_status CHECK (status IN ('active', 'expired', 'deleted'))
);