CREATE TABLE IF NOT EXISTS public.umamusume
(
    no integer NOT NULL DEFAULT nextval('seq1'),
    name character varying(30) NOT NULL,
    parameter character varying(30) NOT NULL,
    CONSTRAINT umamusume_pkey PRIMARY KEY (no)
);