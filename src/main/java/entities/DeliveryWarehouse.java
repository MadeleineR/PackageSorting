/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Madeleine
 */
@Entity
public class DeliveryWarehouse {

    @Id
    @GeneratedValue
    private long delivery_warehouse_id;
    private String regionKey;
    @Embedded
    @OneToOne(cascade = CascadeType.REMOVE)
    private Location location;
    @OneToMany(mappedBy = "warehouse", cascade = CascadeType.PERSIST)
    private List<Package> packages;
    @OneToMany(mappedBy = "delivery_warehouse", cascade = CascadeType.PERSIST)
    private List<DeliveryPerson> persons;

    public DeliveryWarehouse() {
    }

    public List<DeliveryPerson> getPersons() {
        return persons;
    }

    public void setPersons(List<DeliveryPerson> persons) {
        this.persons = persons;
    }

    public DeliveryWarehouse(long id, Location location) {
        this.delivery_warehouse_id = id;
        this.location = location;
    }

    public long getDelivery_warehouse_id() {
        return delivery_warehouse_id;
    }

    public void setDelivery_warehouse_id(long delivery_warehouse_id) {
        this.delivery_warehouse_id = delivery_warehouse_id;
    }

    public String getRegionKey() {
        return regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public List<Package> getPackages() {
        return this.packages;
    }
}
