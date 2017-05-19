package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.CardType;

import java.util.List;

public class CardTypeDAO extends DataAccessObject<CardType>{

    public CardTypeDAO() {
        super(CardType.class);
    }

    @Override
    public List<CardType> getEntitiesList() {
        String query = "SELECT p FROM CardType p";
        return ENTITY_MANAGER.createQuery(query, CardType.class).getResultList();
    }
}
