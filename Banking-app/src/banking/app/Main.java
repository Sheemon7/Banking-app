package banking.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import banking.app.entities.*;
import java.math.BigDecimal;

public class Main {
    
    public static void main(String[] args) {
        //Entity manager and transaction
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        Person person = new Person("Tomas","BOrec",3,"Pop");
        tx.begin();
        em.persist(person);
        tx.commit();
        
        Account account = new Account(person,new BigDecimal(20));
        tx.begin();
        em.persist(account);
        tx.commit();
    }
}
