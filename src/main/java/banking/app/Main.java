package banking.app;

import banking.app.jpadatabase.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class Main {
    
    public static void main(String[] args) {
        PersonDAO personDAO = new PersonDAO();
        AccountDAO accountDAO = new AccountDAO();
        ATMDAO atmDAO = new ATMDAO();
        TraderDAO traderDAO = new TraderDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        CardTypeDAO cardTypeDAO = new CardTypeDAO();

        DatabaseAccess da = new DatabaseAccess();
        // smaze triggery! pro uplny restart
//        da.dropAll();
//        da.truncateAll();
        da.updateAllProcedures();

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
        System.out.println(cardTypeDAO.getMostCashflownCardType());
        System.out.println(cardTypeDAO.getMostUsedCardType());

        // test candidate - FOR EVERY ACCOUNT THAT HAS PAID OR WITHDRAWN WITH ANY CARD IN THE GIVEN INTERVAL AT LEAST THREE TIMES,
        // ADD 100 TO THEIR ACCOUNT
        Date begin = Date.valueOf(LocalDate.of(2017, 5, 1));
        Date end = Date.valueOf(LocalDate.of(2017, 6, 1));
        BigDecimal multiplier = new BigDecimal("0.01");
        System.out.println("Updating bonuses between " + begin + " and " + end) ;
        da.updateBonuses(begin, end, multiplier);
    }
}
