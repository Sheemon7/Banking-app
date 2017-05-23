CREATE OR REPLACE FUNCTION public.truncate_all()
  RETURNS VOID
AS
$BODY$
BEGIN
  TRUNCATE public.transaction CASCADE;
  TRUNCATE public.trader CASCADE;
  TRUNCATE public.payment_place_card_type CASCADE;
  TRUNCATE public.card CASCADE;
  TRUNCATE public.atm CASCADE;
  TRUNCATE public.account CASCADE;
  TRUNCATE public.person CASCADE;
  TRUNCATE public.payment_place CASCADE;
  TRUNCATE public.card_type CASCADE;
--   TRUNCATE public.sequence CASCADE;
  ALTER SEQUENCE public.id_account_seq RESTART WITH 1;
  ALTER SEQUENCE public.id_card_seq RESTART WITH 1;
  ALTER SEQUENCE public.id_card_type_seq RESTART WITH 1;
  ALTER SEQUENCE public.id_payment_place_seq RESTART WITH 1;
  ALTER SEQUENCE public.id_person_seq RESTART WITH 1;
  ALTER SEQUENCE public.id_transaction_seq RESTART WITH 1;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;