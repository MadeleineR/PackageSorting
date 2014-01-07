/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.DeliveryWarehouse;
import entities.Package;
import entities.Recipient;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import junit.framework.TestCase;

/**
 *
 * @author Madeleine
 */
public class DbPackageRepositoryT extends TestCase {
    
    private DbPackageRepository repo;
    private EntityManager em;
    private Package packToGet;
    private Package packToDelete;
    private Package packToAdd;
    
    public DbPackageRepositoryT(String testName) {
        super(testName);
        try {
            repo = (DbPackageRepository) new InitialContext().lookup("java:global/PackageSorting/DbPackageRepository");
        } catch (NamingException ex) {
            Logger.getLogger(DbPackageRepository.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        em.getTransaction().begin();
        // Testdaten
        packToAdd = new Package();
        packToAdd.setRecipient(null);
        packToAdd.setWarehouse(null);
        
        packToDelete = new Package();
        packToDelete.setRecipient(null);
        packToDelete.setWarehouse(null);
        em.persist(packToDelete);
        
        packToGet = new Package();
        packToGet.setRecipient(new Recipient());
        DeliveryWarehouse warehouse = new DeliveryWarehouse();
        warehouse.setRegionKey("TEST123");
        em.persist(warehouse);
        packToGet.setWarehouse(warehouse);
        em.persist(packToGet);
        
        em.getTransaction().commit();        
    }
    
    @Override
    protected void tearDown() throws Exception {        
        em.getTransaction().begin();
        em.remove(packToAdd);
        em.remove(packToGet);
        em.remove(packToDelete);
        em.getTransaction().commit();
        super.tearDown();
    }

    public void testAdd() {
        System.out.println("add");
        repo.add(packToAdd);        
        assertEquals(packToAdd, em.find(Package.class, packToAdd.getPackage_ID()));
    }

    public void testDelete() {
        System.out.println("delete");
        repo.delete(packToDelete);
        assertNull(em.find(Package.class, packToDelete.getPackage_ID()));
    }

    public void testGetById() {
        System.out.println("getById");
        Package object = repo.getById(packToGet.getPackage_ID());
        assertEquals(object, em.find(Package.class, packToGet.getPackage_ID()));
    }

    public void testGetAll() {
        System.out.println("getAll");
        em.getTransaction().begin();
        Query query = em.createQuery("select pack from Package pack");
        List<Package> expResult = query.getResultList();
        em.getTransaction().commit();
        List<Package> result = repo.getAll();
        assertEquals(expResult, result);
    }
    
    public void testGetPackagesByRecipient() {
        System.out.println("getPackagesByRecipient");
        em.getTransaction().begin();
        Query query = em.createQuery("select pack from Package pack where pack.recipient = :rec");
        query.setParameter("rec", packToGet.getRecipient());
        List<Package> expResult = query.getResultList();
        em.getTransaction().commit();
        
        List<Package> result = repo.getPackagesByRecipient(packToGet.getRecipient());
        assertEquals(expResult, result);            
    }
    
    public void testGetPackagesByWarehouseKey(){
        System.out.println("getPackagesByWarehouseKey");
        em.getTransaction().begin();
        Query query = em.createQuery("SELECT pack FROM Package pack JOIN pack.warehouse wh where wh.regionKey = :key");
        query.setParameter("key", packToGet.getWarehouse().getRegionKey());
        List<Package> expResult = query.getResultList();
        em.getTransaction().commit();
        
        List<Package> result = repo.getPackagesByWarehouseKey(packToGet.getWarehouse().getRegionKey());
        assertEquals(expResult, result);
    }
    
}
