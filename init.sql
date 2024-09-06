DO $$
BEGIN
   IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'smartwallet') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE smartwallet');
END IF;
END
$$;
