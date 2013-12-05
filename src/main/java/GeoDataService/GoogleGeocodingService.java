/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeoDataService;

import entities.Location;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author Madeleine
 */
public class GoogleGeocodingService implements IGeoDataService {

    private final static Logger log = Logger.getLogger(GoogleGeocodingService.class);

    @Override
    public Location getCoordinates(Location loc) {

        try {
            String street = URLEncoder.encode(loc.getStreet(), "UTF-8");
            String postalcode = URLEncoder.encode(loc.getPostalcode(), "UTF-8");
            String city = URLEncoder.encode(loc.getCity(), "UTF-8");
            
            URL url = new URL("https://maps.googleapis.com/maps/api/geocode/xml?address=" + street + postalcode + city + "&sensor=false");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");
            InputStream response = connection.getInputStream();

            try {
                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();
                SaxHandler handler = new SaxHandler();
                saxParser.parse(response, handler);

                loc.setLatitude(handler.getLatitude());
                loc.setLongitude(handler.getLongitude());

            } catch (SAXException ex) {
                log.error("Parsing Geodata failed. " + ex.getMessage());
                throw new GeodataServiceException(ex);
            } catch (ParserConfigurationException ex) {
                log.error("Parsing Geodata failed. " + ex.getMessage());
                throw new GeodataServiceException(ex);
            }

            connection.disconnect();
            return loc;

        } catch (IOException ex) {
            log.error("Error while connecting to geodata server ");
            throw new GeodataServiceException(ex);
        }

    }
}
