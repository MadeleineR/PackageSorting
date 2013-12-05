/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

import entities.DeliveryPerson;
import entities.DeliveryWarehouse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import junit.framework.TestCase;

/**
 *
 * @author Madeleine
 */
public class DbPersonRepositoryTest extends TestCase {
    
    private EntityManager em;
    private DbPersonRepository repo;
    private DeliveryPerson personToDelete;
    private DeliveryPerson personToGet;
    private DeliveryPerson personToAdd;
    
    public DbPersonRepositoryTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("pu_package_sorting");
        em = factory.createEntityManager();
        repo = new DbPersonRepository(em);
        
        em.getTransaction().begin();
        personToAdd = new DeliveryPerson();
        personToAdd.setName("Gudrun");
        personToAdd.setDelivery_warehouse(null);
        
        personToDelete = new DeliveryPerson();
        personToDelete.setName("Alex");
        personToDelete.setDelivery_warehouse(null);
        em.persist(personToDelete);
        
        personToGet = new DeliveryPerson();
        personToGet.setName("Madeleine");
        DeliveryWarehouse wh = new DeliveryWarehouse();
        em.persist(wh);
        personToGet.setDelivery_warehouse(wh);
        em.persist(personToGet);
        
        em.getTransaction().commit();
        
    }
    
    @Override
    protected void tearDown() throws Exception {
        em.getTransaction().begin();
        em.remove(personToAdd);
        em.remove(personToDelete);
        em.remove(personToGet);
        em.getTransaction().commit();
        super.tearDown();
    }

    public void testGetPersonsByName() {
        System.out.println("getPersonsByName");
        em.getTransaction().begin();
        Query query = em.createQuery("select per from DeliveryPerson per where per.name = :name");
        query.setParameter("name", personToGet.getName());
        List<DeliveryPerson> expResult = query.getResultList();
        em.getTransaction().commit();
        List<DeliveryPerson> result = repo.getPersonsByName("Madeleine");
        
        assertEquals(expResult, result);
    }

    public void testGetPersonsByWarehouse() {
        System.out.println("getPersonsByWarehouse");
        em.getTransaction().begin();
        Query query = em.createQuery("select per from DeliveryPerson per where per.delivery_warehouse = :wh");
        query.setParameter("wh", personToGet.getDelivery_warehouse());
        List<DeliveryPerson> expResult = query.getResultList();
        em.getTransaction().commit();
        
        List<DeliveryPerson> result = repo.getPersonsByWarehouse(personToGet.getDelivery_warehouse());
        
        assertEquals(expResult, result);
    }

    public void testAdd() {
        System.out.println("add");
        repo.add(personToAdd);
        assertEquals(personToAdd, em.find(DeliveryPerson.class, personToAdd.getPerson_id()));
    }

    public void testDelete() {
        System.out.println("delete");
        repo.delete(personToDelete);
        assertNull(em.find(DeliveryPerson.class, personToDelete.getPerson_id()));
    }

    public void testGetById() {
        System.out.println("getById");
        DeliveryPerson expResult = em.find(DeliveryPerson.class, personToGet.getPerson_id());
        DeliveryPerson result = repo.getById(personToGet.getPerson_id());
        assertEquals(expResult, result);
    }

    public void testGetAll() {
        System.out.println("getAll");
        em.getTransaction().begin();
        Query query = em.createQuery("select pers from DeliveryPerson pers");
        List<DeliveryPerson> expResult = query.getResultList();
        em.getTransaction().commit();
        
        List<DeliveryPerson> result = repo.getAll();
        
        assertEquals(expResult, result);
    }
}
