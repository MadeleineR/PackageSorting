/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import GeoDataService.GoogleGeocodingService;
import entities.Location;
import entities.Package;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;

/**
 *
 * @author Madeleine
 */
public class PackageBL implements IBusinessLayer<Package> {

    private final static Logger log = Logger.getLogger(PackageBL.class);
    private DbPackageRepository repo = new DbPackageRepository();

    @Override
    public void add(Package object) {
        try {
            log.info("add entered");
            
            // get Coordinates from Google API
            GoogleGeocodingService service = new GoogleGeocodingService();
            Location loc = object.getRecipient().getLocation();
            loc = service.getCoordinates(loc);
            object.getRecipient().setLocation(loc);
            
            // assign to region
            PackageAssignment assignment = new PackageAssignment();
            assignment.assignNewPackage(object);
            
            // add to db
            repo.add(object);
            
            for (Package p : repo.getAll()) {
                log.info(p.getRecipient().getLocation().getCity());
                log.info(p.getRecipient().getLocation().getStreet());
                log.info(p.getWarehouse().getLocation().getCity());
                log.info(p.getWarehouse().getLocation().getStreet());
            }
            
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }

    }

    @Override
    public void delete(Package object) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Package> getAll() {
        try {
            log.info("getAll entered");
            return repo.getAll();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
    }

    @Override
    public Package getById(long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Package> getPackagesByWarehouseKey(String regionKey) {
        try {
            log.info("getPackagesByWarehouse entered");
            return repo.getPackagesByWarehouseKey(regionKey);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
    }

    public void updatePackage(Package pack) {
        try {
            log.info("updatePackage entered.");
            repo.updatePackage(pack);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
    }
}
