CREATE TABLE IF NOT EXISTS public.racing_umamusume_exclusive
(
    no integer NOT NULL,
    name character varying(30),
    appeared date,
    CONSTRAINT racing_umamusume_exclusive_pkey PRIMARY KEY (no)
);