/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soapdeliveryservice;

import business_layer.PackageBL;
import entities.Location;
import java.util.List;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import org.skspackage.schema._2013.deliveryservice.Address;
import org.skspackage.schema._2013.deliveryservice.ArrayOfPackage;
import org.skspackage.schema._2013.deliveryservice.Package;
import org.skspackage.schema._2013.deliveryservice.ObjectFactory;

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
            PackageBL pbl = new PackageBL();
            List<entities.Package> plist = pbl.getPackagesByWarehouseKey(regionKey);
            ArrayOfPackage array = new ArrayOfPackage();

            for (entities.Package p : plist) {
                // Package umwandeln + ans Array hängen
                Location loc = p.getRecipient().getLocation();
                Package pack = new Package();
                ObjectFactory factory = new ObjectFactory();
                Address address = new Address();
                address.setCity(factory.createAddressCity(loc.getCity()));
                address.setCountry(factory.createAddressCountry(loc.getCountry()));
                address.setPostalCode(factory.createAddressPostalCode(loc.getPostalcode()));
                address.setStreet(factory.createAddressStreet(loc.getStreet()));
                pack.setAddress(factory.createAddress(address));
                array.getPackage().add(pack);                
                // Package als ausgeliefert markieren
                p.setDelivered(true);
                pbl.updatePackage(p);
            }
            log.info("soap request processed");
            return array;
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new DeliveryServiceException(ex);
        }

    }
}
