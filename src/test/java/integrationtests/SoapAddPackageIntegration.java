/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtests;

import java.util.List;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import repository.db.DbPackageRepository;
import repository.db.DbPackageRepositoryT;

/**
 *
 * @author Madeleine
 */
public class SoapAddPackageIntegration extends TestCase {
    
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(SoapAddPackageIntegration.class);
    private DbPackageRepository repo = new DbPackageRepository();

    public SoapAddPackageIntegration(String testName) {
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

    public void testAddPackageIntegration() {
        try {
            // Create SOAP Connection
            SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            //Send SOAP Message to SOAP Server
            String url = "http://localhost:8080/PackageSorting/ShippingService?wsdl";
            SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
            soapConnection.close();
                        
            List<entities.Package> packages = repo.getAll();
            assertEquals(1, packages.size());
            assertEquals("Wien", packages.get(0).getRecipient().getLocation().getCity());
            assertEquals("Kärntner Straße 1", packages.get(0).getRecipient().getLocation().getStreet());
            assertEquals("1010", packages.get(0).getRecipient().getLocation().getPostalcode());
            assertEquals("Österreich", packages.get(0).getRecipient().getLocation().getCountry());
            
            //remove data
            for (entities.Package p : packages) {
                repo.delete(p);
            }
            
        } catch (SOAPException ex) {
            log.error(ex.getMessage());
        } catch (UnsupportedOperationException ex) {
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
         <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:ship="http://sksPackage.org/2013/ShippingService" xmlns:ship1="http://schema.sksPackage.org/2013/ShippingService">
            <soapenv:Header/>
            <soapenv:Body>
               <ship:AddPackage>
                  <!--Optional:-->
                  <ship:pack>
                     <!--Optional:-->
                     <ship1:Address>
                        <!--Optional:-->
                        <ship1:City>Wien</ship1:City>
                        <!--Optional:-->
                        <ship1:Country>Österreich</ship1:Country>
                        <!--Optional:-->
                        <ship1:Id>1</ship1:Id>
                        <!--Optional:-->
                        <ship1:PostalCode>1010</ship1:PostalCode>
                        <!--Optional:-->
                        <ship1:Street>Kärntner Straße 1</ship1:Street>
                     </ship1:Address>
                     <!--Optional:-->
                     <ship1:Id>1</ship1:Id>
                  </ship:pack>
               </ship:AddPackage>
            </soapenv:Body>
         </soapenv:Envelope>
         */

        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ship", "http://sksPackage.org/2013/ShippingService");
        envelope.addNamespaceDeclaration("ship1", "http://schema.sksPackage.org/2013/ShippingService");

        // SOAP Body
        SOAPBody soapBody = envelope.getBody();
        SOAPElement addpackage = soapBody.addChildElement("AddPackage", "ship");
        SOAPElement pack = addpackage.addChildElement("pack", "ship");
        SOAPElement address = pack.addChildElement("Address", "ship1");
        SOAPElement city = address.addChildElement("City", "ship1");
        city.addTextNode("Wien");
        SOAPElement country = address.addChildElement("Country", "ship1");
        country.addTextNode("Österreich");
        SOAPElement id = address.addChildElement("Id", "ship1");
        id.addTextNode("1");
        SOAPElement postcode = address.addChildElement("PostalCode", "ship1");
        postcode.addTextNode("1010");
        SOAPElement street = address.addChildElement("Street", "ship1");
        street.addTextNode("Kärntner Straße 1");

        soapMessage.saveChanges();
        return soapMessage;
    }
}
