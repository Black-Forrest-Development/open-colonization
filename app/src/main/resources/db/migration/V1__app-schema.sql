-- resource
CREATE SEQUENCE resource_seq;
CREATE TABLE resource
(
    id      BIGINT                      NOT NULL PRIMARY KEY DEFAULT nextval('resource_seq'::regclass),
    name    VARCHAR(255) UNIQUE         NOT NULL,

    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated TIMESTAMP WITHOUT TIME ZONE
);
