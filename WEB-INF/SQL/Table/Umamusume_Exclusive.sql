CREATE TABLE IF NOT EXISTS public.umamusume_exclusive
(
    no integer NOT NULL,
    name character varying(30) COLLATE pg_catalog."default" NOT NULL,
    parameter character varying(30) COLLATE pg_catalog."default",
    CONSTRAINT umamusume_exclusive_pkey PRIMARY KEY (no),
    CONSTRAINT umamusume_exclusive_name_key UNIQUE (name),
    CONSTRAINT no_range CHECK (no >= 801 AND no <= 1000)
);