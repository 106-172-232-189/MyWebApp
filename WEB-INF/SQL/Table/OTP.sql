CREATE TABLE OTP
(
    otp text COLLATE pg_catalog."default" NOT NULL,
    expire timestamp with time zone NOT NULL,
    CONSTRAINT otp_pkey PRIMARY KEY (otp)
);