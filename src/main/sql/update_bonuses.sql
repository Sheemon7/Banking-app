CREATE OR REPLACE FUNCTION update_bonuses(start_date DATE, end_date DATE, multiplier DECIMAL(38))
  RETURNS VOID
AS
$BODY$
BEGIN

  UPDATE account a
  SET balance = balance + balance * multiplier
  WHERE a.id_account IN (SELECT t.id_account
                       FROM transaction t
                         JOIN account a ON
                                          t.id_account = a.id_account
                       WHERE t.date >= start_date AND t.date <= end_date
                       GROUP BY t.id_account
                       HAVING COUNT(t.id_transaction) >= 3);

END;
$BODY$
LANGUAGE plpgsql VOLATILE;