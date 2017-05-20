DROP FUNCTION public.log_increase_balance() CASCADE;

CREATE OR REPLACE FUNCTION log_increase_balance()
  RETURNS trigger AS
$log_increase_balance$
BEGIN
  IF NEW.balance IS NULL THEN
    RAISE EXCEPTION 'balance cannot be null';
  END IF;
  IF NEW.balance < 10000 THEN
    RAISE INFO 'ATM % is low on balance, refilling!', NEW.id_payment_place;
    NEW.balance := NEW.balance + 100000;
    UPDATE ATM SET balance = NEW.balance
      WHERE id_payment_place = NEW.id_payment_place;
  END IF;
  RAISE INFO 'New balance is %!', NEW.balance;
  RETURN NEW;
END;
$log_increase_balance$ LANGUAGE plpgsql VOLATILE;

CREATE TRIGGER check_balance
AFTER INSERT OR UPDATE OF balance ON ATM
FOR EACH ROW
WHEN (pg_trigger_depth() = 0) -- Prevent recursive trigger calls
EXECUTE PROCEDURE log_increase_balance();