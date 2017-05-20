CREATE OR REPLACE FUNCTION public.drop_all()
  RETURNS VOID
AS
$BODY$
BEGIN
  ALTER TABLE public.transaction
    DROP CONSTRAINT fk_transaction_id_account;
  ALTER TABLE public.transaction
    DROP CONSTRAINT fk_transaction_id_payment_place;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_account;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_person;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_payment_place;
  ALTER TABLE public.payment_place_card_type
    DROP CONSTRAINT fk_payment_place_card_type_accepts_id;
  ALTER TABLE public.payment_place_card_type
    DROP CONSTRAINT fk_payment_place_card_type_acceptedat_id_payment_place;
  ALTER TABLE public.card
    DROP CONSTRAINT fk_card_id_account;
  ALTER TABLE public.card
    DROP CONSTRAINT fk_card_id_card_type;
  ALTER TABLE public.atm
    DROP CONSTRAINT fk_atm_id_payment_place;
  ALTER TABLE public.account
    DROP CONSTRAINT fk_account_id_owner;
  DROP TABLE public.transaction;
  DROP TABLE public.trader;
  DROP TABLE public.payment_place_card_type;
  DROP TABLE public.card;
  DROP TABLE public.atm;
  DROP TABLE public.account;
  DROP TABLE public.person;
  DROP TABLE public.payment_place;
  DROP TABLE public.card_type;
  DROP TABLE public.sequence;
  ALTER SEQUENCE public.account_id_account_seq RESTART WITH 1;
  ALTER SEQUENCE public.cardtype_id_card_type_seq RESTART WITH 1;
  ALTER SEQUENCE public.paymentplace_id_payment_place_seq RESTART WITH 1;
  ALTER SEQUENCE public.person_id_person_seq RESTART WITH 1;
  ALTER SEQUENCE public.transaction_id_transaction_seq RESTART WITH 1;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;