EXPLAIN ANALYZE
UPDATE account a
SET balance = (balance + balance * 0.01)
WHERE a.id_account in (
    SELECT a.id_account
    FROM (              SELECT c.id_account
                        FROM transaction t
                          JOIN card c ON
                                        t.id_card = c.id_account
                        WHERE t.date >= '2017/05/01' AND t.date <= '2017/06/01'
                        GROUP BY c.id_card
                        HAVING COUNT(c) >= 3) as eligible_cards
    WHERE a.id_account = eligible_cards.id_account);

EXPLAIN ANALYZE
UPDATE account a
SET balance = (balance + balance * 0.01)
WHERE EXISTS (
    SELECT NULL
    FROM (              SELECT c.id_account
                        FROM transaction t
                          JOIN card c ON
                                        t.id_card = c.id_account
                        WHERE t.date >= '2017/05/01' AND t.date <= '2017/06/01'
                        GROUP BY c.id_card
                        HAVING COUNT(c) >= 3) as eligible_cards
    WHERE a.id_account = eligible_cards.id_account);