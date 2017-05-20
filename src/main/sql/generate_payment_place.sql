CREATE OR REPLACE FUNCTION public.generate_payment_place(count INT4)
  RETURNS VOID
AS
$BODY$
DECLARE
  address VARCHAR [] := ARRAY ['Na', 'Brno 3', 'Praha8', 'Ostrava3', 'Kladno'];
BEGIN
  FOR i IN 1..5 LOOP
    INSERT INTO PaymentPlace VALUES (i, address [i]);
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;