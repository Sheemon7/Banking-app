CREATE TABLE Address (
  id_adress SERIAL  NOT NULL,
  value     VARCHAR NOT NULL UNIQUE,
  PRIMARY KEY (id_adress)
);

CREATE TABLE CardType (
  id_card_type SERIAL NOT NULL,
  type_name    VARCHAR UNIQUE,
  PRIMARY KEY (id_card_type)
);

CREATE TABLE Person (
  id_person                      SERIAL  NOT NULL,
  id_adress                      INTEGER NOT NULL,
  first_name                     VARCHAR NOT NULL,
  second_name                    VARCHAR NOT NULL,
  personal_identification_number INTEGER,
  PRIMARY KEY (id_person),
  FOREIGN KEY (id_adress)
  REFERENCES Address (id_adress)
);

CREATE TABLE PaymentPlace (
  id_payment_place SERIAL  NOT NULL,
  id_adress        INTEGER NOT NULL,
  PRIMARY KEY (id_payment_place),
  FOREIGN KEY (id_adress)
  REFERENCES Address (id_adress)
);

CREATE TABLE Account (
  id_account INTEGER NOT NULL,
  id_owner   INTEGER NOT NULL,
  balance    NUMERIC NOT NULL DEFAULT 0 CHECK (balance >= 0),
  PRIMARY KEY (id_account),
  FOREIGN KEY (id_owner)
  REFERENCES Person (id_person)
  ON DELETE CASCADE
  ON UPDATE CASCADE
);

CREATE TABLE ATM (
  id_payment_place INTEGER NOT NULL,
  balance          NUMERIC NOT NULL DEFAULT 0 CHECK (balance >= 0),
  max_withdrawal   NUMERIC NOT NULL DEFAULT 500 CHECK (max_withdrawal >= 0),
  FOREIGN KEY (id_payment_place)
  REFERENCES PaymentPlace (id_payment_place)
);

CREATE TABLE Transaction (
  id_transaction   SERIAL  NOT NULL,
  id_payment_place INTEGER NOT NULL,
  id_account       INTEGER NOT NULL,
  amount           NUMERIC NOT NULL CHECK (amount != 0),
  message_receiver VARCHAR,
  message_sender   VARCHAR,
  due_date         DATE    NOT NULL,
  PRIMARY KEY (id_transaction),
  FOREIGN KEY (id_payment_place)
  REFERENCES PaymentPlace (id_payment_place),
  FOREIGN KEY (id_account)
  REFERENCES Account (id_account)
);

CREATE TABLE accepts (
  id_card_type     INTEGER NOT NULL,
  id_payment_place INTEGER NOT NULL,
  PRIMARY KEY (id_card_type, id_payment_place),
  FOREIGN KEY (id_payment_place)
  REFERENCES PaymentPlace (id_payment_place),
  FOREIGN KEY (id_card_type)
  REFERENCES CardType (id_card_type)
);

CREATE TABLE Card (
  id_card          SERIAL  NOT NULL,
  id_card_type     INTEGER NOT NULL,
  id_account       INTEGER NOT NULL,
  withdrawal_limit NUMERIC DEFAULT 500 CHECK (withdrawal_limit >= 0),
  PRIMARY KEY (id_card, id_card_type),
  FOREIGN KEY (id_account)
  REFERENCES Account (id_account),
  FOREIGN KEY (id_card_type)
  REFERENCES CardType (id_card_type)
);

CREATE TABLE Trader (
  id_payment_place SERIAL  NOT NULL,
  id_account       INTEGER NOT NULL,
  id_person        INTEGER NOT NULL,
  PRIMARY KEY (id_payment_place),
  FOREIGN KEY (id_payment_place)
  REFERENCES PaymentPlace (id_payment_place),
  FOREIGN KEY (id_account)
  REFERENCES Account (id_account),
  FOREIGN KEY (id_person)
  REFERENCES Person (id_person)
);


