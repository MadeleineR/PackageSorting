/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import entities.DeliveryWarehouse;
import entities.Location;
import entities.Package;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class PackageAssignment implements IPackageAssignment {

    private DbWarehouseRepository whRepo = new DbWarehouseRepository();
    private DbPackageRepository packRepo = new DbPackageRepository();
    private final static Logger log = Logger.getLogger(PackageAssignment.class);

    /*
     * Get all warehouses from db and assign new package to nearest warehouse
     */
    public void assignNewPackage(Package pack) {
        try {
            Double distance = -1.0;
            DeliveryWarehouse nearest = null;
            List<DeliveryWarehouse> warehouses = whRepo.getAll();
            Location packaddress = pack.getRecipient().getLocation();
            Haversine hav = new Haversine();

            for (DeliveryWarehouse wh : warehouses) {
                Double temp = hav.haversine(packaddress.getLatitude(), packaddress.getLongitude(),
                        wh.getLocation().getLatitude(), wh.getLocation().getLongitude());
                if (temp < distance || distance == -1.0) {
                    distance = temp;
                    nearest = wh;
                }
                log.info(distance);
                pack.setWarehouse(nearest);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }


    }

    /*
     * Get all packages and warehouses from database and assign them properly
     */
    public void reassignAll() {
        try {            
            // get packages and warehouses from db
            List<DeliveryWarehouse> warehouses = whRepo.getAll();
            List<Package> packages = packRepo.getAll();

            Double distance = -1.0;
            DeliveryWarehouse nearest = null;
            Haversine hav = new Haversine();
            
            // assign each package to nearest warehouse
            for (Package pack : packages) {
                Location packaddress = pack.getRecipient().getLocation();

                for (DeliveryWarehouse wh : warehouses) {
                    Double temp = hav.haversine(packaddress.getLatitude(), packaddress.getLongitude(),
                            wh.getLocation().getLatitude(), wh.getLocation().getLongitude());
                    if (temp < distance || distance == -1.0) {
                        distance = temp;
                        nearest = wh;
                    }
                    pack.setWarehouse(nearest);
                }
            }

        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }

    }
}
