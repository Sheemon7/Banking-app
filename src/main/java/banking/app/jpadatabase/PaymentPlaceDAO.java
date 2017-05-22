package banking.app.jpadatabase;

import banking.app.jpadatabase.DataAccessObject;
import banking.app.entities.PaymentPlace;

import java.util.List;

public class PaymentPlaceDAO extends DataAccessObject<PaymentPlace>{

    private static final PaymentPlaceDAO instance = new PaymentPlaceDAO();

    public static PaymentPlaceDAO getInstance() {
        return instance;
    }

    private PaymentPlaceDAO() {
        super(PaymentPlace.class);
    }

    @Override
    public List<PaymentPlace> getEntitiesList() {
        String query = "SELECT p FROM PaymentPlace p";
        return ENTITY_MANAGER.createQuery(query, PaymentPlace.class).getResultList();
    }
}
