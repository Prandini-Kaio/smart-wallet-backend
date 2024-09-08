--

DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'smartwallet') THEN
      EXECUTE 'CREATE DATABASE smartwallet';
END IF;
END
$$;
