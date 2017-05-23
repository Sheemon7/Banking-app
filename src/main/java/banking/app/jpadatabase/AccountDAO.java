package banking.app.jpadatabase;

import banking.app.entities.Account;
import banking.app.entities.Card;
import banking.app.entities.Transaction;
import banking.app.util.*;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

public class AccountDAO extends DataAccessObject<Account> {
    private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());

    private static final AccountDAO instance = new AccountDAO();

    public static AccountDAO getInstance() {
        return instance;
    }

    private AccountDAO() {
        super(Account.class);
    }

    @Override
    public List<Account> getEntitiesList() {
        String query = "SELECT p FROM Account p";
        return ENTITY_MANAGER.createQuery(query, Account.class).getResultList();
    }

    public Account loginAccount(Long id, String password) throws IncorrectAccountPasswordException, EntityNotFoundException {
        Account ret = getEntity(id);
        if (ret.getPassword().equals(ret.getSaltyPasswordHash(password))) {
            LOG.info("Login successful for " + id);
            return ret;
        } else {
            LOG.info("Login unsuccessful for " + id);
            throw new IncorrectAccountPasswordException(id);
        }
    }

    public BigDecimal getAverageBalance() {
        Query q = ENTITY_MANAGER.createNamedQuery("Account.getAverageBalance");
        return BigDecimal.valueOf((Double) q.getSingleResult());
    }

    public long getNumberOfRich(BigDecimal threshold) {
        Query q = ENTITY_MANAGER.createNamedQuery("Account.getNumberOfRich");
        q.setParameter("threshold", threshold);
        return (Long) q.getSingleResult();
    }

    public void pay(Card fromCard, long toAccountId, String messageToSender, String messageToReceiver, BigDecimal amount)
            throws NonExistingAccountNumber, NotEnoughMoneyException {
        Account toAccount, fromAccount = fromCard.getAccount();
        try {
            toAccount = getEntity(toAccountId);
        } catch (EntityNotFoundException e) {
            throw new NonExistingAccountNumber();
        }

        TRANSACTION.begin();

        if (amount.compareTo(fromAccount.getBalance()) > 0) {
            TRANSACTION.rollback();
            throw new NotEnoughMoneyException();
        }

        Transaction t = new Transaction(fromCard, toAccount, amount,
                messageToSender, messageToReceiver, Date.valueOf(LocalDate.now()));
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));
        toAccount.getReceivedTransactions().add(t);
        fromCard.getSentTransactions().add(t);
        TRANSACTION.commit();
    }
}
