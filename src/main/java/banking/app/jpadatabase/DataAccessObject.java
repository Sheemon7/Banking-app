package banking.app.jpadatabase;

import banking.app.entities.PaymentPlace;
import banking.app.entities.Person;
import banking.app.util.*;
import banking.app.util.EntityNotFoundException;

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

    public E getEntity(Long id) throws EntityNotFoundException {
        TRANSACTION.begin();
        E entity = ENTITY_MANAGER.find(classType, id);
        TRANSACTION.commit();
        if (entity == null) {
            throw new banking.app.util.EntityNotFoundException(id);
        }
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

    public void refreshEntity(E entity) {
        ENTITY_MANAGER.refresh(entity);
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
