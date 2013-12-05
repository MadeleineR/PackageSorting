/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

/**
 *
 * @author Madeleine
 */
public class RestException extends RuntimeException {
    
    public RestException(){
        
    }
    
    public RestException(String message) {
        super(message);
    }
    
    public RestException (Throwable cause) {
        super(cause);
    }
    
}
