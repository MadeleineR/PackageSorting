/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.db;

/**
 *
 * @author Madeleine
 */
public class RepositoryException extends RuntimeException {
    
    public RepositoryException(){
        
    }
    
    public RepositoryException(String message) {
        super(message);
    }
    
    public RepositoryException (Throwable cause) {
        super(cause);
    }
    
    
}
