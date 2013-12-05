/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GeoDataService;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Madeleine
 */
class SaxHandler extends DefaultHandler {
    
    private boolean latTag = false;
    private boolean lngTag = false;
    private double latitude = 1000;
    private double longitude = 1000;
    private boolean firstLatTag = true;
    private boolean firstLngTag = true;
    
    
    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
    
    
    public void startDocument() throws SAXException {
    }

    public void endDocument() throws SAXException {
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        
        if (qName.equalsIgnoreCase("lat") && firstLatTag){
            latTag = true;
            firstLatTag = false;
        }
        
        if (qName.equalsIgnoreCase("lng") && firstLngTag){
            lngTag = true;
            firstLngTag = false; 
        }

    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
    }

    public void characters(char ch[], int start, int length) throws SAXException {
        
        if (latTag) {
            String lat = new String(ch, start, length);
            latitude = Double.parseDouble(lat);
            latTag = false;
        }
        
        if (lngTag) {
            String lng = new String(ch, start, length);
            longitude = Double.parseDouble(lng);
            lngTag = false;
        }
    }

    public void ignorableWhitespace(char ch[], int start, int length) throws SAXException {
    }
    
}
