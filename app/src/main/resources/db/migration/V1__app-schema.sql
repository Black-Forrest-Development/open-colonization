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

-- goods
CREATE SEQUENCE goods_seq;
CREATE TABLE goods
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('goods_seq'::regclass),
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

CREATE SEQUENCE tile_terrain_seq;
CREATE TABLE tile_terrain
(
    id           BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('tile_terrain_seq'::regclass),
    coordinate_x INT    NOT NULL,
    coordinate_y INT    NOT NULL,

    layer_id     BIGINT NOT NULL REFERENCES tile_layer,
    terrain_id   BIGINT NOT NULL REFERENCES terrain
);

-- settlement
CREATE SEQUENCE settlement_seq;
CREATE TABLE settlement
(
    id           BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('settlement_seq'::regclass),
    coordinate_x INT                         NOT NULL,
    coordinate_y INT                         NOT NULL,
    name         VARCHAR(255) UNIQUE         NOT NULL,

    world_id     BIGINT                      NOT NULL REFERENCES world,
    owner_id     BIGINT                      NOT NULL REFERENCES player,

    created      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated      TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE settlement_resource
(
    id_settlement_id BIGINT           NOT NULL REFERENCES settlement,
    id_resource_id   BIGINT           NOT NULL REFERENCES resource,
    amount        DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (id_settlement_id, id_resource_id)
);


-- buildings
CREATE SEQUENCE building_seq;
CREATE TABLE building
(
    id       BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('building_seq'::regclass),
    name     VARCHAR(255) UNIQUE         NOT NULL,

    world_id BIGINT                      NOT NULL REFERENCES world,

    created  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated  TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE building_requirement
(
    id         BIGINT                      NOT NULL PRIMARY KEY REFERENCES building,
    population INT                         NOT NULL,

    created    TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated    TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE building_cost_resources_seq;
CREATE TABLE building_cost_resources
(
    id          BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('building_cost_resources_seq'::regclass),
    amount      INT                         NOT NULL,

    building_id BIGINT                      NOT NULL REFERENCES building,
    resource_id BIGINT                      NOT NULL REFERENCES resource,

    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated     TIMESTAMP WITHOUT TIME ZONE
);

CREATE SEQUENCE building_effect_goods_seq;
CREATE TABLE building_effect_goods
(
    id          BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('building_effect_goods_seq'::regclass),
    amount      INT                         NOT NULL,

    building_id BIGINT                      NOT NULL REFERENCES building,
    goods_id    BIGINT                      NOT NULL REFERENCES goods,

    created     TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated     TIMESTAMP WITHOUT TIME ZONE
);
