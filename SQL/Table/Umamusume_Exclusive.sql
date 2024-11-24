CREATE TABLE IF NOT EXISTS public.umamusume_exclusive
(
    no integer NOT NULL,
    name character varying(30),
    parameter character varying(30),
    CONSTRAINT umamusume_exclusive_pkey PRIMARY KEY (no),
    CONSTRAINT umamusume_exclusive_name_key UNIQUE (name)
);