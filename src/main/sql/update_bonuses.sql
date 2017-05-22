CREATE OR REPLACE FUNCTION update_bonuses(start_date DATE, end_date DATE, multiplier DECIMAL(38))
  RETURNS VOID
AS
$BODY$
BEGIN

  -- SELECT a
  -- FROM account a
  UPDATE account a
  SET balance = (balance + balance * multiplier)
  WHERE EXISTS (
      SELECT NULL
      FROM (              SELECT c.id_card
                         FROM transaction t
                           JOIN card c ON
                                         t.id_card = c.id_account
                         WHERE t.date >= start_date AND t.date <= end_date
                         GROUP BY c.id_card
                         HAVING COUNT(c) >= 3) as eligible_cards
  WHERE a.id_account = eligible_cards.id_card);

END;
$BODY$
LANGUAGE plpgsql VOLATILE;