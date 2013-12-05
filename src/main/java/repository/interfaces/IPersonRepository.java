/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.interfaces;

import entities.DeliveryPerson;
import entities.DeliveryWarehouse;
import java.util.List;

/**
 *
 * @author Madeleine
 */
public interface IPersonRepository extends IRepository<DeliveryPerson>{
    
    List<DeliveryPerson> getPersonsByName(String name);
    List<DeliveryPerson> getPersonsByWarehouse(DeliveryWarehouse warehouse);
    
}
