/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import java.util.List;

/**
 *
 * @author Madeleine
 */
public interface IBusinessLayer<T> {
    
    void add(T object);
    void delete(T object);
    List<T> getAll();
    T getById(long id);
    
}
