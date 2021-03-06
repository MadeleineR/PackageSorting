/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import entities.DeliveryWarehouse;
import entities.Package;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;
import repository.db.DbWarehouseRepository;

/**
 *
 * @author Madeleine
 */
@Named(value="packAssign")
@ApplicationScoped
public class PackageAssignment implements IPackageAssignment {

    private final static Logger log = Logger.getLogger(PackageAssignment.class);
    
    /*
     * Get all warehouses from db and assign new package to nearest warehouse
     */
    @Override
    public void assignNewPackage(Package pack, List<DeliveryWarehouse> warehouses) {
        log.info("assignNewPackage entered");
        DeliveryWarehouse closestWarehouse = new DeliveryWarehouse();
        double closestDistance = 1000000;		// setting high value to assure replacement ;-)
        double currentDistance = 1000000;		// will be calculated anyway ;-)
        try {
            if (warehouses != null && !warehouses.isEmpty()) {		// if-else to be sure there is >= warehouse on the list

                for (DeliveryWarehouse currentWarehouse : warehouses) {			// go through list of warehouses
                    currentDistance = Math.pow((pack.getRecipient().getLocation().getLatitude() - currentWarehouse.getLocation().getLatitude()), 2) + Math.pow((pack.getRecipient().getLocation().getLongitude() - currentWarehouse.getLocation().getLongitude()), 2);	// calculate distance of given pack to current warehouse
                    if (currentDistance < closestDistance) {		// found closer warehouse
                        log.info("closer warehouse found");
                        closestDistance = currentDistance;		// update closestDistance for further search
                        closestWarehouse = currentWarehouse;		// update closestWarehouse 
                    }							// no need for else because in false case closestWarehouse stays the same 
                }
                pack.setWarehouse(closestWarehouse);		// assign the closest warehouse to the given pack
            } else {
                log.warn("List of Warehouses empty.");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
    }

    /*
     * Get all packages and warehouses from database and assign them properly
     */
    @Override
    public void reassignAll(List<Package> packages, List<DeliveryWarehouse> warehouses) {
        log.info("reassignAll entered");
        //Package currentPack = new Package();
        try {
            if (packages != null && !packages.isEmpty()) {
                for (entities.Package currentPack : packages) {				// go through list of packages
                    // assign to region
                    PackageAssignment assignment = new PackageAssignment();	// ### Madeleine: passt das so mit dem Konstruktor?
                    // ### ich hab das aus dem aktuellen aufruf der methode innerhalb des "add"
                    // ### stiebitzt
                    assignment.assignNewPackage(currentPack, warehouses);		// every single package is being assigned closest warehouse
                }
            } else {
                log.warn("No packages found.");
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }

    }
}
