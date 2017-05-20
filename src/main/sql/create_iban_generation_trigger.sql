BEGIN;
DROP TRIGGER IF EXISTS create_iban ON ATM CASCADE;
DROP FUNCTION IF EXISTS public.create_random_iban() CASCADE;

CREATE OR REPLACE FUNCTION create_random_iban()
  RETURNS TRIGGER AS
$create_random_iban$
DECLARE
  nums   TEXT [] := '{0,1,2,3,4,5,6,7,8,9}';
  result TEXT := 'CZ';
  i      INTEGER := 0;
BEGIN
  FOR i IN 1..30 LOOP
    result := result || nums [1 + random() * (array_length(nums, 1) - 1)];
  END LOOP;
  UPDATE account
  SET iban = result
  WHERE id_account = NEW.id_account;
  RAISE INFO 'New iban is %!', result;
  RETURN NEW;
END;
$create_random_iban$ LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER create_iban
AFTER INSERT ON account
FOR EACH ROW
WHEN (pg_trigger_depth() = 0) -- Prevent recursive trigger calls
EXECUTE PROCEDURE create_random_iban();
END;