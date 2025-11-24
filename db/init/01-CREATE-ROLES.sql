CREATE SCHEMA IF NOT EXISTS leverx_ratings;

CREATE TYPE leverx_ratings.user_role AS ENUM ('Administrator', 'Seller');

CREATE CAST (varchar AS leverx_ratings.user_role) WITH INOUT AS IMPLICIT;