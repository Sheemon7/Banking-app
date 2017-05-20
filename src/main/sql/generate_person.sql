CREATE OR REPLACE FUNCTION public.generate_person(count int4)
  RETURNS void
AS
$BODY$
  DECLARE
	first_name varchar[] := array['Tom','Bob','Luke','Hans','Marie'];
	second_name varchar[] := array['Harper','Sheen','Dylan','Newton','Mandlon'];
	address varchar[] := array['Dejvice 2','Brno 3','Praha8','Ostrava3','Kladno'];
	f_n_length integer := 5;
	s_n_length integer := 5;
	a_length integer := 5;
	counter int := 0;
    BEGIN
    WHILE counter < count LOOP
    	INSERT INTO person VALUES (DEFAULT, first_name[floor((RANDOM()*f_n_length))+1::int], second_name[floor((RANDOM()*s_n_length))+1::int], 3, address[floor((RANDOM()*a_length))+1::int]);
    	counter := counter + 1;
	END LOOP;	
    END;
$BODY$
LANGUAGE plpgsql VOLATILE;