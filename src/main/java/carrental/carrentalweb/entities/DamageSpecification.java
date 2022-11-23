package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

// Mads
public class DamageSpecification {
    private String description;
    private boolean damaged;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DamageSpecification() {
    }

    public DamageSpecification(String description, boolean damaged, double price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.description = description;
        this.damaged = damaged;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
