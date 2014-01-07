/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.DeliveryPerson;
import entities.DeliveryWarehouse;
import entities.Location;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import junit.framework.TestCase;
import entities.Package;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Madeleine
 */
public class DbWarehouseRepositoryT extends TestCase {
    
    private EntityManager em;
    private DbWarehouseRepository repo;
    private DeliveryWarehouse warehouseToAdd;
    private DeliveryWarehouse warehouseToDelete;
    private DeliveryWarehouse warehouseToGet;
    
    public DbWarehouseRepositoryT(String testName) {
        super(testName);        
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        try {
            repo = (DbWarehouseRepository) new InitialContext().lookup("java:global/PackageSorting/DbWarehouseRepository");
        } catch (NamingException ex) {
            Logger.getLogger(DbWarehouseRepositoryT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        em.getTransaction().begin();
        warehouseToAdd = new DeliveryWarehouse();
        warehouseToAdd.setLocation(null);
        warehouseToAdd.setPackages(null);
        warehouseToAdd.setPersons(null);
        
        warehouseToDelete = new DeliveryWarehouse();
        warehouseToDelete.setLocation(null);
        warehouseToDelete.setPackages(null);
        warehouseToDelete.setPersons(null);
        em.persist(warehouseToDelete);
        
        warehouseToGet = new DeliveryWarehouse();
        warehouseToGet.setLocation(new Location());
        warehouseToGet.setPackages(new ArrayList<Package>());
        warehouseToGet.setPersons(new ArrayList<DeliveryPerson>());
        em.persist(warehouseToGet);
        
        em.getTransaction().commit();
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        em.getTransaction().begin();
        em.remove(warehouseToAdd);
        em.remove(warehouseToDelete);
        em.remove(warehouseToGet);
        em.getTransaction().commit();
        super.tearDown();
    }

    public void testAdd() {
        System.out.println("add");
        repo.add(warehouseToAdd);
        assertEquals(warehouseToAdd, em.find(DeliveryWarehouse.class, warehouseToAdd.getDelivery_warehouse_id()));
    }

    public void testDelete() {
        System.out.println("delete");
        repo.delete(warehouseToDelete);
        assertNull(em.find(DeliveryWarehouse.class, warehouseToDelete.getDelivery_warehouse_id()));
    }

    public void testGetById() {
        System.out.println("getById");
        DeliveryWarehouse expResult = em.find(DeliveryWarehouse.class, warehouseToGet.getDelivery_warehouse_id());
        DeliveryWarehouse result = repo.getById(warehouseToGet.getDelivery_warehouse_id());
        assertEquals(expResult, result);
    }

    public void testGetAll() {
        System.out.println("getAll");
        em.getTransaction().begin();
        Query query = em.createQuery("select wh from DeliveryWarehouse wh");
        List<DeliveryWarehouse> expResult = query.getResultList();
        em.getTransaction().commit();
        List<DeliveryWarehouse> result = repo.getAll();
        
        assertEquals(expResult, result);
    }

    public void testGetWarehouseByLocation() {
        System.out.println("getWarehouseByLocation");
        em.getTransaction().begin();
        Query query = em.createQuery("select wh from DeliveryWarehouse wh where wh.location = :loc");
        query.setParameter("loc", warehouseToGet.getLocation());
        List<DeliveryWarehouse> expResult = query.getResultList();
        em.getTransaction().commit();
        
        List<DeliveryWarehouse> result = repo.getWarehouseByLocation(warehouseToGet.getLocation());
        assertEquals(expResult, result);
    }
}
