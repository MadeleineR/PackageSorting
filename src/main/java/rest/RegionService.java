/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import business_layer.RegionImport;
import entities.DeliveryWarehouse;
import entities.Location;
import generated.RegionData;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * REST Web Service
 *
 * @author Madeleine
 */
@Path("regionService")
public class RegionService {
    
    private final static Logger log = Logger.getLogger(RegionService.class);

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of RegionService
     */
    public RegionService() {
        // netbeans unable to find relative path
        PropertyConfigurator.configure("C:\\Users\\Madeleine\\Documents\\NetBeansProjects\\PackageSorting\\src\\main\\resources\\log4j.properties");
    }

    /**
     * Retrieves representation of an instance of rest.RegionService
     * @return an instance of java.lang.String
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public void postRegions(String content) {
        try {
            log.info("post request received");
            JAXBContext jaxbContext = JAXBContext.newInstance(RegionData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            
            // read data from xml
            StringReader reader = new StringReader(content);
            RegionData regions = (RegionData) unmarshaller.unmarshal(reader);
            
            List<DeliveryWarehouse> warehouses = new ArrayList<DeliveryWarehouse>();
            for (RegionData.Region region : regions.getRegion()) {
                // convert it to DeliveryWarehouse
                DeliveryWarehouse warehouse = new DeliveryWarehouse();
                warehouse.setRegionKey(region.getKey());
                Location loc = new Location();
                loc.setPostalcode(region.getAddress().getPostalCode());
                loc.setCity(region.getAddress().getCity());
                loc.setStreet(region.getAddress().getStreet());
                warehouse.setLocation(loc);
                warehouses.add(warehouse);
            }

            RegionImport regionImporter = new RegionImport();
            regionImporter.importRegions(warehouses);
            
            log.info("post request processed");

        } catch (JAXBException ex) {
            log.error("retrieving regiondata object from xml failed", ex);
            throw new RestException(ex);
        }

    }
}
