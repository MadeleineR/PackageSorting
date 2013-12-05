/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import org.apache.log4j.Logger;

/**
 *
 * @author Madeleine
 */
public class Haversine {

    public static final double R = 6372.8; // In kilometers
    private final static Logger log = Logger.getLogger(Haversine.class);

    public double haversine(double lat1, double lon1, double lat2, double lon2) {
        try {
            double dLat = Math.toRadians(lat2 - lat1);
            double dLon = Math.toRadians(lon2 - lon1);
            lat1 = Math.toRadians(lat1);
            lat2 = Math.toRadians(lat2);
            double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
            double c = 2 * Math.asin(Math.sqrt(a));
            return R * c;
        } catch (Exception ex) {
            log.error("Error while computing distance.");
            throw new BLException(ex);
        }
    }
}
