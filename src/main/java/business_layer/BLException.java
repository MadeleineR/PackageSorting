/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

/**
 *
 * @author Madeleine
 */
public class BLException extends RuntimeException {
    
    public BLException(){
        
    }
    
    public BLException(String message) {
        super(message);
    }
    
    public BLException (Throwable cause) {
        super(cause);
    }
    
}
