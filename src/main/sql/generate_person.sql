CREATE OR REPLACE FUNCTION public.generate_person(count INT4)
  RETURNS VOID
AS
$BODY$
DECLARE
  first_name  VARCHAR [] := ARRAY ['Tom', 'Bob', 'Luke', 'Hans', 'Marie'];
  second_name VARCHAR [] := ARRAY ['Harper', 'Sheen', 'Dylan', 'Newton', 'Mandlon'];
  address     VARCHAR [] := ARRAY ['Dejvice 2', 'Brno 3', 'Praha8', 'Ostrava3', 'Kladno'];
  f_n_length  INTEGER := 5;
  s_n_length  INTEGER := 5;
  a_length    INTEGER := 5;
  counter     INT := 0;
BEGIN
  WHILE counter < count LOOP
    INSERT INTO person VALUES (DEFAULT, first_name [floor((RANDOM() * f_n_length)) + 1 :: INT],
                               second_name [floor((RANDOM() * s_n_length)) + 1 :: INT], 3,
                               address [floor((RANDOM() * a_length)) + 1 :: INT]);
    counter := counter + 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;