/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeoDataService;

import entities.Location;
import junit.framework.TestCase;


/**
 *
 * @author Madeleine
 */
public class GoogleGeocodingServiceT extends TestCase {
    
    public GoogleGeocodingServiceT() {
    }
    

    @Override
    public void setUp() {
    }
    
    @Override
    public void tearDown() {
    }


    public void testGetCoordinates1() {
        System.out.println("getCoordinates");
        Location loc = new Location();
        loc.setStreet("Höchstädtplatz 5");
        loc.setPostalcode("1200");
        loc.setCity("Wien");
        loc.setLatitude(48.2395734);
        loc.setLongitude(16.3777399);
        
        GoogleGeocodingService instance = new GoogleGeocodingService();
        Location expResult = loc;
        Location result = instance.getCoordinates(loc);
        assertEquals(expResult.getLatitude(), result.getLatitude(), 0.00001);
        assertEquals(expResult.getLongitude(), result.getLongitude(), 0.00001);
    }
    

    public void testGetCoordinates2() {
        System.out.println("getCoordinates");
        Location loc = new Location();
        loc.setCity("Tokio");
        loc.setStreet("");
        loc.setPostalcode("");
        loc.setLatitude(35.6894875);
        loc.setLongitude(139.6917064);
        
        GoogleGeocodingService instance = new GoogleGeocodingService();
        Location expResult = loc;
        Location result = instance.getCoordinates(loc);
        assertEquals(expResult.getLatitude(), result.getLatitude(), 0.00001);
        assertEquals(expResult.getLongitude(), result.getLongitude(), 0.00001);
    }
    
    
}