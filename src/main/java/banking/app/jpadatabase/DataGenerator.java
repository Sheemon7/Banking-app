package banking.app.jpadatabase;

import banking.app.entities.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DataGenerator extends DatabaseAccess {
    private static final AccountDAO ACCOUNT_DAO = AccountDAO.getInstance();
    private static final ATMDAO ATM_DAO = ATMDAO.getInstance();
    private static final CardDAO CARD_DAO = CardDAO.getInstance();
    private static final CardTypeDAO CARD_TYPE_DAO = CardTypeDAO.getInstance();
    private static final PaymentPlaceDAO PAYMENT_PLACE_DAO = PaymentPlaceDAO.getInstance();
    private static final PersonDAO PERSON_DAO = PersonDAO.getInstance();
    private static final TraderDAO TRADER_DAO = TraderDAO.getInstance();
    private static final TransactionDAO TRANSACTION_DAO = TransactionDAO.getInstance();

    private static final Random RANDOM = new Random(0L);

    private static final int PIN_RANGE = Integer.MAX_VALUE;
    private static final BigDecimal MAX_INIT_BALANCE = new BigDecimal("1000000");
    private static final List<String> FIRST_NAMES = loadDataString("data\\first_names.txt");
    private static final List<String> SECOND_NAMES = loadDataString("data\\second_names.txt");
    private static final List<String> ADDRESSES = loadDataString("data\\addresses.txt");
    private static final List<String> MESSAGES = loadDataString("data\\messages.txt");

    private static final List<String> CARD_TYPES =
            Arrays.asList("MasterCard", "Visa", "American Express");
    private static final List<BigDecimal> MAX_WITHDRAWALS_CARDS =
            Arrays.asList(new BigDecimal("1000"), new BigDecimal("2000"), new BigDecimal("5000"));
    private static final List<BigDecimal> MAX_WITHDRAWALS_ATMS =
            Arrays.asList(new BigDecimal("1000"),
                    new BigDecimal("2000"), new BigDecimal("5000"), new BigDecimal("10000"), new BigDecimal("20000"),
                    new BigDecimal("50000"), new BigDecimal("100000"));

    private static final Date BEGIN_TRANSACTION_DATE = Date.valueOf(LocalDate.of(2000, 1, 1));
    private static final Date END_TRANSACTION_DATE = Date.valueOf(LocalDate.of(2017, 5, 1));

    private static List<String> loadDataString(String fname) {
        List<String> result = null;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fname)));
            result = br.lines().collect(Collectors.toList());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void generateData(int count) {
        System.out.println("Generating new people");
        generatePersons(count);
        System.out.println("Generating new card types");
        generateCardTypes();
        System.out.println("Generating new accounts");
        generateAccounts(2 * count);
        System.out.println("Generating new cards");
        generateCards(3 * count);
        System.out.println("Generating new traders");
        generateTraders(count);
        System.out.println("Generating new atms");
        generateATMS(count);
        System.out.println("Generating new transactions");
        generateTransactions(count * 50);
    }

    private static void generatePersons(int count) {
        Random r = new Random();
        for (int i = 0; i < count; i++) {
            Person p = new Person(FIRST_NAMES.get(r.nextInt(FIRST_NAMES.size())),
                    SECOND_NAMES.get(r.nextInt(SECOND_NAMES.size())),
                    r.nextInt(PIN_RANGE), ADDRESSES.get(r.nextInt(ADDRESSES.size())));
            PERSON_DAO.saveEntity(p);
        }
    }

    private static void generateCardTypes() {
        for (String CARD_TYPE : CARD_TYPES) {
            CardType ct = new CardType(CARD_TYPE);
            CARD_TYPE_DAO.saveEntity(ct);
        }
    }

    private static void generateAccounts(int count) {
        List<Person> persons = PERSON_DAO.getEntitiesList();
        for (int i = 0; i < count; i++) {
            Person p = persons.get(RANDOM.nextInt(persons.size()));
            BigDecimal balance = new BigDecimal(Math.random()).multiply(MAX_INIT_BALANCE).round(new MathContext(0, RoundingMode.HALF_UP));
            Account a = new Account("DUMMY_PASSWORD", p, balance);
            ACCOUNT_DAO.saveEntity(a);
        }
    }

    private static void generateCards(int count) {
        List<CardType> cardTypes = CARD_TYPE_DAO.getEntitiesList();
        List<Account> accounts = ACCOUNT_DAO.getEntitiesList();
        for (int i = 0; i < count; i++) {
            Card c = new Card(cardTypes.get(RANDOM.nextInt(cardTypes.size())),
                    accounts.get(RANDOM.nextInt(accounts.size())),
                    MAX_WITHDRAWALS_CARDS.get(RANDOM.nextInt(MAX_WITHDRAWALS_CARDS.size())));
            CARD_DAO.saveEntity(c);
        }
    }

    private static void generateTraders(int count) {
        // owner of payment place can be different from owner of account!
        List<Person> persons = PERSON_DAO.getEntitiesList();
        List<Account> accounts = ACCOUNT_DAO.getEntitiesList();
        for (int i = 0; i < count; i++) {
            PaymentPlace pp = generatePaymentPlace();
            Person p = persons.get(RANDOM.nextInt(persons.size()));
            Account a = accounts.get(RANDOM.nextInt(accounts.size()));
            Trader t = new Trader(pp, a, p);
            TRADER_DAO.saveEntity(t);
        }
    }

    private static void generateATMS(int count) {
        for (int i = 0; i < count; i++) {
            PaymentPlace pp = generatePaymentPlace();
            BigDecimal balance = new BigDecimal(Math.random()).multiply(MAX_INIT_BALANCE).round(new MathContext(0, RoundingMode.HALF_UP));
            ATM atm = new ATM(pp, balance,
                    MAX_WITHDRAWALS_ATMS.get(RANDOM.nextInt(MAX_WITHDRAWALS_ATMS.size())));
            ATM_DAO.saveEntity(atm);
        }

    }

    private static PaymentPlace generatePaymentPlace() {
        List<CardType> cardTypes = CARD_TYPE_DAO.getEntitiesList();
        PaymentPlace pp = new PaymentPlace(ADDRESSES.get(RANDOM.nextInt(ADDRESSES.size())));
        List<CardType> accepts = new ArrayList<>();
        pp.setAccepts(accepts);
        Collections.shuffle(cardTypes);
        // at least one accepted card type
        for (int i = 0; i < RANDOM.nextInt(cardTypes.size() - 1) + 1; i++) {
            pp.getAccepts().add(cardTypes.get(i));
        }
        PAYMENT_PLACE_DAO.saveEntity(pp);
        return pp;
    }

    private static void generateTransactions(int count) {
        List<Card> cards = CARD_DAO.getEntitiesList();
        List<Account> accounts = ACCOUNT_DAO.getEntitiesList();
        List<ATM> atms = ATM_DAO.getEntitiesList();
        List<Trader> traders = TRADER_DAO.getEntitiesList();

        // ATMS withdrawals
        for (int i = 0; i < count / 2; i++) {
            Card c = cards.get(RANDOM.nextInt(cards.size()));
            // find ATM, s.t. it accepts this card
            ATM atm;
            do {
                atm = atms.get(RANDOM.nextInt(atms.size()));
            } while (!atm.getPaymentPlace().getAccepts().contains(c.getCard_type()));

            // ATM transactions have null for account and negative remainders
            BigDecimal withdrew = new BigDecimal(Math.random()).multiply(c.getWithdrawalLimit()).round(new MathContext(2, RoundingMode.HALF_UP));
            withdrew = withdrew.multiply(new BigDecimal("-1"));
            Long d = BEGIN_TRANSACTION_DATE.getTime() + ((long) (RANDOM.nextDouble() * (END_TRANSACTION_DATE.getTime() - BEGIN_TRANSACTION_DATE.getTime())));
            Date date = new Date(d);
            // ATM transactions don't have messages as well
            String message = "Card id " + c.getId_card() + " withdrawn " + withdrew + " from ATM id " +
                    atm.getPaymentPlace().getId_payment_place() + " at " + atm.getPaymentPlace().getAddress();
            Transaction tr = new Transaction(c, null, withdrew, message, message, date);
            TRANSACTION_DAO.saveEntity(tr);
        }

        // Trader transactions
        for (int i = 0; i < count / 2; i++) {
            Card c = cards.get(RANDOM.nextInt(cards.size()));
            // find ATM, s.t. it accepts this card
            Trader t;
            do {
                t = traders.get(RANDOM.nextInt(traders.size()));
            } while (!t.getPaymentPlace().getAccepts().contains(c.getCard_type()));

            BigDecimal paid = new BigDecimal(Math.random()).multiply(c.getWithdrawalLimit()).round(new MathContext(2, RoundingMode.HALF_UP));
            Long d = BEGIN_TRANSACTION_DATE.getTime() + ((long) (RANDOM.nextDouble() * (END_TRANSACTION_DATE.getTime() - BEGIN_TRANSACTION_DATE.getTime())));
            Date date = new Date(d);
            Account a = accounts.get(RANDOM.nextInt(accounts.size()));
            String messageReceiver = MESSAGES.get(RANDOM.nextInt(MESSAGES.size()));
            String messageSender = MESSAGES.get(RANDOM.nextInt(MESSAGES.size()));
            Transaction tr = new Transaction(c, a, paid, messageSender, messageReceiver, date);
            TRANSACTION_DAO.saveEntity(tr);
        }
    }
}
