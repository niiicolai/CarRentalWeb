package carrental.carrentalweb.entities;

import java.time.LocalDateTime;
import java.util.List;

// Mads
public class DamageReport {
    private List<DamageSpecification> damageSpecifications;
    private Long bookingId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DamageReport() {
    }

    public DamageReport(List<DamageSpecification> damageSpecifications, Long bookingId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.damageSpecifications = damageSpecifications;
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public List<DamageSpecification> getDamageSpecifications() {
        return damageSpecifications;
    }

    public void setDamageSpecifications(List<DamageSpecification> damageSpecifications) {
        this.damageSpecifications = damageSpecifications;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
