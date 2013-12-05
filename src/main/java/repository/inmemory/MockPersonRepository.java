/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.inmemory;

import repository.interfaces.IPersonRepository;
import entities.DeliveryPerson;
import entities.DeliveryWarehouse;
import java.util.ArrayList;
import java.util.List;
import repository.db.RepositoryException;

/**
 *
 * @author Madeleine
 */
public class MockPersonRepository implements IPersonRepository{
    
    public List<DeliveryPerson> persons;
    
    
    public MockPersonRepository(List<DeliveryPerson> del_persons){
        persons = del_persons;
    }
    

    public List<DeliveryPerson> getPersonsByName(String name) {
        List resultset = new ArrayList<DeliveryPerson>();
        for (DeliveryPerson del_per : persons){
            if(del_per.getName().equals(name)){
                resultset.add(del_per);
            }
        }
        if (!resultset.isEmpty()){
            return resultset;
        }
        else {
            throw new RepositoryException("no persons with name " + name + " found");
        }
        
    }
    
    public void add(DeliveryPerson object) {
        if (!persons.contains(object)){
          persons.add(object);  
        }
        else {
            throw new RepositoryException("person already exists");
        }
        
    }


    public void delete(DeliveryPerson object) {
        if (persons.contains(object)) {
           persons.remove(object); 
        }
        else {
            throw new RepositoryException("Person does not exist, therefore cannot be deleted");
        }
        
    }

    public DeliveryPerson getById(long id) {
        DeliveryPerson pers = null;
        for (DeliveryPerson person : persons){
            if(person.getPerson_id() == id){
                pers = person;
            }
        }
        if (pers != null) {
            return pers;
        }
        else {
            throw new RepositoryException("person not found");
        }
    }

    public List<DeliveryPerson> getAll() {
        if (persons != null && !persons.isEmpty()) {
          return persons;  
        }
        else {
            throw new RepositoryException("no persons exist");
        }
        
    }

    public List<DeliveryPerson> getPersonsByWarehouse(DeliveryWarehouse warehouse) {
        List resultset = new ArrayList<DeliveryPerson>();
        for (DeliveryPerson del_per : persons){
            if(del_per.getDelivery_warehouse().equals(warehouse)){
                resultset.add(del_per);
            }
        }
        
        if (!resultset.isEmpty()) {
            return resultset;
        }
        else {
            throw new RepositoryException("no persons for this warehouse found");
        }
        
    }


    
}
