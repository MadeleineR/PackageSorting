/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.interfaces;

import entities.DeliveryWarehouse;
import repository.interfaces.IRepository;
import entities.Package;
import entities.Recipient;
import java.util.List;

/**
 *
 * @author Madeleine
 */
public interface IPackageRepository  extends IRepository<Package>{
    
    List<Package> getPackagesByRecipient(Recipient recipient);
    List<Package> getPackagesByWarehouseKey(String key);
    
    
}
