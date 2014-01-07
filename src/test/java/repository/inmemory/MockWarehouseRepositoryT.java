/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.inmemory;

import entities.DeliveryWarehouse;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Madeleine
 */
public class MockWarehouseRepositoryT extends TestCase {
    
    public MockWarehouseRepositoryT(String testName) {
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

    public void testAdd() {
        System.out.println("add");        
        MockWarehouseRepository instance = new MockWarehouseRepository(new ArrayList<DeliveryWarehouse>());
        DeliveryWarehouse warehouse = new DeliveryWarehouse(1, null);
        //Act
        instance.add(warehouse);
        //Assert
        assertEquals(1, instance.warehouses.size());
    }

    public void testDelete() {
        System.out.println("delete");
        List warehouses = new ArrayList<DeliveryWarehouse>();
        DeliveryWarehouse warehouse = new DeliveryWarehouse(1, null);
        warehouses.add(warehouse);
        MockWarehouseRepository instance = new MockWarehouseRepository(warehouses);
        //Act
        instance.delete(warehouse); 
        //Assert
        assertEquals(0, instance.warehouses.size());
    }

    public void testGetById() {
        System.out.println("getById");
        int id = 1;
        List del_pers = new ArrayList<DeliveryWarehouse>();
        DeliveryWarehouse del_per = new DeliveryWarehouse(1, null);
        del_pers.add(del_per);
        MockWarehouseRepository instance = new MockWarehouseRepository(del_pers);
        DeliveryWarehouse expResult = del_per;
        DeliveryWarehouse result = instance.getById(id);
        assertEquals(expResult, result);
    }

    public void testGetAll() {
        System.out.println("getAll");
        List<DeliveryWarehouse> warehouses = new ArrayList<DeliveryWarehouse>();
        warehouses.add(new DeliveryWarehouse(1, null));
        MockWarehouseRepository instance = new MockWarehouseRepository(warehouses);
        List expResult = warehouses;
        List result = instance.getAll();
        assertEquals(expResult, result);
    }
}
