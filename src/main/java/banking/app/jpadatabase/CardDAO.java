package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.Card;

import java.util.List;

public class CardDAO extends DataAccessObject<Card>{

    private static final CardDAO instance = new CardDAO();

    public static CardDAO getInstance() {
        return instance;
    }

    private CardDAO() {
        super(Card.class);
    }

    @Override
    public List<Card> getEntitiesList() {
        String query = "SELECT p FROM Card p";
        return ENTITY_MANAGER.createQuery(query, Card.class).getResultList();
    }
}
