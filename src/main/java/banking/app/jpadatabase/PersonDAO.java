package banking.app.jpadatabase;

import banking.app.entities.Person;
import banking.app.jpadatabase.DataAccessObject;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;


public class PersonDAO extends DataAccessObject<Person> {

    public PersonDAO() {
        super(Person.class);
    }

    @Override
    public List<Person> getEntitiesList() {
        String query = "SELECT p FROM Person p";
        return ENTITY_MANAGER.createQuery(query, Person.class).getResultList();
    }

    public void printTheRichestPerson() {
        Query q = ENTITY_MANAGER.createNamedQuery("Person.getTheRichestPerson");
        Object[] result = (Object[]) q.getResultList().get(0);
        System.out.println("the richest person:");
        System.out.println(result[0] + " " + result[1] + " Wealth: " + result[2]);
    }
}