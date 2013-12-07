/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soapdeliveryservice;

import business_layer.PackageDelivery;
import entities.Location;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.skspackage.schema._2013.deliveryservice.Address;
import org.skspackage.schema._2013.deliveryservice.ArrayOfPackage;
import org.skspackage.schema._2013.deliveryservice.ObjectFactory;
import entities.Package;

/**
 *
 * @author Madeleine
 */
@WebService(serviceName = "PackageService", portName = "Soap_DeliveryService", endpointInterface = "org.skspackage._2013.deliveryservice.DeliveryService", targetNamespace = "http://sksPackage.org/2013/DeliveryService", wsdlLocation = "WEB-INF/wsdl/deliveryservice.wsdl")
public class DeliveryService {
    
    private final static Logger log = Logger.getLogger(DeliveryService.class);

    public ArrayOfPackage getPackagesForRegion(String regionKey) {

        try {
            log.info("getPackagesForRegion entered");
            PackageDelivery delivery = new PackageDelivery();
            List<Package> packs = delivery.getPackagesForRegion(regionKey);
            
            ArrayOfPackage array = new ArrayOfPackage();
            for (Package p : packs){
             // Package umwandeln + ans Array hängen
                Location loc = p.getRecipient().getLocation();
                org.skspackage.schema._2013.deliveryservice.Package pack = new org.skspackage.schema._2013.deliveryservice.Package();
                ObjectFactory factory = new ObjectFactory();
                Address address = new Address();
                address.setCity(factory.createAddressCity(loc.getCity()));
                address.setCountry(factory.createAddressCountry(loc.getCountry()));
                address.setPostalCode(factory.createAddressPostalCode(loc.getPostalcode()));
                address.setStreet(factory.createAddressStreet(loc.getStreet()));
                pack.setAddress(factory.createAddress(address));
                array.getPackage().add(pack);
            }
            log.info("soap request processed");
            return array;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new DeliveryServiceException(ex);
        }

    }
}
