package banking.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import banking.app.entities.*;

public class Main {
    
    public static void main(String[] args) {
        //Entity manager and transaction
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PersistenceUnit");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        Person person = new Person("Tomas","Blbecek",3,"debilov");
        tx.begin();
        em.persist(person);
        tx.commit();
        
    }
}
