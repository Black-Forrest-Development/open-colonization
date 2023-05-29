-- world
CREATE SEQUENCE world_seq;
CREATE TABLE world
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('world_seq'::regclass),
    name    VARCHAR(255)                NOT NULL UNIQUE,

    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);

-- player table
CREATE SEQUENCE player_seq;
CREATE TABLE player
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('world_seq'::regclass),
    name     VARCHAR(255)                NOT NULL UNIQUE,

    world_id BIGINT                      NOT NULL REFERENCES world,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
);


-- resource
CREATE SEQUENCE resource_seq;
CREATE TABLE resource
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('resource_seq'::regclass),
    name     VARCHAR(255) UNIQUE         NOT NULL,

    world_id BIGINT                      NOT NULL REFERENCES world,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
);

-- terrain
CREATE SEQUENCE terrain_seq;
CREATE TABLE terrain
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('terrain_seq'::regclass),
    name     VARCHAR(255) UNIQUE         NOT NULL,

    world_id BIGINT                      NOT NULL REFERENCES world,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
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


-- tile
CREATE SEQUENCE tile_map_seq;
CREATE TABLE tile_map
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('tile_map_seq'::regclass),

    width    INT                         NOT NULL,
    height   INT                         NOT NULL,

    world_id BIGINT                      NOT NULL UNIQUE REFERENCES world,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE tile_layer_seq;
CREATE TABLE tile_layer
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('tile_layer_seq'::regclass),
    type    VARCHAR(255)                NOT NULL,

    map_id  BIGINT                      NOT NULL REFERENCES tile_map,

    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE tile_terrain
(
    coordinate_x INT    NOT NULL,
    coordinate_y INT    NOT NULL,

    layer_id        BIGINT NOT NULL REFERENCES tile_layer,
    terrain_id      BIGINT NOT NULL REFERENCES terrain,

    PRIMARY KEY (coordinate_x, coordinate_y)
);

