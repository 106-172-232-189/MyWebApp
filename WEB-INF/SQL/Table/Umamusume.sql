CREATE TABLE IF NOT EXISTS public.umamusume
(
    no integer NOT NULL DEFAULT nextval('seq1'),
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    parameter character varying(30) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT umamusume_pkey PRIMARY KEY (no),
    CONSTRAINT umamusume_name_key UNIQUE (name),
    CONSTRAINT no_range CHECK (no >= 1 AND no <= 800)
);