/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.inmemory;

import repository.interfaces.IPackageRepository;
import entities.Package;
import entities.Recipient;
import java.util.ArrayList;
import java.util.List;
import repository.db.RepositoryException;

/**
 *
 * @author Madeleine
 */
public class MockPackageRepository implements IPackageRepository {

    public List<Package> packages;

    public MockPackageRepository(List<Package> packs) {
        this.packages = packs;
    }

    public void add(Package object) {
        if (!packages.contains(object)) {
            packages.add(object);
        } else {
            throw new RepositoryException("package already exists");
        }

    }

    public void delete(Package object) {
        if (packages.contains(object)) {
            packages.remove(object);
        } else {
            throw new RepositoryException("package does not exist, therefore cannot be deleted");
        }

    }

    public Package getById(long id) {
        Package p = null;
        for (Package pack : packages) {
            if (pack.getPackage_ID() == id) {
                p = pack;
            }
        }
        if (p != null){
            return p;
        } else {
            throw new RepositoryException("Package not found");
        }
    }

    public List<Package> getAll() {
        if (packages != null){
           return packages; 
        }
        else {
            throw new RepositoryException("no packages exists");
        }
        
    }

    public List<Package> getPackagesByRecipient(Recipient recipient) {
        List<Package> packs = new ArrayList<Package>();
        for (Package pack : packs) {
            if (pack.getRecipient().equals(recipient)) {
                packs.add(pack);
            }
        }
        
        if (!packs.isEmpty()){
          return packs;  
        }
        else {
            throw new RepositoryException("no packages found");
        }        
    }

    public List<Package> getPackagesByWarehouseKey(String regionKey) {
        List<Package> packs = new ArrayList<Package>();
        for (Package pack : packs) {
            if (pack.getWarehouse().getRegionKey().equals(regionKey)) {
                packs.add(pack);
            }
        }
        if (!packs.isEmpty()){
            return packs;
        } else {
            throw new RepositoryException("no packages found.");
        }
        
    }
}
