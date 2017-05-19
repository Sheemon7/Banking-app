package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.ATM;

import java.util.List;

public class ATMDAO extends DataAccessObject<ATM>{

    public ATMDAO() {
        super(ATM.class);
    }

    @Override
    public List<ATM> getEntitiesList() {
        String query = "SELECT p FROM ATM p";
        return ENTITY_MANAGER.createQuery(query, ATM.class).getResultList();
    }
}
