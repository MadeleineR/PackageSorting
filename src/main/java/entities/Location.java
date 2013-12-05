/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Embeddable;

/**
 *
 * @author Madeleine
 */

@Embeddable
public class Location {
    

    private String street;
    private String postalcode;
    private String city;
    private String country;
    private String irrelevant;
    private double longitude;
    private double latitude;

    public Location() {
        this.longitude = 1000;
        this.latitude = 1000;
    }
    
    public Location(String street, String postcode, String city, String country, String irrelevant){
        this.street = street;
        this.postalcode = postcode;
        this.city = city;
        this.country = country;
        this.irrelevant = irrelevant;
        this.longitude = 1000;
        this.latitude = 1000;
    }
    
    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }


    public String getIrrelevant() {
        return irrelevant;
    }

    public void setIrrelevant(String irrelevant) {
        this.irrelevant = irrelevant;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    
    
    
}
