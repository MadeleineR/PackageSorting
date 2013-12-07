/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import GeoDataService.GoogleGeocodingService;
import entities.DeliveryWarehouse;
import entities.Location;
import generated.RegionData;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbWarehouseRepository;
import repository.interfaces.IWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class RegionImport implements IRegionImport<List<DeliveryWarehouse>>{
    
    private final static Logger log = Logger.getLogger(RegionImport.class);
    private IWarehouseRepository repo = new DbWarehouseRepository();

    @Override
    public void importRegions(List<DeliveryWarehouse> warehouses) {
        try {
            log.info("importRegions entered");
            for (DeliveryWarehouse warehouse : warehouses) {
                //load coordinates from google service
                GoogleGeocodingService service = new GoogleGeocodingService();
                Location loc = service.getCoordinates(warehouse.getLocation());
                warehouse.setLocation(loc);

                if (loc.getLatitude() != 1000 && loc.getLongitude() != 1000) {  // check if coordinates were set
                    // add to db
                    repo.add(warehouse);
                    log.info("Warehouse imported");
                    // resort all packages
                    PackageAssignment assignment = new PackageAssignment();
                    assignment.reassignAll();
                    log.info("Packages resorted");
                } else {
                    log.warn("longitude and latitude undefinied");
                }

                for (DeliveryWarehouse wh : repo.getAll()) {
                    log.info(wh.getRegionKey());
                    log.info(wh.getLocation().getStreet());
                    log.info(wh.getLocation().getPostalcode());
                    log.info(wh.getLocation().getCity());
                    log.info(wh.getLocation().getLongitude());
                    log.info(wh.getLocation().getLatitude());
                }
            }
        } catch (NullPointerException ex) {
            log.error("importing warehouse failed");
            throw new BLException(ex);
        }
    }
    
}
