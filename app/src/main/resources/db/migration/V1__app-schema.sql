-- resource
CREATE SEQUENCE resource_seq;
CREATE TABLE resource
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('resource_seq'::regclass),
    name    VARCHAR(255) UNIQUE         NOT NULL,

    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);

-- terrain
CREATE SEQUENCE terrain_seq;
CREATE TABLE terrain
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('terrain_seq'::regclass),
    name    VARCHAR(255) UNIQUE         NOT NULL,

    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE terrain_production_seq;
CREATE TABLE terrain_production
(
    id          BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('terrain_production_seq'::regclass),

    forested    DOUBLE PRECISION            NOT NULL,
    woodless    DOUBLE PRECISION            NOT NULL,

    terrain_id  BIGINT                      NOT NULL REFERENCES terrain,
    resource_id BIGINT                      NOT NULL REFERENCES resource,

    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated     TIMESTAMP WITHOUT TIME ZONE
);
