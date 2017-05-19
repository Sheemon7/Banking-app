package banking.app.jpadatabase;

import banking.app.entities.Person;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public abstract class DataAccessObject<E> {

    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("PersistenceUnit");
    static final EntityManager ENTITY_MANAGER = ENTITY_MANAGER_FACTORY.createEntityManager();
    private static final EntityTransaction TRANSACTION = ENTITY_MANAGER.getTransaction();

    private Class<E> classType;

    public DataAccessObject(Class<E> classType) {
        this.classType = classType;
    }

    public void rollback() {
        TRANSACTION.rollback();
    }

    public E getEntity(Long id) {
        TRANSACTION.begin();
        E entity = ENTITY_MANAGER.find(classType, id);
        TRANSACTION.commit();
        return entity;
    }

    public E saveEntity(E entity) {
        TRANSACTION.begin();
        ENTITY_MANAGER.persist(entity);
        TRANSACTION.commit();
        return entity;
    }

    public void deleteEntity(Long id) {
        TRANSACTION.begin();
        E reference = ENTITY_MANAGER.getReference(classType, id);
        ENTITY_MANAGER.remove(reference);
        TRANSACTION.commit();
    }

    public E updateEntity(E entity) {
        TRANSACTION.begin();
        ENTITY_MANAGER.merge(entity);
        TRANSACTION.commit();
        return entity;
    }

    public abstract List<E> getEntitiesList();
}
