package carrental.carrentalweb.builder;

import carrental.carrentalweb.entities.DamageSpecification;

import java.time.LocalDateTime;

// Mads
public class DamageSpecificationBuilder {
    private DamageSpecification damageSpecification;

    public DamageSpecificationBuilder() {
        this.damageSpecification = new DamageSpecification();
    }

    public DamageSpecificationBuilder description(String description) {
        damageSpecification.setDescription(description);
        return this;
    }
    public DamageSpecificationBuilder price(double price) {
        damageSpecification.setPrice(price);
        return this;
    }
    public DamageSpecificationBuilder damaged(boolean damaged) {
        damageSpecification.setDamaged(damaged);
        return this;
    }
    public DamageSpecificationBuilder createdAt(LocalDateTime createdAt) {
        damageSpecification.setCreatedAt(createdAt);
        return this;
    }
    public DamageSpecificationBuilder updatedAt(LocalDateTime updatedAt) {
        damageSpecification.setUpdatedAt(updatedAt);
        return this;
    }
    public DamageSpecification build() {
        return damageSpecification;
    }
}
