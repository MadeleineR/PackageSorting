/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtests;

import entities.DeliveryWarehouse;
import entities.Location;
import entities.Recipient;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import org.w3c.dom.NodeList;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class SoapGetPackagesIntegration extends TestCase {

    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SoapGetPackagesIntegration.class);
    private DbPackageRepository packRepo = new DbPackageRepository();
    private DbWarehouseRepository whRepo = new DbWarehouseRepository();

    public SoapGetPackagesIntegration(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetPackagesIntegration() {
        try {
            //prepare data
            DeliveryWarehouse warehouse = new DeliveryWarehouse();
            warehouse.setRegionKey("R412");
            whRepo.add(warehouse);
            entities.Package p = new entities.Package();
            Location loc = new Location();
            loc.setStreet("Stephansplatz 1");
            loc.setPostalcode("1010");
            loc.setCity("Wien");
            loc.setCountry("Österreich");
            p.setRecipient(new Recipient("unknown", loc));
            p.setWarehouse(warehouse);
            packRepo.add(p);
            
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            //Send SOAP Message to SOAP Server
            String url = "http://localhost:8080/PackageSorting/PackageService?wsdl";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);


            NodeList packages = soapResponse.getSOAPPart().getElementsByTagName("Package");
            
            assertEquals(1, packages.getLength());
            NodeList address = packages.item(0).getFirstChild().getChildNodes();
            assertEquals(loc.getCity(), address.item(0).getTextContent());
            assertEquals(loc.getCountry(), address.item(1).getTextContent());
            assertEquals(loc.getPostalcode(), address.item(2).getTextContent());
            assertEquals(loc.getStreet(), address.item(3).getTextContent());
            soapConnection.close();
            
            // remove data
            packRepo.delete(p);
            whRepo.delete(warehouse);
            
        } catch (SOAPException ex) {
            log.error(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }

    private static SOAPMessage createSOAPRequest() throws Exception {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();

        /*
         Construct SOAP Request Message:
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:del="http://sksPackage.org/2013/DeliveryService">
         <soapenv:Header/>
         <soapenv:Body>
         <del:GetPackagesForRegion>
         <!--Optional:-->
         <del:regionKey>R412</del:regionKey>
         </del:GetPackagesForRegion>
         </soapenv:Body>
         </soapenv:Envelope>
         */

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("del", "http://sksPackage.org/2013/DeliveryService");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement getpackages = soapBody.addChildElement("GetPackagesForRegion", "del");
        SOAPElement regkey = getpackages.addChildElement("regionKey", "del");
        regkey.addTextNode("R412");

        soapMessage.saveChanges();
        return soapMessage;
    }
}
