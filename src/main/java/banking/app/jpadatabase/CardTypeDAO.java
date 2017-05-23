package banking.app.jpadatabase;

import banking.app.entities.CardType;

import javax.persistence.Query;
import java.util.List;

public class CardTypeDAO extends DataAccessObject<CardType> {

    private static final CardTypeDAO instance = new CardTypeDAO();

    public static CardTypeDAO getInstance() {
        return instance;
    }

    private CardTypeDAO() {
        super(CardType.class);
    }

    @Override
    public List<CardType> getEntitiesList() {
        String query = "SELECT p FROM CardType p";
        return ENTITY_MANAGER.createQuery(query, CardType.class).getResultList();
    }

    public String getMostCashflownCardType() throws Exception {
        Query q = ENTITY_MANAGER.createNativeQuery(
                "SELECT ct.typename, SUM(amount) FROM " +
                        "transaction t JOIN card c ON t.id_card = c.id_card AND t.id_account is not NULL " +
                        "JOIN card_type ct ON c.id_card_type = ct.id_card_type GROUP BY ct.typename ORDER BY SUM(t.amount) DESC"
        );
        // jpa doesn't recognize limit
        q.setMaxResults(1);
        Object[] result = (Object[]) q.getSingleResult();
        System.out.println("Users of " + result[0] + " spend " + result[1]);
        return (String) result[0];
    }

    public String getMostUsedCardType() throws Exception {
        Query q = ENTITY_MANAGER.createNativeQuery(
                "SELECT ct.typename, COUNT(*) FROM " +
                        "transaction t JOIN card c ON t.id_card = c.id_card " +
                        "JOIN card_type ct ON c.id_card_type = ct.id_card_type GROUP BY ct.typename ORDER BY COUNT(*) DESC"
        );
        // jpa doesn't recognize limit
        q.setMaxResults(1);
        Object[] result = (Object[]) q.getSingleResult();
        System.out.println("Users of " + result[0] + " spend " + result[1] + " times.");
        return (String) result[0];
    }
}
