
package entities;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Madeleine
 */

@Embeddable
public class Recipient {
    

    private String name;
    
    @Embedded
    @OneToOne(cascade=CascadeType.REMOVE)
    private Location location;

    public Recipient() {
    }
    
    public Recipient(String name, Location location){
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    
    
}
