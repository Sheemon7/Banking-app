CREATE OR REPLACE FUNCTION public.drop_all()
  RETURNS VOID
AS
$BODY$
BEGIN
  ALTER TABLE public.transaction
    DROP CONSTRAINT fk_transaction_id_account;
  ALTER TABLE public.transaction
    DROP CONSTRAINT fk_transaction_id_card;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_account;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_person;
  ALTER TABLE public.trader
    DROP CONSTRAINT fk_trader_id_payment_place;
  ALTER TABLE public.payment_place_card_type
    DROP CONSTRAINT fk_payment_place_card_type_accepts_id_card_type;
  ALTER TABLE public.payment_place_card_type
    DROP CONSTRAINT fk_payment_place_card_type_acceptedat_id_payment_place;
  ALTER TABLE public.atm
    DROP CONSTRAINT fk_atm_id_payment_place;
  ALTER TABLE public.card
    DROP CONSTRAINT fk_card_id_account;
  ALTER TABLE public.card
    DROP CONSTRAINT fk_card_id_card_type;
  ALTER TABLE public.account
    DROP CONSTRAINT fk_account_id_owner;
  DROP TABLE public.transaction;
  DROP TABLE public.trader;
  DROP TABLE public.payment_place_card_type;
  DROP TABLE public.atm;
  DROP TABLE public.card;
  DROP TABLE public.account;
  DROP TABLE public.person;
  DROP TABLE public.payment_place;
  DROP TABLE public.card_type;
--   DROP TABLE public.sequence;
  DROP SEQUENCE public.id_account_seq;
  DROP SEQUENCE public.id_card_seq;
  DROP SEQUENCE public.id_card_type_seq;
  DROP SEQUENCE public.id_payment_place_seq;
  DROP SEQUENCE public.id_person_seq;
  DROP SEQUENCE public.id_transaction_seq;
END;
$BODY$
LANGUAGE plpgsql VOLATILE;