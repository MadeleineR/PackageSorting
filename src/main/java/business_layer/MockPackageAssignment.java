/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import entities.DeliveryWarehouse;
import entities.Package;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;

/**
 *
 * @author Madeleine
 */
public class MockPackageAssignment implements IPackageAssignment {

    private String key;
    private final static Logger log = Logger.getLogger(MockPackageAssignment.class);
    private DbWarehouseRepository whRepo = new DbWarehouseRepository();
    private DbPackageRepository packRepo = new DbPackageRepository();

    public MockPackageAssignment(String regionKey) {
        this.key = regionKey;
    }
    
    /*
     * Get all packages and warehouses from database and assign them (MOCK!)
     */
    public void reassignAll(List<Package> packages, List<DeliveryWarehouse> warehouses) {
        try {
            log.info("reassignAll entered");
            for (Package p : packRepo.getAll()) {
                if (warehouses != null && !warehouses.isEmpty()) {
                    p.setWarehouse(warehouses.get(0));
                    packRepo.updatePackage(p);
                } else {
                    log.warn("Warehouse with region key " + key + " does not exist.");
                }
            }
            log.info("all packages assigned");
        } catch (NullPointerException ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }

    }

    /*
     * Get all warehouses from db and assign new package to warehouse (MOCK!)
     */
    public void assignNewPackage(Package pack, List<DeliveryWarehouse> warehouses) {
        try {
            if (warehouses != null && !warehouses.isEmpty()) {
                pack.setWarehouse(warehouses.get(0));
            } else {
                log.warn("Warehouse with region key " + key + " does not exist.");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }

    }
}
