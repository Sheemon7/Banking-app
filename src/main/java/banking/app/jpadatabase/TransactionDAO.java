package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Transaction;

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
}
