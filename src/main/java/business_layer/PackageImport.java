/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import GeoDataService.GoogleGeocodingService;
import entities.Location;
import org.apache.log4j.Logger;
import entities.Package;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;
import soapshippingservice.ShippingServiceException;

/**
 *
 * @author Madeleine
 */

public class PackageImport implements IPackageImport<Package> {

    private final static Logger log = Logger.getLogger(PackageImport.class);
    private DbPackageRepository packRepo = new DbPackageRepository();
    private DbWarehouseRepository whRepo = new DbWarehouseRepository();

    public PackageImport() {
    }

    @Override
    public void importPackage(Package pack) {
        try {
            log.info("importPackage entered");
            
            // get coordinates from Google API
            GoogleGeocodingService service = new GoogleGeocodingService();;
            Location loc = service.getCoordinates(pack.getRecipient().getLocation());
            pack.getRecipient().setLocation(loc);

            // assign to region
            PackageAssignment assignment = new PackageAssignment();
            assignment.assignNewPackage(pack, whRepo.getAll());

            // add to db
            packRepo.add(pack);

            for (entities.Package p : packRepo.getAll()) {
                log.info(p.getRecipient().getLocation().getCity());
                log.info(p.getRecipient().getLocation().getStreet());
                log.info(p.getWarehouse().getLocation().getCity());
                log.info(p.getWarehouse().getLocation().getStreet());
            }

        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
            throw new ShippingServiceException(ex);
        }
    }
}
