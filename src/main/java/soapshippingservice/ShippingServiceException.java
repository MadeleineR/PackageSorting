/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soapshippingservice;

/**
 *
 * @author Madeleine
 */
public class ShippingServiceException extends RuntimeException {
    
    public ShippingServiceException(){
        
    }
    
    public ShippingServiceException (String message) {
        super(message);
    }
    
    public ShippingServiceException (Throwable cause) {
        super(cause);
    }
    
}
