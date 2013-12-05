/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 *
 * @author Madeleine
 */
public class DeliveryCentre {
    
    private List<DeliveryWarehouse> delivery_warehouses;
    private List<Package> packs;
    
    public DeliveryCentre(){
        
    }

    public List<DeliveryWarehouse> getDelivery_warehouses() {
        return delivery_warehouses;
    }
    
    public void addDeliveryWarehouse(DeliveryWarehouse warehouse){
        this.delivery_warehouses.add(warehouse);
    }
    
    public void addPackage(Package pack){
        this.packs.add(pack);
    }
    
    public List<Package> getPackages() {
        return packs;
    }
    
    public void sortPackages(){
        throw new NotImplementedException();
        
    }
    
    
    
}
