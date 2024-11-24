CREATE TABLE IF NOT EXISTS public.racing_umamusume
(
    no integer NOT NULL DEFAULT nextval('seq2'),
    name character varying(30) NOT NULL,
    appeared date,
    CONSTRAINT racing_umamusume_pkey PRIMARY KEY (no),
    CONSTRAINT racing_umamusume_name_fkey FOREIGN KEY (name)
        REFERENCES public.umamusume (name) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);