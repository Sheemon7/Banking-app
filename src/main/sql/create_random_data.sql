CREATE OR REPLACE FUNCTION generate_person(count INT)
  RETURNS void AS $$
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
    INSERT INTO person VALUES (nextval('person_id_person_seq'), address[floor((RANDOM()*a_length))+1::int], first_name[floor((RANDOM()*f_n_length))+1::int], 3,second_name[floor((RANDOM()*s_n_length))+1::int]);
    counter := counter + 1;
  END LOOP;
END;
$$ LANGUAGE plpgsql;
    
--SELECT generate_person(100);


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


--SELECT generate_person(25),generate_card_type();



--INSERT INTO account (id_owner, balance) SELECT id_person,RANDOM()*100 as balance FROM person;



--INSERT INTO card (id_card_type, id_account, withdrawal_limit)
--SELECT id_card_type, id_account, RANDOM()*99+1 AS withdrawal_limit from account, cardtype ORDER BY RANDOM() LIMIT 10;




--INSERT INTO trader
-- SELECT nextval('paymentplace_id_payment_place_seq') as id_payment_place, id_account, id_person FROM (SELECT id_account, id_person FROM account, person
--  ORDER BY RANDOM() LIMIT 30) as rnd;

CREATE OR REPLACE FUNCTION generate_atm(count INT)
	RETURNS void AS $$
	
	BEGIN
		
		FOR i in 1..count LOOP
			INSERT INTO atm SELECT nextval('paymentplace_id_payment_place_seq') as id_payment_place, RANDOM()*100 as balance, 100 as max_withdrawal;
		END LOOP;	
	END;
    $$ LANGUAGE plpgsql;

--SELECT generate_atm(10);

/* TRIGGER se o to postara
INSERT INTO paymentplace
select id_payment_place from atm
union
select id_payment_place from trader;
*/

--INSERT INTO accepts 
--SELECT DISTINCT * FROM (SELECT id_card_type, id_payment_place from paymentplace, cardtype LIMIT 40) nondistinct;


--INSERT INTO transaction(id_payment_place,id_account, amount, due_date)
--SELECT id_payment_place, id_account, ((RANDOM()*99)+1) AS amount, CURRENT_DATE + RANDOM()* 10 * INTERVAL '1 day' AS due_date FROM account, paymentplace ORDER BY RANDOM() LIMIT 100;


--------Random generation ended-
--
--DROP TRIGGER atm_payment_place_trigger ON atm;
--DROP TRIGGER trader_payment_place_trigger ON trader;
-- CREATE TRIGGER atm_payment_place_trigger
--     BEFORE INSERT ON atm
--     FOR EACH ROW
--     EXECUTE PROCEDURE payment_place_trigger_function();

-- CREATE TRIGGER trader_payment_place_trigger
--     BEFORE INSERT ON trader
--     FOR EACH ROW
--     EXECUTE PROCEDURE payment_place_trigger_function();

-- CREATE OR REPLACE FUNCTION payment_place_trigger_function() RETURNS trigger AS $$
-- BEGIN
-- 	INSERT INTO paymentplace VALUES (NEW.id_payment_place);
-- 	RETURN NEW;	
-- END;
-- $$ LANGUAGE plpgsql;


--insert into atm values (40, 100, 100);
--insert into trader values (402, 1020, 1020);
--SELECT * from paymentplace;



