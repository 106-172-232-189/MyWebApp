CREATE TABLE IF NOT EXISTS public.racing_umamusume
(
    no integer NOT NULL DEFAULT nextval('seq2'),
    name character varying(30),
    appeared date,
    CONSTRAINT racing_umamusume_pkey PRIMARY KEY (no)
)