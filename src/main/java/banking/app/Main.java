package banking.app;

import banking.app.entities.ATM;
import banking.app.entities.Account;
import banking.app.entities.Person;
import banking.app.jpadatabase.ATMDAO;
import banking.app.jpadatabase.AccountDAO;
import banking.app.jpadatabase.DatabaseAccess;
import banking.app.jpadatabase.PersonDAO;
import banking.app.util.EntityNotFoundException;
import banking.app.util.IncorrectAccountPasswordException;

import java.math.BigDecimal;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();
        ATMDAO atmdao = new ATMDAO();

        DatabaseAccess da = new DatabaseAccess();
        // smaze triggery! pro uplny restart
//        da.dropAll();
        da.truncateAll();
//        da.updateAllProcedures();
//
        Account a1 = new Account("heslo1", new Person("simon", "mandlik", 1, "kladno"));
        System.out.println(a1.getPassword());
        accountDAO.saveEntity(a1);
        long id = a1.getId_account();
        a1 = null;

        // musis vedet id uctu - gui ti to rekne
        try {
            a1 = accountDAO.loginAccount(id, "heslospatne");
        } catch (IncorrectAccountPasswordException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(a1);
        try {
            a1 = accountDAO.loginAccount(id, "heslo1");
        } catch (IncorrectAccountPasswordException e) {
            e.printStackTrace();
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(a1);


//
//
    }
}
