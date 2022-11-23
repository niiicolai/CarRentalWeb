package carrental.carrentalweb.entities;

import java.time.LocalDateTime;

public class Booking {
    private long id;

    private User user;
    private Car car;
    private String subscriptionName;
    private CreditRating creditRating;
    private PickupPoint pickupPoint;
    private DamageReport damageReport;
    private LocalDateTime deliveredAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Booking(long id, User user, Car car, String subscriptionName, CreditRating creditRating, PickupPoint pickupPoint, DamageReport damageReport, LocalDateTime deliveredAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.user = user;
        this.id = id;
        this.car = car;
        this.subscriptionName = subscriptionName;
        this.creditRating = creditRating;
        this.pickupPoint = pickupPoint;
        this.damageReport = damageReport;
        this.deliveredAt = deliveredAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Booking() {
    }

    public Car getCar() {
        return car;
    }

    public CreditRating getCreditRating() {
        return creditRating;
    }

    public PickupPoint getPickupPoint() {
        return pickupPoint;
    }

    public DamageReport getDamageReport() {
        return damageReport;
    }

    public LocalDateTime getDeliveredAt() {
        return deliveredAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public void setCreditRating(CreditRating creditRating) {
        this.creditRating = creditRating;
    }

    public void setPickupPoint(PickupPoint pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public void setDamageReport(DamageReport damageReport) {
        this.damageReport = damageReport;
    }

    public void setDeliveredAt(LocalDateTime deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }
}
