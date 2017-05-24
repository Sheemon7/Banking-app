package banking.app.jpadatabase;

import banking.app.entities.ATM;
import banking.app.entities.Account;
import banking.app.entities.Card;
import banking.app.entities.Transaction;
import banking.app.util.CardMaxWithdrawalException;
import banking.app.util.CardNotAcceptedException;
import banking.app.util.EntityNotFoundException;
import banking.app.util.NonExistingAccountNumber;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class ATMDAO extends DataAccessObject<ATM>{

    private static final ATMDAO instance = new ATMDAO();

    public static ATMDAO getInstance() {
        return instance;
    }

    private ATMDAO() {
        super(ATM.class);
    }

    @Override
    public List<ATM> getEntitiesList() {
        String query = "SELECT p FROM ATM p";
        return ENTITY_MANAGER.createQuery(query, ATM.class).getResultList();
    }

    public List<ATM> getListOfUnsatisfactoryATMS() {
        Query q = ENTITY_MANAGER.createNativeQuery("SELECT a.id_payment_place, a.balance, a.maxwithdrawal " +
                "FROM atm a WHERE a.balance BETWEEN 10000 and 10000 + a.maxwithdrawal ORDER BY a.balance ASC", "ATMResult");
        List<ATM> resultList = q.getResultList();
        return resultList;
    }

    public void withdraw(Card fromCard, long ATMId, BigDecimal amount)
            throws CardMaxWithdrawalException, NonExistingAccountNumber, CardNotAcceptedException {
        ATM atm;
        try {
            atm = ATMDAO.getInstance().getEntity(ATMId);
        } catch (EntityNotFoundException e) {
            throw new NonExistingAccountNumber();
        }

        TRANSACTION.begin();

        if (amount.compareTo(fromCard.getWithdrawalLimit()) > 0) {
            TRANSACTION.rollback();
            throw new CardMaxWithdrawalException();
        }

        if (!atm.getPaymentPlace().getAccepts().contains(fromCard.getCard_type())) {
            TRANSACTION.rollback();
            throw new CardNotAcceptedException();
        }

        Account fromAccount = fromCard.getAccount();
        String message = "Card id " + fromCard.getId_card() + " withdrawn " + amount + " from ATM id " +
                atm.getPaymentPlace().getId_payment_place() + " at " + atm.getPaymentPlace().getAddress();
        Transaction t = new Transaction(fromCard, null, amount,
                message, message, Date.valueOf(LocalDate.now()));
        ENTITY_MANAGER.persist(t);
        fromAccount.setBalance(fromAccount.getBalance().add(amount));
        ENTITY_MANAGER.persist(fromAccount);
        ENTITY_MANAGER.refresh(fromAccount);
        atm.setBalance(atm.getBalance().subtract(amount));
        ENTITY_MANAGER.persist(atm);
        ENTITY_MANAGER.refresh(atm);

        TRANSACTION.commit();
    }
}
