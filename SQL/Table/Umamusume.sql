CREATE TABLE IF NOT EXISTS public.umamusume
(
    no integer NOT NULL DEFAULT nextval('seq1'),
    name character varying(30),
    parameter character varying(30),
    CONSTRAINT umamusume_pkey PRIMARY KEY (no)
);