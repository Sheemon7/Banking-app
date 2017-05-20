package banking.app.jpadatabase;

import banking.app.entities.PaymentPlace;
import banking.app.entities.Person;

import javax.persistence.*;
import java.util.List;

abstract class DataAccessObject<E> extends DatabaseAccess{

    private Class<E> classType;

    DataAccessObject(Class<E> classType) {
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
        // if trigger changed entity
        TRANSACTION.begin();
        ENTITY_MANAGER.refresh(entity);
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
