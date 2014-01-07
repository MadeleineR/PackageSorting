/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package integrationtests;

import entities.DeliveryWarehouse;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
import repository.db.DbWarehouseRepository;
import repository.db.DbWarehouseRepositoryT;

/**
 *
 * @author Madeleine
 */
public class RestIntegration extends TestCase{
    
    private DbWarehouseRepository repo;

    public RestIntegration() {
        repo = new DbWarehouseRepository();
    }

    
    @Override
    public void setUp() {
    }

    @Override
    public void tearDown() {
    }

    public void testRestToDb() {
        
        try {
            // make http connection
            URL url = new URL("http://localhost:8080/PackageSorting/webresources/regionService");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/xml");
            connection.setDoOutput(true);
            
            // write xml output
            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\Madeleine\\Documents\\NetBeansProjects\\RestService\\test\\integrationtests\\test.xml"));
            String currentLine;
            StringBuilder builder = new StringBuilder();
            while ((currentLine = reader.readLine()) != null) {
                builder.append(currentLine);
            }
            output.writeBytes(builder.toString());
            output.flush();
            output.close();
            
            // verify response code
            assertEquals(204, connection.getResponseCode());
            
            // verify db
            List<DeliveryWarehouse> warehouses1 = repo.getWarehouseByKey("R12412");            
            assertEquals(1, warehouses1.size());
            assertEquals("Stephansplatz 1", warehouses1.get(0).getLocation().getStreet());
            assertEquals("1010", warehouses1.get(0).getLocation().getPostalcode());
            assertEquals("Wien", warehouses1.get(0).getLocation().getCity());
            
            List<DeliveryWarehouse> warehouses2 = repo.getWarehouseByKey("R412");
            assertEquals(1, warehouses2.size());
            assertEquals("Hauptplatz 1", warehouses2.get(0).getLocation().getStreet());
            assertEquals("3910", warehouses2.get(0).getLocation().getPostalcode());  
            assertEquals("Zwettl", warehouses2.get(0).getLocation().getCity());  

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (ProtocolException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}