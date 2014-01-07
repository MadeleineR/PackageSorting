/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import GeoDataService.GoogleGeocodingService;
import entities.DeliveryWarehouse;
import entities.Location;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;
import repository.interfaces.IWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class RegionImport implements IRegionImport<List<DeliveryWarehouse>>{
    
    private final static Logger log = Logger.getLogger(RegionImport.class);
    private IWarehouseRepository whRepo = new DbWarehouseRepository();
    private DbPackageRepository packRepo = new DbPackageRepository();

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
                    whRepo.add(warehouse);
                    log.info("Warehouse imported");
                    // resort all packages
                    PackageAssignment assignment = new PackageAssignment();
                    assignment.reassignAll(packRepo.getAll(), whRepo.getAll());
                    log.info("Packages resorted");
                } else {
                    log.warn("longitude and latitude undefinied");
                }

                for (DeliveryWarehouse wh : whRepo.getAll()) {
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
