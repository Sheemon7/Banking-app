-- N:M relationship between card type and payment place (accepts relationship)

-- payment_place - id_payment_place - INT, address STRING
-- card_type - id_card_type - INT, name STRING
-- payment_place_card_type - id_card_type INT, id_payment_place INT

-- CRUD DEMO

-- CREATE NEW ACCEPTS
INSERT INTO payment_place_card_type (accepts_id_card_type, acceptedat_id_payment_place)
VALUES (1, 1);

-- READ
-- GET ALL CARD TYPES ID ACCEPTED BY
SELECT accepts_id_card_type FROM payment_place_card_type WHERE acceptedat_id_payment_place = 1;

-- GET ALL PAYMENT PLACES WHERE CARD TYPE IS ACCEPTED
SELECT acceptedat_id_payment_place FROM payment_place_card_type WHERE accepts_id_card_type = 1;

-- DELETE
-- DELETE CARD TYPE AND ALL ACCEPTS RELATIONSHIPS
DELETE FROM payment_place_card_type WHERE accepts_id_card_type = 1;

-- DELETE PAYMENT PLACE AND ALL ACCEPTS RELATIONSHIPS
DELETE FROM payment_place_card_type WHERE acceptedat_id_payment_place = 1;

