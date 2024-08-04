-- Role: rdsadmin
-- DROP ROLE IF EXISTS rdsadmin;

CREATE ROLE rdsadmin WITH
  LOGIN
  SUPERUSER
  INHERIT
  CREATEDB
  CREATEROLE
  REPLICATION
  VALID UNTIL 'infinity' ;

ALTER ROLE rdsadmin SET TimeZone TO 'utc';
ALTER ROLE rdsadmin SET log_statement TO 'all';
ALTER ROLE rdsadmin SET log_min_error_statement TO 'debug5';
ALTER ROLE rdsadmin SET log_min_messages TO 'panic';
ALTER ROLE rdsadmin SET exit_on_error TO '0';
ALTER ROLE rdsadmin SET statement_timeout TO '0';
ALTER ROLE rdsadmin SET role TO 'rdsadmin';
ALTER ROLE rdsadmin SET auto_explain.log_min_duration TO '-1';
ALTER ROLE rdsadmin SET temp_file_limit TO '-1';
ALTER ROLE rdsadmin SET search_path TO pg_catalog, public;
ALTER ROLE rdsadmin SET pg_hint_plan.enable_hint TO 'off';
ALTER ROLE rdsadmin SET default_transaction_read_only TO 'off';

-- Role: rds_ad
-- DROP ROLE IF EXISTS rds_ad;

CREATE ROLE rds_ad WITH
  NOLOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION;

-- Role: lambda
-- DROP ROLE IF EXISTS lambda;

CREATE ROLE lambda WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION;

ALTER ROLE lambda SET search_path TO "projectapi_service, public";

-- FUNCTION: public.uuid_generate_v4()

-- DROP FUNCTION IF EXISTS public.uuid_generate_v4();

CREATE OR REPLACE FUNCTION public.uuid_generate_v4(
	)
    RETURNS uuid
    LANGUAGE 'c'
    COST 1
    VOLATILE STRICT PARALLEL SAFE
AS '$libdir/uuid-ossp', 'uuid_generate_v4'
;

ALTER FUNCTION public.uuid_generate_v4()
    OWNER TO rdsadmin;

GRANT EXECUTE ON FUNCTION public.uuid_generate_v4() TO PUBLIC;

GRANT EXECUTE ON FUNCTION public.uuid_generate_v4() TO lambda;

GRANT EXECUTE ON FUNCTION public.uuid_generate_v4() TO rdsadmin;

