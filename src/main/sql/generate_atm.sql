CREATE OR REPLACE FUNCTION public.generate_atm(count INT4)
  RETURNS VOID
AS
$BODY$
BEGIN

  FOR i IN 1..count LOOP
    INSERT INTO atm SELECT
                      nextval('paymentplace_id_payment_place_seq') AS id_payment_place,
                      RANDOM() * 100                               AS balance,
                      100                                          AS max_withdrawal;
  END LOOP;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;