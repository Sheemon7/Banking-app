package banking.app.jpadatabase;

import banking.app.entities.Person;
import banking.app.jpadatabase.DataAccessObject;

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
}