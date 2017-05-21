package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Account;
import banking.app.util.EntityNotFoundException;
import banking.app.util.IncorrectAccountPasswordException;

import javax.persistence.NamedQuery;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

public class AccountDAO extends DataAccessObject<Account>{
    private static final Logger LOG = Logger.getLogger(AccountDAO.class.getName());

    public AccountDAO() {
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
}
