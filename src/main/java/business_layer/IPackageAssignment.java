/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import entities.DeliveryWarehouse;
import java.util.List;


/**
 *
 * @author Madeleine
 */
public interface IPackageAssignment {
    
    public void assignNewPackage(entities.Package pack, List<DeliveryWarehouse> warehouses);
    public void reassignAll(List<entities.Package> packs, List<DeliveryWarehouse> warehouses);
    
}
