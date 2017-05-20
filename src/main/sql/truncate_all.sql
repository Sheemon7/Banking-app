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
  TRUNCATE public.sequence CASCADE;
  ALTER SEQUENCE public.account_id_account_seq RESTART WITH 1;
  ALTER SEQUENCE public.cardtype_id_card_type_seq RESTART WITH 1;
  ALTER SEQUENCE public.paymentplace_id_payment_place_seq RESTART WITH 1;
  ALTER SEQUENCE public.person_id_person_seq RESTART WITH 1;
  ALTER SEQUENCE public.transaction_id_transaction_seq RESTART WITH 1;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;