
CREATE OR REPLACE FUNCTION generate_card_type()
  RETURNS void AS $$
DECLARE
  name varchar[] := array['MasterCard','Visa','American Express'];
BEGIN

  FOR i in 1..3 LOOP
    INSERT INTO card_type VALUES (nextval('cardtype_id_card_type_seq'),name[i]);
  END LOOP;
END;
$$ LANGUAGE plpgsql;

SELECT generate_card_type();


CREATE OR REPLACE FUNCTION generate_atm(count INT)
	RETURNS void AS $$
	
	BEGIN
		
		FOR i in 1..count LOOP
			INSERT INTO atm SELECT nextval('paymentplace_id_payment_place_seq') as id_payment_place, RANDOM()*100 as balance, 100 as max_withdrawal;
		END LOOP;	
	END;
    $$ LANGUAGE plpgsql;



