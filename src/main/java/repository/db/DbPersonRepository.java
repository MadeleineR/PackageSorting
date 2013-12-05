/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.DeliveryPerson;
import entities.DeliveryWarehouse;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import repository.interfaces.IPersonRepository;

/**
 *
 * @author Madeleine
 */
public class DbPersonRepository implements IPersonRepository{
    
    private EntityManager em;
    private final static Logger log = Logger.getLogger(DbPersonRepository.class);
    
    public DbPersonRepository(EntityManager entMan){
        this.em = entMan;
    }

    public List<DeliveryPerson> getPersonsByName(String name) {
        log.info("getPersonsByName entered");
        EntityTransaction transaction = null;
        
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select per from DeliveryPerson per where per.name = :name");
            query.setParameter("name", name);
            List<DeliveryPerson> result = query.getResultList();
            transaction.commit();
            log.info("Persons found");
            return result;
        }  catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        }  catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
        
    }

    public List<DeliveryPerson> getPersonsByWarehouse(DeliveryWarehouse warehouse) {
        
        log.info("getPersonsByWarehouse entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select per from DeliveryPerson per where per.delivery_warehouse = :wh");
            query.setParameter("wh", warehouse);
            List<DeliveryPerson> result = query.getResultList();
            transaction.commit();
            log.info("Persons found");
            return result;
        }  catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        }  catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public void add(DeliveryPerson object) {
        log.info("add entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(object);
            transaction.commit();
            log.info("Person added");
        } catch(EntityExistsException ex) {
            log.error("Person already exists. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()){
                transaction.rollback();
            }
        }
    }


    public void delete(DeliveryPerson object) {
        log.info("delete entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.remove(object);
            transaction.commit();
            log.info("Person deleted");
            
        } catch (IllegalArgumentException ex) {
            log.error("Person could not be deleted. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public DeliveryPerson getById(long id) {
        log.info("getById entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            DeliveryPerson result = em.find(DeliveryPerson.class, id);
            transaction.commit();
            log.info("Person found");
            return result;
        } catch (IllegalArgumentException ex) {      
            log.error("first argument does not denote an entity type or the second argument is not a valid type for that entity’s primary key or is null");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }

    public List getAll() {
        log.info("getAll found");
        EntityTransaction transaction = null;
        
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select per from DeliveryPerson per");
            List<DeliveryPerson> result = query.getResultList();
            transaction.commit();
            log.info("Persons found");
            return result;
            
        } catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        }  catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }
    }
    
    
    
}
