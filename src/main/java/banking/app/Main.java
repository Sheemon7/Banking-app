package banking.app;

import banking.app.jpadatabase.*;

import java.math.BigDecimal;

public class Main {
    
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();
        ATMDAO atmDAO = new ATMDAO();
        TraderDAO traderDAO = new TraderDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        DatabaseAccess da = new DatabaseAccess();
        // smaze triggery! pro uplny restart
//        da.dropAll();
//        da.truncateAll();
        da.updateAllProcedures();
//
//        Account a1 = new Account("heslo1", new Person("simon", "mandlik", 1, "kladno"));
//        System.out.println(a1.getPassword());
//        accountDAO.saveEntity(a1);
//        long id = a1.getId_account();
//        a1 = null;
//
//        try {
//            a1 = accountDAO.loginAccount(id, "heslospatne");
//        } catch (IncorrectAccountPasswordException | EntityNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(a1);
//        try {
//            a1 = accountDAO.loginAccount(id, "heslo1");
//        } catch (IncorrectAccountPasswordException | EntityNotFoundException e) {
//            e.printStackTrace();
//        }
//        System.out.println(a1);



        // agregacni sestavy demo
        // named query
        System.out.println("Average ATM balance:");
        System.out.println(accountDAO.getAverageBalance());
        BigDecimal threshold = new BigDecimal("1000000");
        System.out.println("Number of accounts richer than " + threshold);
        System.out.println(accountDAO.getNumberOfRich(threshold));
        personDAO.printTheRichestPerson();
        // native query
        System.out.println("Unsatisfactory ATMS");
        System.out.println(atmDAO.getListOfUnsatisfactoryATMS());
        int k = 1;
        traderDAO.printKRichestTraders(k);
        System.out.println(transactionDAO.getSuspiciousTransactions());

        // test candidate - FOR EVERY ACCOUNT THAT HAS PAID OR WITHDRAWN WITH ANY CARD IN THE PREVIOUS MONTH,
        // ADD 100 TO THEIR ACCOUNT
    }
}
