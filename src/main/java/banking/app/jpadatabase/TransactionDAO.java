package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Transaction;

import javax.persistence.Query;
import java.util.List;

public class TransactionDAO extends DataAccessObject<Transaction>{

    public TransactionDAO() {
        super(Transaction.class);
    }

    @Override
    public List<Transaction> getEntitiesList() {
        String query = "SELECT p FROM Transaction p";
        return ENTITY_MANAGER.createQuery(query, Transaction.class).getResultList();
    }

    public List<Transaction> getSuspiciousTransactions() {
        Query q = ENTITY_MANAGER.createNativeQuery("SELECT t FROM transaction t WHERE " +
                "(t.messagetosender = '' OR t.messagetosender is NULL) AND" +
                "(t.messagetoreceiver = '' OR t.messagetoreceiver is NULL)");
        return (List<Transaction>)q.getResultList();
    }
}
