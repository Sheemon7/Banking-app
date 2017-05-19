package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Account;

import java.util.List;

public class AccountDAO extends DataAccessObject<Account>{

    public AccountDAO() {
        super(Account.class);
    }

    @Override
    public List<Account> getEntitiesList() {
        String query = "SELECT p FROM Account p";
        return ENTITY_MANAGER.createQuery(query, Account.class).getResultList();
    }
}
