CREATE OR REPLACE FUNCTION public.generate_payment_place(count int4)
  RETURNS void
AS
$BODY$
  DECLARE
		address varchar[] := array['Na','Brno 3','Praha8','Ostrava3','Kladno'];
	BEGIN
		FOR i in 1..5 LOOP
			INSERT INTO PaymentPlace VALUES (i,address[i]);
		END LOOP;
	END;
$BODY$
LANGUAGE plpgsql VOLATILE;