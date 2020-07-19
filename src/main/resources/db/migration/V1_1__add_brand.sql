CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1;

CREATE TABLE brand
(
    id      INT8        NOT NULL,
    name    VARCHAR(64) NOT NULL UNIQUE,
    founded DATE,
    defunct DATE,
    PRIMARY KEY (id)
);

CREATE SEQUENCE IF NOT EXISTS brand_seq_id
    START WITH 1
    INCREMENT BY 1
    MINVALUE 1
    NO MAXVALUE
    CACHE 5
    NO CYCLE
    OWNED BY brand.id;
