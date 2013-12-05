/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.interfaces;

import java.util.List;

/**
 *
 * @author Madeleine
 */
public interface IRepository<T> {
    
    void add(T object);  
    void delete(T object); 
    T getById(long id);
    List<T> getAll();
    
}
