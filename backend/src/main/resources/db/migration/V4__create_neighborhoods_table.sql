CREATE TABLE neighborhoods
(
    id         UUID PRIMARY KEY      DEFAULT uuid_generate_v4(),
    name       VARCHAR(100) NOT NULL,
    center_lat FLOAT8       NOT NULL,
    center_lng FLOAT8       NOT NULL,
    boundary   GEOMETRY(POLYGON, 4326),
    created_at TIMESTAMP    NOT NULL DEFAULT NOW()
)