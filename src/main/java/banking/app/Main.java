package banking.app;

import banking.app.jpadatabase.AccountDAO;
import banking.app.jpadatabase.DataAccessObject;
import banking.app.jpadatabase.PersonDAO;

public class Main {
    
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        System.out.println(personDAO.getEntitiesList());

        AccountDAO accountDAO = new AccountDAO();
        System.out.println(accountDAO.getEntitiesList());
    }
}
