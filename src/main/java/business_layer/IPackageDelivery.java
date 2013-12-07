/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

/**
 *
 * @author Madeleine
 */
interface IPackageDelivery<T> {
    
    T getPackagesForRegion(String key);
    
}
