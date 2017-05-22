package banking.app;

import banking.app.jpadatabase.*;
import javafx.application.Application;

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
//        da.updateAllProcedures();
//        da.dropAll();
//        da.truncateAll();
//        DataGenerator.generateData(10);

        // agregacni sestavy demo
        // named query
//        System.out.println("Average ATM balance:");
//        System.out.println(accountDAO.getAverageBalance());
//
//        BigDecimal threshold = new BigDecimal("1000000");
//        System.out.println("Number of accounts richer than " + threshold);
//        System.out.println(accountDAO.getNumberOfRich(threshold));
//
//        personDAO.printTheRichestPerson();
//
//        // native query
//        System.out.println("Unsatisfactory ATMS");
//        System.out.println(atmDAO.getListOfUnsatisfactoryATMS());
//
//        int k = 3;
//        traderDAO.printKRichestTraders(k);
//        traderDAO.printKTradersWithBiggestRevenue(k);
//
//        System.out.println(transactionDAO.getSuspiciousTransactions());
//
//        try {
//            System.out.println(cardTypeDAO.getMostCashflownCardType());
//        } catch (Exception e) {
//            System.out.println("There is no card type in database!");
//        }
//        try {
//            System.out.println(cardTypeDAO.getMostUsedCardType());
//        } catch (Exception e) {
//            System.out.println("There is no card type in database!");
//        }
//
//        // test candidate - FOR EVERY ACCOUNT THAT HAS PAID OR WITHDRAWN WITH ANY CARD IN THE GIVEN INTERVAL AT LEAST THREE TIMES,
//        // ADD 100 TO THEIR ACCOUNT
//        Date begin = Date.valueOf(LocalDate.of(2017, 5, 1));
//        Date end = Date.valueOf(LocalDate.of(2017, 6, 1));
//        BigDecimal multiplier = new BigDecimal("0.01");
//        System.out.println("Updating bonuses between " + begin + " and " + end) ;
//        da.updateBonuses(begin, end, multiplier);
//
        Application.launch(Gui.class,args);
    }
}
