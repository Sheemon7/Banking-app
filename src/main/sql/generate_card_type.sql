CREATE OR REPLACE FUNCTION public.generate_card_type(count int4)
  RETURNS void
AS
$BODY$
  DECLARE
		name varchar[] := array['MasterCard','Visa','American Express'];
		n_length integer := 3;
		counter integer := 0;
	BEGIN
		
		WHILE counter < count LOOP
			INSERT INTO cardtype(type_name) VALUES (name[floor(random()*n_length)+1::int]);
			counter := counter + 1;
		END LOOP;	
	END;
$BODY$
LANGUAGE plpgsql VOLATILE;