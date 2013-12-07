/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package business_layer;

import entities.Package;
import java.util.List;
import org.apache.log4j.Logger;
import repository.db.DbPackageRepository;

/**
 *
 * @author Madeleine
 */
public class PackageDelivery implements IPackageDelivery<List<Package>> {
    
    private final static Logger log = Logger.getLogger(PackageDelivery.class);
    private DbPackageRepository repo = new DbPackageRepository();

    @Override
    public List<Package> getPackagesForRegion(String key) {
        
        try {
            log.info("getPackagesForRegion entered");
            List<entities.Package> plist = repo.getPackagesByWarehouseKey(key);

            for (entities.Package p : plist) {                               
                // Package als ausgeliefert markieren
                p.setDelivered(true);
                repo.updatePackage(p);
            }
                
            return plist;
            
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BLException(ex);
        }
        
    }
    
}
