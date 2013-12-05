/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Madeleine
 */

@Entity
public class DeliveryPerson {
    
    @Id
    @GeneratedValue
    private long person_id;
    private String name;
    
    @ManyToOne
    private DeliveryWarehouse delivery_warehouse;

    public DeliveryPerson() {
    }
    
    public DeliveryPerson(long id, String name, DeliveryWarehouse del_wh){
        this.person_id = id;
        this.name = name;
        this.delivery_warehouse = del_wh;
    }

    public long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(long person_id) {
        this.person_id = person_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DeliveryWarehouse getDelivery_warehouse() {
        return delivery_warehouse;
    }

    public void setDelivery_warehouse(DeliveryWarehouse delivery_warehouse) {
        this.delivery_warehouse = delivery_warehouse;
    }
    
    
}
