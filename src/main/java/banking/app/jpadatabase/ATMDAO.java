package banking.app.jpadatabase;

import banking.app.entities.ATM;

import javax.persistence.Query;
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
}
