/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package soapshippingservice;

import business_layer.PackageImport;
import entities.Location;
import entities.Recipient;
import javax.jws.WebService;
import org.apache.log4j.Logger;

/**
 *
 * @author Madeleine
 */
@WebService(serviceName = "ShippingService", portName = "BasicHttpBinding_IShippingService", endpointInterface = "org.skspackage._2013.shippingservice.IShippingService", targetNamespace = "http://sksPackage.org/2013/ShippingService", wsdlLocation = "WEB-INF/wsdl/ShippingService.wsdl")
public class ShippingService {

    private final static Logger log = Logger.getLogger(ShippingService.class);

    public void addPackage(org.skspackage.schema._2013.shippingservice.Pack pack) {

        try {
            log.info("soap request received");
            //convert it to entities.Package
            entities.Package p = new entities.Package();
            Location loc = new Location();
            loc.setStreet(pack.getAddress().getValue().getStreet().getValue());
            loc.setPostalcode(pack.getAddress().getValue().getPostalCode().getValue());
            loc.setCity(pack.getAddress().getValue().getCity().getValue());
            loc.setCountry(pack.getAddress().getValue().getCountry().getValue());
            p.setRecipient(new Recipient("", loc));
            p.setDelivered(false);
            
            PackageImport importer = new PackageImport();
            importer.importPackage(p);
            log.info("soap request processed");
        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
            throw new ShippingServiceException(ex);
        }
    }
}
