CREATE TABLE IF NOT EXISTS public.racing_umamusume_exclusive
(
    no integer NOT NULL,
    name character varying(30) NOT NULL,
    appeared date,
    CONSTRAINT racing_umamusume_exclusive_pkey PRIMARY KEY (no),
    CONSTRAINT racing_umamusume_exclusive_name_fkey FOREIGN KEY (name)
        REFERENCES public.umamusume_exclusive (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);