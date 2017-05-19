package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Trader;

import java.util.List;

public class TraderDAO extends DataAccessObject<Trader>{

    public TraderDAO() {
        super(Trader.class);
    }

    @Override
    public List<Trader> getEntitiesList() {
        String query = "SELECT p FROM Trader p";
        return ENTITY_MANAGER.createQuery(query, Trader.class).getResultList();
    }
}
