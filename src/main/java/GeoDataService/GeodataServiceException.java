/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeoDataService;

/**
 *
 * @author Madeleine
 */
public class GeodataServiceException extends RuntimeException {
    
    public GeodataServiceException(){
        
    }
    
    public GeodataServiceException (String message) {
        super(message);
    }
    
    public GeodataServiceException (Throwable cause) {
        super(cause);
    }
    
}
