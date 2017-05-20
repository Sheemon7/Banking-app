CREATE OR REPLACE FUNCTION public.generate_atm(count int4)
  RETURNS void
AS
$BODY$
  BEGIN
		
		FOR i in 1..count LOOP
			INSERT INTO atm SELECT nextval('paymentplace_id_payment_place_seq') as id_payment_place, RANDOM()*100 as balance, 100 as max_withdrawal;
		END LOOP;	
	END;
$BODY$
LANGUAGE plpgsql VOLATILE;