/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.Package;
import entities.Recipient;
import java.util.List;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import repository.interfaces.IPackageRepository;

/**
 *
 * @author Madeleine
 */

public class DbPackageRepository implements IPackageRepository {
    
    private EntityManager em;
    private final static Logger log = Logger.getLogger(DbPackageRepository.class);

    public DbPackageRepository() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu_package_sorting");
        em = factory.createEntityManager();
    }

    public DbPackageRepository(EntityManager em) {
        this.em = em;
    }  
    
    public void add(Package object) {
        log.info("add entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(object);
            transaction.commit();
            log.info("Package added");

        } catch (EntityExistsException ex) {
            log.error("Package exists already. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void delete(Package object) {
        log.info("delete entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.remove(object);
            transaction.commit();
            log.info("Package delete");
        } catch (IllegalArgumentException ex) {
            log.error("Package could not be deleted. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public Package getById(long id) {
        log.info("getById entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            Package result = em.find(Package.class, id);
            transaction.commit();
            log.info("Package found");
            return result;
        } catch (IllegalArgumentException ex) {
            log.error("first argument does not denote an entity type or the second argument is not a valid type for that entity’s primary key or is null");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public List<Package> getAll() {
        log.info("getAll entered");
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select pack from Package pack");
            List<Package> result = query.getResultList();
            transaction.commit();
            log.info("List of all packages found");
            return result;

        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public List<Package> getPackagesByRecipient(Recipient recipient) {
        log.info("getPackagesByRecipient entered");
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("select pack from Package pack where pack.recipient = :rec");
            query.setParameter("rec", recipient);
            List<Package> result = query.getResultList();
            transaction.commit();
            log.info("Packages by recipient found");
            return result;

        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

    }

    public List<Package> getPackagesByWarehouseKey(String key) {
        log.info("getPackagesByWarehouseKey entered");
        EntityTransaction transaction = null;

        try {
            transaction = em.getTransaction();
            transaction.begin();
            Query query = em.createQuery("SELECT pack FROM Package pack JOIN pack.warehouse wh where wh.regionKey = :key");
            query.setParameter("key", key);
            List<Package> result = query.getResultList();
            transaction.commit();
            log.info("Packages by warehouse found");
            return result;

        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } catch (IllegalArgumentException ex) {
            log.error("the query string is found to be invalid");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

    public void updatePackage(Package pack) {
        log.info("updatePackage entered");
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(pack);
            transaction.commit();
            log.info("Package updated");
        } catch (IllegalArgumentException ex) {
            log.error("Package could not be updated. Rollback initiated.");
            throw new RepositoryException(ex);
        } catch (IllegalStateException ex) {
            log.error("Could not begin or commit transaction");
            throw new RepositoryException(ex);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }
    }

}
