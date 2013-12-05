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
public class WarehouseBL implements IBusinessLayer<RegionData> {

    private IWarehouseRepository repo;
    private final static Logger log = Logger.getLogger(WarehouseBL.class);

    public WarehouseBL() {
        repo = new DbWarehouseRepository();
    }

    public WarehouseBL(IWarehouseRepository repo) {
        this.repo = repo;
    }

    public void add(RegionData regions) {

        try {
            log.info("bl add entered");
            for (RegionData.Region region : regions.getRegion()) {
                DeliveryWarehouse warehouse = new DeliveryWarehouse();
                warehouse.setRegionKey(region.getKey());
                Location loc = new Location();
                loc.setPostalcode(region.getAddress().getPostalCode());
                loc.setCity(region.getAddress().getCity());
                loc.setStreet(region.getAddress().getStreet());
                GoogleGeocodingService service = new GoogleGeocodingService();
                loc = service.getCoordinates(loc);
                warehouse.setLocation(loc);

                if (loc.getLatitude() != 1000 && loc.getLongitude() != 1000) {
                    // add to db
                    repo.add(warehouse);
                    log.info("Warehouse added");
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
            log.error("add warehouse failed");
            throw new BLException(ex);
        }
    }

    public void delete(RegionData object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<RegionData> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public RegionData getById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<DeliveryWarehouse> getByKey(String key) {
        try {
            log.info("getByKey entered");
            return repo.getWarehouseByKey(key);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
    }
}
