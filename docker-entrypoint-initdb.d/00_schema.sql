CREATE TABLE items
(
    id         BIGSERIAL PRIMARY KEY,
    name       TEXT        NOT NULL,
    price      INT         NOT NULL CHECK ( price >= 0 ),
    qty        INT         NOT NULL CHECK ( qty >= 0 ) DEFAULT 0,
    image      TEXT        NOT NULL,
    removed    BOOL        NOT NULL                    DEFAULT FALSE,
    created    timestamptz NOT NULL                    DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE articles
(
    id             BIGSERIAL PRIMARY KEY,
    name           TEXT                    NOT NULL,
    category       TEXT                    NOT NULL,
    text           TEXT                    NOT NULL,
    first_item_id  BIGINT REFERENCES items NOT NULL,
    second_item_id BIGINT REFERENCES items NOT NULL,
    third_item_id  BIGINT REFERENCES items NOT NULL,
    image          TEXT                    NOT NULL,
    removed        BOOL                    NOT NULL DEFAULT FALSE,
    created        timestamptz             NOT NULL DEFAULT CURRENT_TIMESTAMP
);
