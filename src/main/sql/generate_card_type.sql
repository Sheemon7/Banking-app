CREATE OR REPLACE FUNCTION public.generate_card_type(count INT4)
  RETURNS VOID
AS
$BODY$
DECLARE
  name     VARCHAR [] := ARRAY ['MasterCard', 'Visa', 'American Express'];
  n_length INTEGER := 3;
  counter  INTEGER := 0;
BEGIN

  WHILE counter < count LOOP
    INSERT INTO cardtype (type_name) VALUES (name [floor(random() * n_length) + 1 :: INT]);
    counter := counter + 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;