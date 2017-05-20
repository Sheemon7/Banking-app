package banking.app;

import banking.app.entities.Account;
import banking.app.entities.Person;
import banking.app.jpadatabase.AccountDAO;
import banking.app.jpadatabase.PersonDAO;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();

        personDAO.dropAll();

        Person simon = new Person("simon", "mandlik", 1, "kobylisy");
        Person tomas = new Person("tomas", "palecek", 0, "hanspaulka");
        personDAO.saveEntity(simon);
        personDAO.saveEntity(tomas);
        System.out.println(personDAO.getEntitiesList());
//
//
//        Person simon = personDAO.getEntity(61L);
//        Account simonacc1 = new Account(simon);
//        Account simonacc2 = new Account(simon, new BigDecimal("100"));
////        Account tomasacc = new Account(tomas, new BigDecimal("-100"));
//        accountDAO.saveEntity(simonacc1);
//        accountDAO.saveEntity(simonacc2);
////        accountDAO.saveEntity(tomasacc);
//        personDAO.saveEntity(simon);
//        System.out.println(accountDAO.getEntitiesList());
//
//
//        // CASCADE TEST
//        System.out.println(accountDAO.getEntitiesList());
//        personDAO.deleteEntity(61L);
//        System.out.println(accountDAO.getEntitiesList());
    }
}
