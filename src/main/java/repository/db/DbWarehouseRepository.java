/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.DeliveryWarehouse;
import entities.Location;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import repository.interfaces.IWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class DbWarehouseRepository implements IWarehouseRepository {

    private EntityManager em;
    private final static Logger log = Logger.getLogger(DbWarehouseRepository.class);

    public DbWarehouseRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu_package_sorting");
        em = factory.createEntityManager();
    }
    
    public DbWarehouseRepository(EntityManager em){
        this.em = em;
    }

    public void add(DeliveryWarehouse object) {
        log.info("Add entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(object);
            transaction.commit();
            log.info("Warehouse added");
        } catch (EntityExistsException ex) {
            log.error("Delivery Warehouse already exists. Rollback initiated.");
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

    public void delete(DeliveryWarehouse object) {
        log.info("Delete entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.remove(object);
            transaction.commit();
            log.info("Warehouse deleted");
        } catch (IllegalArgumentException ex) {
            log.error("Warehouse could not be deleted. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex){
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public DeliveryWarehouse getById(long id) {
        
        log.info("getById entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            DeliveryWarehouse result = em.find(DeliveryWarehouse.class, id);
            transaction.commit();
            log.info("Warehouse found");
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
    
    public List<DeliveryWarehouse> getWarehouseByKey(String key) {
        log.info("getByWarehouseKey entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select wh from DeliveryWarehouse wh where wh.regionKey = :key");
            query.setParameter("key", key);
            List<DeliveryWarehouse> result = query.getResultList();
            transaction.commit();
            log.info("Warehouse found");
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

    public List<DeliveryWarehouse> getAll() {
        
        log.info("getAll entered");
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select wh from DeliveryWarehouse wh");
            List<DeliveryWarehouse> result = query.getResultList();
            transaction.commit();
            log.info("Warehouses found");
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

    public List<DeliveryWarehouse> getWarehouseByLocation(Location location) {
        
        log.info("getWarehouseByLocation entered");
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select wh from DeliveryWarehouse wh where wh.location = :loc");
            query.setParameter("loc", location);
            List<DeliveryWarehouse> result = query.getResultList();
            transaction.commit();
            log.info("Warehouses by location found");
            return result;

        } catch (NoResultException ex) {
            log.error("No warehouses found.");
            throw new RepositoryException(ex);
        } finally {
            if(transaction.isActive()){
                transaction.rollback();
            }
        }

    }
    
    public List<DeliveryWarehouse> getWarehouseByRegionName(String name) {
        log.info("getWarehouseByName entered");
        EntityTransaction transaction = null;
        
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select wh from DeliveryWarehouse wh where wh.regionName = :name");
            query.setParameter("name", name);
            List<DeliveryWarehouse> result = query.getResultList();
            transaction.commit();
            log.info("Warehouse(s) found");
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
}
