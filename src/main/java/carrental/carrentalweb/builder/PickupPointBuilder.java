package carrental.carrentalweb.builder;

import java.time.LocalDateTime;

import carrental.carrentalweb.entities.PickupPoint;

/*
 * Written by Nicolai Berg Andersen
 */
public class PickupPointBuilder {
    
    private PickupPoint pickupPoint;

    public PickupPointBuilder () {
        this.pickupPoint = new PickupPoint();
    }

    public PickupPointBuilder id(long id) {
        pickupPoint.setId(id);
        return this;
    }

    public PickupPointBuilder name(String name) {
        pickupPoint.setName(name);
        return this;
    }

    public PickupPointBuilder addressId(long addressId) {
        pickupPoint.setAddressId(addressId);
        return this;
    }

    public PickupPointBuilder createdAt(LocalDateTime createdAt) {
        pickupPoint.setCreatedAt(createdAt);
        return this;
    }

    public PickupPointBuilder updatedAt(LocalDateTime updatedAt) {
        pickupPoint.setUpdatedAt(updatedAt);
        return this;
    }

    public PickupPoint build() {
        return pickupPoint;
    }
}
