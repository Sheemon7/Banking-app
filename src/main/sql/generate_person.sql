CREATE OR REPLACE FUNCTION public.generate_person(count int4)
  RETURNS void
AS
$BODY$
DECLARE
  first_name  VARCHAR [] := ARRAY ['Tom', 'Bob', 'Luke', 'Hans', 'Marie', 'John', 'Petr', 'Simon', 'Anna', 'Cyril'];
  second_name VARCHAR [] := ARRAY ['Harper', 'Sheen', 'Dylan', 'Newton', 'Mandlon', 'Novak', 'Kovar', 'Jagr', 'Voracek', 'Plekanec'];
  address     VARCHAR [] := ARRAY ['Dejvice 2', 'Brno 3', 'Praha 8', 'Ostrava 3', 'Kladno', 'Klatovy', 'Plzen', 'Trinec', 'Pisek', 'Karlovy Vary'];
  f_n_length  INT := 10;
  s_n_length  INT := 10;
  a_length    INT := 10;
  counter     INT := 0;
  pin_range   INT := 1000000;
BEGIN
  WHILE counter < count LOOP
    INSERT INTO person(id_person, address, first_name, second_name, pin)
        VALUES (DEFAULT,
                address [floor((RANDOM() * a_length)) + 1 :: INT],
                first_name [floor((RANDOM() * f_n_length)) + 1 :: INT],
                second_name [floor((RANDOM() * s_n_length)) + 1 :: INT],
                pin [RANDOM() * pin_range :: INT]);
    counter := counter + 1;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;