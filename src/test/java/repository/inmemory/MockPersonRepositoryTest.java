/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.inmemory;

import entities.DeliveryPerson;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Madeleine
 */
public class MockPersonRepositoryTest extends TestCase {
    
    public MockPersonRepositoryTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    
    public void testGetByName() {
        System.out.println("getByName");
        String name = "Franz";
        List del_pers = new ArrayList<DeliveryPerson>();
        del_pers.add(new DeliveryPerson(1, "Franz", null));
        MockPersonRepository instance = new MockPersonRepository(del_pers);
        List expResult = del_pers;
        List result = instance.getPersonsByName(name);
        assertEquals(expResult, result);
    }



    public void testAdd() {
        System.out.println("add");  
        MockPersonRepository instance = new MockPersonRepository(new ArrayList<DeliveryPerson>());        
        DeliveryPerson object = new DeliveryPerson(1, "Franz", null);      
        //Act
        instance.add(object); 
        //Assert
        assertEquals(1, instance.persons.size());
    }



    public void testDelete() {
        System.out.println("delete");        
        List persons = new ArrayList<DeliveryPerson>();
        DeliveryPerson person = new DeliveryPerson(1, "Franz", null);
        persons.add(person);
        MockPersonRepository instance = new MockPersonRepository(persons);
        
        //Act
        instance.delete(person); 
        //Assert
        assertEquals(0, instance.persons.size());
    }


    public void testGetById() {
        System.out.println("getById");
        int id = 1;
        List del_pers = new ArrayList<DeliveryPerson>();
        DeliveryPerson del_per = new DeliveryPerson(1, "Franz", null);
        del_pers.add(del_per);
        MockPersonRepository instance = new MockPersonRepository(del_pers);
        DeliveryPerson expResult = del_per;
        DeliveryPerson result = instance.getById(id);
        assertEquals(expResult, result);
    }


    public void testGetAll() {
        System.out.println("getAll");
        List del_pers = new ArrayList<DeliveryPerson>();
        del_pers.add(new DeliveryPerson(1, "Franz", null));
        MockPersonRepository instance = new MockPersonRepository(del_pers);
        List expResult = del_pers;
        List result = instance.getAll();
        assertEquals(expResult, result);
    }
}
