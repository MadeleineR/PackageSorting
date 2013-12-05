/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.interfaces;

import entities.DeliveryWarehouse;
import entities.Location;
import java.util.List;

/**
 *
 * @author Madeleine
 */
public interface IWarehouseRepository extends IRepository<DeliveryWarehouse> {
    
    List<DeliveryWarehouse> getWarehouseByLocation(Location location);
    List<DeliveryWarehouse> getWarehouseByKey(String key);
    
}
