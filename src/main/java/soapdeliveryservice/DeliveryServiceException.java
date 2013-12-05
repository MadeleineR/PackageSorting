/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soapdeliveryservice;


/**
 *
 * @author Madeleine
 */
public class DeliveryServiceException extends RuntimeException {
    
    public DeliveryServiceException(){
        
    }
    
    public DeliveryServiceException (String message) {
        super(message);
    }
    
    public DeliveryServiceException (Throwable cause) {
        super(cause);
    }
    
}
