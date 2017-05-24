CREATE OR REPLACE FUNCTION update_bonuses(start_date DATE, end_date DATE, multiplier DECIMAL(38))
  RETURNS VOID
AS
$BODY$
BEGIN

  -- SELECT a
  -- FROM account a
  UPDATE account a
  SET balance = (balance + balance * 0.01)
  WHERE EXISTS(
      SELECT NULL
      FROM (
             SELECT c.id_account
             FROM transaction t
               JOIN card c ON
                             t.id_card = c.id_account
             WHERE t.date >= '2017/05/01' AND t.date <= '2017/06/01'
             GROUP BY c.id_card
             HAVING COUNT(c) >= 3
           ) AS eligible_cards
      WHERE a.id_account = eligible_cards.id_account);
END;
$BODY$
LANGUAGE plpgsql VOLATILE;