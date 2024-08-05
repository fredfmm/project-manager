CREATE USER fred WITH password 'fred';

CREATE SCHEMA project_service;
ALTER USER fred SET search_path = 'project_service, public';

GRANT USAGE, CREATE ON SCHEMA project_service TO fred;
GRANT ALL ON ALL TABLES IN SCHEMA project_service TO fred;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA project_service TO fred;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA project_service TO fred;
GRANT EXECUTE ON ALL FUNCTIONS IN SCHEMA project_service TO fred;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
GRANT EXECUTE ON FUNCTION public.uuid_generate_v4() TO fred;


ALTER SYSTEM SET max_connections TO '150';
ALTER SYSTEM SET shared_buffers TO '256MB';
