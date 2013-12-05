/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.inmemory;

import repository.interfaces.IWarehouseRepository;
import entities.DeliveryWarehouse;
import entities.Location;
import java.util.ArrayList;
import java.util.List;
import repository.db.RepositoryException;

/**
 *
 * @author Madeleine
 */
public class MockWarehouseRepository implements IWarehouseRepository{
    
    public List<DeliveryWarehouse> warehouses;
    
    public MockWarehouseRepository(List warehouses){
        this.warehouses = warehouses;
    }
    
    public void add(DeliveryWarehouse object) {
        if (!warehouses.contains(object)){
         warehouses.add(object);   
        }
        else {
            throw new RepositoryException("Warehouse already exists.");
        }
        
    }


    public void delete(DeliveryWarehouse object) {
        if (warehouses.contains(object)) {
            warehouses.remove(object);
        }
        else {
            throw new RepositoryException("Warehouse does not exist, therefore cannot be deleted");
        }
        
    }

    public DeliveryWarehouse getById(long id) {
        DeliveryWarehouse wh = null;
        for (DeliveryWarehouse warehouse : warehouses){
            if(warehouse.getDelivery_warehouse_id() == id){
                wh = warehouse;
            }
        }
        if (wh != null) {
            return wh;
        }
        else {
            throw new RepositoryException("Warehouse not found");
        }
    }

    public List<DeliveryWarehouse> getAll() {
        if (warehouses != null && !warehouses.isEmpty()){
            return warehouses;
        }
        else {
            throw new RepositoryException("No warehouses exist");
        }
        
    }

    public List<DeliveryWarehouse> getWarehouseByLocation(Location location) {
        
        List<DeliveryWarehouse> wh = new ArrayList<DeliveryWarehouse>();
        for (DeliveryWarehouse warehouse : warehouses){
            if(warehouse.getLocation().equals(location)){
                wh.add(warehouse);
            }
        }
        
        if (!wh.isEmpty()) {
            return wh;
        }
        else {
            throw new RepositoryException("No warehouses for this location found");
        }        
    }

    @Override
    public List<DeliveryWarehouse> getWarehouseByKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
