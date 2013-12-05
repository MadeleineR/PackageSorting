/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeoDataService;

import entities.Location;

/**
 *
 * @author Madeleine
 */
public interface IGeoDataService {
    
    public Location getCoordinates(Location loc);    
    
}
