package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Card;

import java.util.List;

public class CardDAO extends DataAccessObject<Card>{

    public CardDAO() {
        super(Card.class);
    }

    @Override
    public List<Card> getEntitiesList() {
        String query = "SELECT p FROM Card p";
        return ENTITY_MANAGER.createQuery(query, Card.class).getResultList();
    }
}
