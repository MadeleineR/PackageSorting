/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Madeleine
 */

@Entity
public class Package {
    
    @Id
    @GeneratedValue
    private long package_ID;
    
    @Embedded
    @OneToOne(cascade=CascadeType.REMOVE)
    private Recipient recipient;
    
    @ManyToOne
    private DeliveryWarehouse warehouse;
    
    private boolean delivered;


    public Package() {
        this.delivered = false;
    }
    

    public DeliveryWarehouse getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(DeliveryWarehouse warehouse) {
        this.warehouse = warehouse;
    }
    
    public Package(long id, Recipient recipient){
        this.package_ID = id;
        this.recipient = recipient;
    }

    
    public long getPackage_ID() {
        return package_ID;
    }

    public void setPackage_ID(int package_ID) {
        this.package_ID = package_ID;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }
    
    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }
    
    
    
}
