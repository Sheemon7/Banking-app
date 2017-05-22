package banking.app.jpadatabase;

import banking.app.entities.Trader;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

public class TraderDAO extends DataAccessObject<Trader>{

    private static final TraderDAO instance = new TraderDAO();

    public static TraderDAO getInstance() {
        return instance;
    }

    private TraderDAO() {
        super(Trader.class);
    }

    @Override
    public List<Trader> getEntitiesList() {
        String query = "SELECT p FROM Trader p";
        return ENTITY_MANAGER.createQuery(query, Trader.class).getResultList();
    }

    public void printKRichestTraders(int k) {
        Query q = ENTITY_MANAGER.createNativeQuery(
                "SELECT address, balance FROM account a JOIN trader t ON a.id_account = t.id_account\n" +
                "  JOIN payment_place p ON p.id_payment_place = t.id_payment_place ORDER BY balance DESC " +
                        "LIMIT ?1");
        q.setParameter(1, k);
        List<Object[]> resultList = q.getResultList();
        System.out.println("Showing " + k + " richest traders");
        for (Object[] row : resultList) {
            System.out.println("Address: " + (String) row[0] + " Wealth: " + (BigDecimal) row[1]);
        }
    }

    public void printKTradersWithBiggestRevenue(int k) {
        Query q = ENTITY_MANAGER.createNativeQuery(
                "SELECT address, balance FROM account a JOIN trader t ON a.id_account = t.id_account\n" +
                        "  JOIN payment_place p ON p.id_payment_place = t.id_payment_place ORDER BY balance DESC " +
                        "LIMIT ?1");
        q.setParameter(1, k);
        List<Object[]> resultList = q.getResultList();
        System.out.println("Showing " + k + " traders with biggest revenue in last year");
        for (Object[] row : resultList) {
            System.out.println("Address: " + (String) row[0] + " Income: " + (BigDecimal) row[1]);
        }
    }
}
